# Install Vocab Daily (APK)

## Teacher — build & share

1. Install [Android Studio](https://developer.android.com/studio)
2. Open the `android/` folder
3. **Build → Build Bundle(s) / APK(s) → Build APK(s)** (use **release**)
4. Share this file with students:  
   `android/app/build/outputs/apk/release/app-release.apk`

Or push to GitHub → **Actions → Build Android APK** → download the artifact.

If a run sits past ~10 minutes with no new log lines: **Cancel** it, pull the latest workflow fix, push again, and re-run. First successful build is often 3–8 minutes; later runs are faster with cache.

## Students — install & add widget

1. Open the APK on an **Android** phone → **Install**  
   (allow install from Drive/Chrome/Files if asked)
2. Open **Vocab Daily**
3. Long-press home screen → **Widgets** → **Vocab Daily card** → place it

No internet needed. The word changes each UTC day.

To update later: install the new APK over the old one.
