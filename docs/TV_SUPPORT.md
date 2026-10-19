# Android TV support — design notes

This document describes everything the fork adds on top of upstream Kotatsu for 10-foot usage,
where the code lives, and how to extend it.

All TV behaviour is gated by `org.koitharu.kotatsu.core.ui.tv.TvController`:

* `isTvDevice` — hardware detection (`UiModeManager`, `UI_MODE_TYPE_TELEVISION`,
  `PackageManager.FEATURE_LEANBACK`).
* `isEnabled` — detection combined with the user preference *Settings → TV & remote control →
  TV mode* (`AUTO` / `ON` / `OFF`, key `tv_mode`).
* per-feature getters (`isFocusHighlightEnabled`, `isHoverToFocusEnabled`,
  `isWheelPagingEnabled`, `isAutoscrollRemoteKeysEnabled`).

Everything below is a no-op when `isEnabled` is `false`, so phones and tablets keep the exact
upstream behaviour.

## 1. Platform / manifest

* `android.software.leanback` and `android.hardware.touchscreen` declared with
  `required="false"` → installable on TV *and* on phones.
* `android.intent.category.LEANBACK_LAUNCHER` on `MainActivity` → icon on the TV home screen.
* `android:banner="@drawable/tv_banner"` (320×180 px, `drawable-xhdpi`) → artwork in the TV
  launcher.
* `res/values-television/dimens.xml` → larger covers/spacing for viewing distance.

## 2. Focus layer (lists & grids)

Upstream item layouts are touch-only: no view is focusable, so on a TV the D-pad does nothing.
Instead of touching ~150 layouts, the fork adapts every `RecyclerView` globally:

| File | Role |
|---|---|
| `core/ui/tv/TvActivityLifecycleCallbacks.kt` | registered in `AppModule.provideActivityLifecycleCallbacks`; walks each activity decor view on resume and every fragment view on creation and installs the delegate into each `RecyclerView` |
| `core/ui/tv/TvListFocusDelegate.kt` | makes item views focusable, sets pointer icons and hover-to-focus, remembers the focused position and restores it when the window comes back unfocused, repaints the ring on focus changes |
| `core/ui/tv/TvFocusHighlightDecoration.kt` | `ItemDecoration` drawing a two-tone focus ring around the focused item (no layout changes, no `foreground` override) |
| `core/ui/tv/TvFocus.kt` | tree walking, subtree opt-out, initial-focus fallback |
| `core/ui/tv/TvFocusTarget.kt` | interface for activities that decide their own initial focus |

Skipped on purpose:

* `RecyclerView`s owned by `ViewPager2` (focus would fight the pager),
* any subtree tagged with `TvFocus.skipSubtree(...)` — used by the reader, see below.

Initial focus: when an activity resumes with nothing focused, `TvFocus.ensureInitialFocus`
focuses the first focusable view in tree order (typically the search bar), so the very first
D-pad press already does something. Activities may opt out via `TvFocusTarget` (the reader does).

## 3. Reader remote map

Implemented in `reader/ui/ReaderControlDelegate.kt` (+ `ReaderActivity`):

| Key | Action |
|---|---|
| `DPAD LEFT` / `RIGHT` | previous / next page (honours *invert navigation*) |
| `DPAD UP` / `DOWN` | smooth scroll, or previous / next page when the edge is reached |
| `DPAD CENTER` (short) | show / hide the reader UI |
| `DPAD CENTER` (long) | toggle auto-scroll (with on-screen readout) |
| `MENU` (or gamepad `Y`) | open the reader options sheet |
| `MEDIA PLAY_PAUSE` / `PLAY` | toggle auto-scroll |
| `MEDIA FAST_FORWARD` / `NEXT` | auto-scroll speed +5% |
| `MEDIA REWIND` / `PREVIOUS` | auto-scroll speed −5% |
| `BACK` | hides the reader UI first, exits on the second press |
| `PAGE_UP/DOWN`, `SPACE`, `R`, `L`, volume keys | unchanged upstream behaviour |

Rules that make the D-pad usable while the UI is visible:

* while the focus is inside the toolbar / docked actions bar / auto-scroll panel
  (`ReaderActivity.isUiControlsFocused()`), directional keys are **not** intercepted so the
  framework moves the focus between the controls;
* showing the UI moves the focus into it, hiding it drops the focus back to “nowhere”, which
  re-arms paging;
* the reader container is tagged with `TvFocus.skipSubtree`, so page views never become
  focusable and never swallow D-pad events.

### Auto-scroll internals

`reader/ui/ScrollTimer.kt` gained `toggleActive()` (applies a default speed when none was ever
configured, so a single `PLAY` press works) and `adjustSpeed(delta)`, both persisted through
`AppSettings.readerAutoscrollSpeed`. The existing pause-on-interaction logic (2 s grace after
any key/touch) is untouched, so adjusting the speed never fights the scroll job.

`ScrollTimerControlView` (the speed panel) grabs the D-pad focus when shown on a TV and returns
it when hidden; its switch and slider are natively D-pad operable.

## 4. Mouse / pointer

`core/ui/tv/TvPointer.kt`:

* `installHoverToFocus` — hovering a list item moves the focus under the cursor, so the focus
  ring and the pointer always agree about what a click will activate;
* `applyIcon` — system pointer icons (`TYPE_HAND` for activatable items), API 24+;
* `verticalScrollDelta` — wheel extraction used by `ReaderActivity.dispatchGenericMotionEvent`
  to turn pages with the wheel in pager modes (webtoon mode scrolls natively because its
  `RecyclerView` consumes the wheel itself).

Android TV shows the system pointer whenever a mouse/air-mouse is connected; the app never
captures or hides it.

## 5. Settings

* `res/xml/pref_tv.xml` + `settings/TvSettingsFragment.kt`, reachable from
  *Settings → TV & remote control*.
* Keys (in `AppSettings`): `tv_mode`, `tv_focus_highlight`, `tv_hover_focus`, `tv_wheel_paging`,
  `tv_autoscroll_keys`.
* Changing *TV mode* takes effect on the next activity resume (lists already on screen keep
  their current state).

## 6. Extending

* New list screen: nothing to do — the lifecycle callbacks adapt any `RecyclerView`.
* New screen that handles the D-pad itself: implement `TvFocusTarget` (return `null` to keep the
  window unfocused) and/or call `TvFocus.skipSubtree(root)`.
* New remote shortcuts: add them to `ReaderControlDelegate.onKeyDown`; remember to return
  `false` for keys the focused UI control should handle.
