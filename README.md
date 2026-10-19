# Kotatsu TV

A fork of [Kotatsu](https://github.com/KotatsuApp/Kotatsu) — the open-source manga reader for
Android — reworked for **Android TV / Google TV** and other 10-foot devices.

The upstream app is built for touch: lists are not focusable, nothing reacts to a D-pad outside
of the reader, and the auto-scroll feature can only be driven from an on-screen panel. This fork
keeps 100% of the upstream app (phones and tablets still work exactly as before) and layers a TV
adaptation on top of it:

| Area | What the fork adds |
|---|---|
| **Platform** | Leanback launcher entry, TV banner, `touchscreen` no longer required → the app shows up on the Android TV home screen and installs on TV boxes |
| **D-pad navigation** | Every list/grid becomes focusable, with a high-contrast focus ring, focus memory across screens and a sane initial focus |
| **Reader remote map** | D-pad pages/scrolls, `CENTER` toggles the UI, long-press `CENTER` toggles auto-scroll, `MENU` opens reader options, media keys drive auto-scroll |
| **Auto-scroll on remote** | `PLAY/PAUSE` start/stop, `FF`/`REW` change the speed with an on-screen readout; the speed panel is D-pad operable |
| **Mouse / air-mouse** | Visible focus follows the pointer on hover, system pointer icons, mouse-wheel page turning in the reader |
| **10-foot layout** | Bigger covers and spacing via the `television` resource qualifier |
| **Settings** | A new *TV & remote control* screen: mode auto/on/off, focus ring, hover-to-focus, wheel paging, remote auto-scroll keys |

See **[docs/TV_SUPPORT.md](docs/TV_SUPPORT.md)** for the full remote key map and the architecture
of the TV layer, and **[docs/UPSTREAM_SYNC.md](docs/UPSTREAM_SYNC.md)** for how to merge new
upstream releases into this fork.

## Upstream base

* Repository: <https://github.com/KotatsuApp/Kotatsu>
* Branch / commit: `master` @ `06a0b5829b8d5f214d60d5011d66ec1ccbd5630e` (2025-11-03)
* Upstream version: **9.4.1**
* License: **GPL-3.0-only** (see [LICENSE](LICENSE)); the upstream README is kept at
  [docs/UPSTREAM_README.md](docs/UPSTREAM_README.md)

Excluded from the imported snapshot (re-addable from upstream at any time): the F-Droid store
listing (`metadata/`) and upstream CI workflows (`.github/workflows`).

## Building

Requirements: JDK 17, Android SDK with platform 36 (the SDK is downloaded by the build if needed).

```bash
./gradlew assembleDebug          # debug APK for phone, tablet and TV
adb install app/build/outputs/apk/debug/app-debug.apk
```

For a TV device without adb, install over the network:

```bash
adb connect <tv-ip>:5555
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Every push to this repository also produces a debug APK as a GitHub Actions artifact
(see the *Build TV APK* workflow).

To install next to the original Kotatsu, change `applicationId` in `app/build.gradle`
(e.g. to `org.koitharu.kotatsu.tv`).

## Testing on an emulator

```bash
sdkmanager "system-images;android-34;google_apis;x86_64" "system-images;android-34;android-tv;x86"
avdmanager create avd -n tv -k "system-images;android-34;android-tv;x86"
emulator -avd tv
```

The TV adaptation auto-detects the device; force it on or off in *Settings → TV & remote control*.

## License

GPL-3.0-only — same as upstream. Modifications in this fork are © their contributors and inherit
the upstream license.
