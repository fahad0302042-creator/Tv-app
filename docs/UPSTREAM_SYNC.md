# Syncing with upstream Kotatsu

This repository started as a **snapshot import** (single squashed commit) of upstream `master`,
not as a full-history fork, to keep the repository small. The import commit message records the
exact base:

```
Import Kotatsu 9.4.1 (upstream 06a0b58) as TV fork base
```

* Upstream: <https://github.com/KotatsuApp/Kotatsu>
* Base commit: `06a0b5829b8d5f214d60d5011d66ec1ccbd5630e` (`master`, 2025-11-03, version 9.4.1)

## Pulling a new upstream release

```bash
git remote add upstream https://github.com/KotatsuApp/Kotatsu.git   # once
git fetch upstream --tag --depth=1 <tag-or-branch>                  # e.g. release/7.1.2 or master
git merge --allow-unrelated-histories FETCH_HEAD
```

Because the import is a snapshot, the first merge has no common ancestor
(`--allow-unrelated-histories`); Git will then treat the upstream tree as the other side and
report conflicts only where the TV fork actually changed files. After the first merge the two
histories are joined and later merges are normal.

Alternative without merging (cherry-pick style, keeps history linear):

```bash
git fetch upstream --depth=1 master
git checkout FETCH_HEAD -- app/src/main       # then review with git status / git diff --cached
```

## Files touched by the TV fork (conflict hotspots)

Keep this list in mind when merging; everything else merges cleanly.

| Path | Why |
|---|---|
| `app/src/main/AndroidManifest.xml` | leanback feature, banner, `LEANBACK_LAUNCHER` |
| `app/build.gradle` | (only if you changed `applicationId`) |
| `app/src/main/kotlin/org/koitharu/kotatsu/core/AppModule.kt` | `TvActivityLifecycleCallbacks` registered in the lifecycle-callbacks set |
| `app/src/main/kotlin/org/koitharu/kotatsu/core/prefs/AppSettings.kt` | TV preference properties + keys |
| `app/src/main/kotlin/org/koitharu/kotatsu/reader/ui/ReaderControlDelegate.kt` | remote key map (heavily modified) |
| `app/src/main/kotlin/org/koitharu/kotatsu/reader/ui/ReaderActivity.kt` | TV focus/wheel/back handling, new listener methods |
| `app/src/main/kotlin/org/koitharu/kotatsu/reader/ui/ScrollTimer.kt` | `toggleActive()`, `adjustSpeed()` |
| `app/src/main/kotlin/org/koitharu/kotatsu/reader/ui/ScrollTimerControlView.kt` | D-pad focus grab/release |
| `app/src/main/res/values/{strings,colors,dimens,ids,arrays}.xml` | TV resources appended at the end of each file |
| `app/src/main/res/xml/pref_root.xml` | TV settings entry |

Purely additive (never conflict): everything under
`app/src/main/kotlin/org/koitharu/kotatsu/core/ui/tv/`,
`app/src/main/kotlin/org/koitharu/kotatsu/settings/TvSettingsFragment.kt`,
`app/src/main/res/xml/pref_tv.xml`, `app/src/main/res/values-television/`,
`app/src/main/res/drawable/{ic_tv.xml}`, `app/src/main/res/drawable-xhdpi/tv_banner.png`,
`.github/workflows/build_tv.yml`, `docs/`.

## Deliberately not imported from upstream

* `metadata/` — F-Droid store listing of the phone app (~5 MB of screenshots).
* `.github/workflows/` — upstream CI (issue moderator, website deploy trigger); replaced by
  `build_tv.yml`.
* `.idea/` — IDE state.
