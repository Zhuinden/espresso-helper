# Espresso-Helper 0.1.3 (2018-05-12)

- **Bugfix:** add missing `'com.android.support.test:rules:1.0.2'` transitive dependency, you don't need to add it yourself anymore 

- Add `ActivityTestRule.waitOnMainThread()` to allow waiting for the completion of UI-thread-bound callbacks.

# Espresso-Helper 0.1.2 (2018-05-05)

- Remove `checkNextActivityByIntent()` because it is not needed next to `checkCurrentActivityIs`

- Alter `BackgroundColorMatcher` to detect `GradientDrawable`'s default color too on API 24+

# Espresso-Helper 0.1.1 (2018-05-03)

- Add `checkCurrentActivityIs()` (with `ActivityLifecycleMonitorRegistry`), and `checkNextActivityByIntent()` (with `IntentsTestRule`)

- Add `rotate*()` methods.

- Add `performActionOnRecyclerHolderItem`, `performActionOnRecyclerItem`, `performActionOnRecyclerItemAtPosition`.

- Add `closeSoftKeyboard()` to `typeText`, `clearText`, `typeTextIntoFocusedView` and `replaceText`.

# Espresso-Helper 0.1.0 (2018-05-01)

- Initial release.