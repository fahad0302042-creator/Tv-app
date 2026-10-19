# Kotatsu TV — Android TV Manga Reader Fork

A TV-optimized fork of **Kotatsu-Redo** for Android Google TV, with remote navigation, mouse pointer, and auto-update pipeline.

## TV Updates Applied

1. **Renamed / Rebranded** — App name: `Kotatsu TV`, package: `io.github.kotatsuredo.tv`, namespace updated.
2. **Leanback Launcher** — Appears in Android TV home screen (`LEANBACK_LAUNCHER`).
3. **Mouse Pointer** — Toggle with **MENU button**. When enabled, D-pad moves the pointer; OK/Select clicks; MENU toggles off. Avoids navigation conflicts.
4. **Scaled UI** — `values-tv/dimens.xml` for larger text and spacing (10-foot viewing).
5. **Focus Navigation** — All menus navigable with remote (D-pad arrows + OK).
6. **Simplified Reader** — Large touch targets, reduced gesture complexity for remote use.
7. **Auto-Update Pipeline** — `update-pipeline.sh` pulls upstream Kotatsu-Redo and reapplies TV patches.

## Build

```bash
cd source
./gradlew :app:assembleRelease
```

## Auto-Update

```bash
./update-pipeline.sh
```

This pulls latest upstream, applies `tv-patches/`, and prepares a new build.
