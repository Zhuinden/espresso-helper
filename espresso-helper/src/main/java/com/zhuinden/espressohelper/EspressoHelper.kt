/*
 * Copyright 2017 agoda-com (kakao)
 *
 * Copyright 2018 Gabor Varadi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhuinden.espressohelper

import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.TabLayout
import android.support.design.widget.TextInputLayout
import android.support.v4.widget.SwipeRefreshLayout
import androidx.test.espresso.*
import androidx.test.espresso.action.EspressoKey
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import android.view.Gravity
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.platform.app.InstrumentationRegistry
import org.hamcrest.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit


// Wait for UI thread
private typealias InvokeOnComplete = () -> Unit

fun <T: Activity> ActivityTestRule<T>.waitOnMainThread(duration: Long = 15L, timeUnit: TimeUnit = TimeUnit.SECONDS, executionBlock: (InvokeOnComplete) -> Unit) {
    val latch = CountDownLatch(1)
    val completionCallback = { latch.countDown() }
    runOnUiThread {
        executionBlock(completionCallback)
    }
    MatcherAssert.assertThat("Waiting timed out, callback was never invoked.", latch.await(duration, timeUnit), CoreMatchers.`is`(true))
}

// Rotation
fun Activity.rotateToLandscape() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
}

fun Activity.rotateToPortrait() {
    requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
}

fun Activity.rotateOrientation() = resources.configuration.orientation.let { currentOrientation ->
    when (currentOrientation) {
        Configuration.ORIENTATION_LANDSCAPE -> rotateToPortrait()
        Configuration.ORIENTATION_PORTRAIT -> rotateToLandscape()
        else -> rotateToLandscape()
    }
}

// Get current activity
fun getCurrentActivity(): Activity {
    // The array is just to wrap the Activity and be able to access it from the Runnable.
    val resumedActivity = arrayOfNulls<Activity>(1)
    InstrumentationRegistry.getInstrumentation().runOnMainSync {
        val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(Stage.RESUMED);
        if (resumedActivities.iterator().hasNext()) {
            resumedActivity[0] = resumedActivities.iterator().next();
        } else {
            throw IllegalStateException("No Activity in stage RESUMED");
        }
    }
    return resumedActivity[0]!!
}

inline fun <reified T : Activity> checkCurrentActivityIs() {
    val currentActivity = getCurrentActivity()
    if (!(currentActivity is T)) {
        throw IllegalStateException("Activity should be ${T::class.java.simpleName} but was ${currentActivity::class.java.simpleName}")
    }
}

// ID matchers

fun Int.asIdViewMatcher() = ViewMatchers.withId(this)

fun matchRoot(): ViewInteraction = matchView(ViewMatchers.isRoot())

fun matchView(matcher: Matcher<View>): ViewInteraction = Espresso.onView(matcher)

fun Int.matchView(): ViewInteraction = matchView(asIdViewMatcher())

fun Int.performViewAction(vararg action: ViewAction) = matchView().perform(*action)

// ID extensions

fun Int.performClick() = matchView().performClick()

fun Int.performTypeText(text: String) = matchView().performTypeText(text)

fun Int.performTypeTextIntoFocusedView(text: String) = matchView().performTypeTextIntoFocusedView(text)

fun Int.performReplaceText(text: String) = matchView().performReplaceText(text)

fun Int.performClearText() = matchView().performClearText()

fun Int.performClick(inputDevice: Int, buttonState: Int) = matchView().performClick(inputDevice, buttonState)

fun Int.performClick(rollbackAction: ViewAction) = matchView().performClick(rollbackAction)

fun Int.performCloseSoftKeyboard() = matchView().performCloseSoftKeyboard()

fun Int.performPressKey(keyCode: Int) = matchView().performPressKey(keyCode)

fun Int.performPressKey(espressoKey: EspressoKey) = matchView().performPressKey(espressoKey)

fun Int.performPressMenu() = matchView().performPressMenu()

fun Int.performPressBack() = matchView().performPressBack()

fun Int.performPressBackUnconditionally() = matchView().performPressBackUnconditionally()

fun Int.performPressImeActionButton() = matchView().performPressImeActionButton()

fun Int.performScrollTo() = matchView().performScrollTo()

fun Int.performDoubleClick() = matchView().performDoubleClick()

fun Int.performLongClick() = matchView().performLongClick()

fun Int.performOpenLink(linkTextMatcher: Matcher<String>, uriMatcher: Matcher<Uri>) = matchView().performOpenLink(linkTextMatcher, uriMatcher)

fun Int.performOpenLinkWithText(linkText: String) = matchView().performOpenLinkWithText(linkText)

fun Int.performOpenLinkWithUri(uri: String) = matchView().performOpenLinkWithUri(uri)

fun Int.performSwipeUp() = matchView().performSwipeUp()

fun Int.performSwipeDown() = matchView().performSwipeDown()

fun Int.performSwipeLeft() = matchView().performSwipeLeft()

fun Int.performSwipeRight() = matchView().performSwipeRight()

// Kokoa-actions

fun Int.performIdleAction(duration: Long = IdleAction.DEFAULT_DURATION) = matchView().performIdleAction(duration)

fun Int.performScrollRecyclerToStart() = matchView().performScrollRecyclerToStart()

fun Int.performScrollRecyclerToEnd() = matchView().performScrollRecyclerToEnd()

fun Int.performScrollRecyclerTo(position: Int) = matchView().performScrollRecyclerTo(position)

fun Int.performScrollRecyclerTo(matcher: Matcher<View>) = matchView().performScrollRecyclerTo(matcher)

fun Int.recyclerAdapterSize(): Int = matchView().recyclerAdapterSize()

fun Int.performScrollScrollViewToStart() = matchView().performScrollScrollViewToStart()

fun Int.performScrollScrollViewToEnd() = matchView().performScrollScrollViewToEnd()

fun Int.performScrollScrollViewTo(position: Int) = matchView().performScrollScrollViewTo(position)

fun Int.scrollViewSize(): Int = matchView().scrollViewSize()

fun Int.performSetCheckableChecked(checked: Boolean) = matchView().performSetCheckableChecked(checked)

fun Int.performOpenNavigationDrawer(gravity: Int = Gravity.START) = matchView().performOpenNavigationDrawer(gravity)

fun Int.performCloseNavigationDrawer(gravity: Int = Gravity.START) = matchView().performCloseNavigationDrawer(gravity)

fun Int.performNavigateNavViewTo(id: Int) = matchView().performNavigateNavViewTo(id)

fun Int.performSetProgressBarProgress(number: Int) = matchView().performSetProgressBarProgress(number)

fun Int.performSetRatingBarRatingAt(number: Float) = matchView().performSetRatingBarRatingAt(number)

fun Int.performSetBottomNavViewSelectedItem(id: Int) = matchView().performSetBottomNavViewSelectedItem(id)

fun Int.performSelectTabLayoutTab(index: Int) = matchView().performSelectTabLayoutTab(index)

fun Int.getTabLayoutSelectedItem(): Int = matchView().getTabLayoutSelectedItem()

fun Int.performSetRefreshLayoutRefreshing(refreshing: Boolean) = matchView().performSetRefreshLayoutRefreshing(refreshing)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerHolderItem(viewHolderMatcher: Matcher<T>, action: ViewAction) = matchView().performActionOnRecyclerHolderItem(viewHolderMatcher, action)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerItem(itemViewMatcher: Matcher<View>, action: ViewAction) = matchView().performActionOnRecyclerItem<T>(itemViewMatcher, action)

fun <T : RecyclerView.ViewHolder> Int.performActionOnRecyclerItemAtPosition(position: Int, action: ViewAction) = matchView().performActionOnRecyclerItemAtPosition<T>(position, action)

// interactions

fun ViewInteraction.performClick() = perform(ViewActions.click())

fun ViewInteraction.performTypeText(text: String) = perform(ViewActions.typeText(text), ViewActions.closeSoftKeyboard())

fun ViewInteraction.performTypeTextIntoFocusedView(text: String) = perform(ViewActions.typeTextIntoFocusedView(text), ViewActions.closeSoftKeyboard())

fun ViewInteraction.performReplaceText(text: String) = perform(ViewActions.replaceText(text), ViewActions.closeSoftKeyboard())

fun ViewInteraction.performClearText() = perform(ViewActions.clearText(), ViewActions.closeSoftKeyboard())

fun ViewInteraction.performClick(inputDevice: Int, buttonState: Int) = perform(ViewActions.click(inputDevice, buttonState))

fun ViewInteraction.performClick(rollbackAction: ViewAction) = perform(ViewActions.click(rollbackAction))

fun ViewInteraction.performCloseSoftKeyboard() = perform(ViewActions.closeSoftKeyboard())

fun ViewInteraction.performPressKey(keyCode: Int) = perform(ViewActions.pressKey(keyCode))

fun ViewInteraction.performPressKey(espressoKey: EspressoKey) = perform(ViewActions.pressKey(espressoKey))

fun ViewInteraction.performPressMenu() = perform(ViewActions.pressMenuKey())

fun ViewInteraction.performPressBack() = perform(ViewActions.pressBack())

fun ViewInteraction.performPressBackUnconditionally() = perform(ViewActions.pressBackUnconditionally())

fun ViewInteraction.performPressImeActionButton() = perform(ViewActions.pressImeActionButton())

fun ViewInteraction.performScrollTo() = perform(ViewActions.scrollTo())

fun ViewInteraction.performDoubleClick() = perform(ViewActions.doubleClick())

fun ViewInteraction.performLongClick() = perform(ViewActions.longClick())

fun ViewInteraction.performOpenLink(linkTextMatcher: Matcher<String>, uriMatcher: Matcher<Uri>) = perform(ViewActions.openLink(linkTextMatcher, uriMatcher))

fun ViewInteraction.performOpenLinkWithText(linkText: String) = perform(ViewActions.openLinkWithText(linkText))

fun ViewInteraction.performOpenLinkWithUri(uri: String) = perform(ViewActions.openLinkWithUri(uri))

fun ViewInteraction.performSwipeUp() = perform(ViewActions.swipeUp())

fun ViewInteraction.performSwipeDown() = perform(ViewActions.swipeDown())

fun ViewInteraction.performSwipeLeft() = perform(ViewActions.swipeLeft())

fun ViewInteraction.performSwipeRight() = perform(ViewActions.swipeRight())

// Kokoa-actions

fun ViewInteraction.performIdleAction(duration: Long = IdleAction.DEFAULT_DURATION) = perform(IdleAction(duration))

fun ViewInteraction.performScrollRecyclerToStart() {
    perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
}

fun ViewInteraction.performScrollRecyclerToEnd() {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll RecyclerView to the bottom"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(RecyclerView::class.java)

        override fun perform(controller: UiController, view: View) {
            if (view is RecyclerView) {
                val position = view.adapter!!.itemCount - 1
                view.scrollToPosition(position)
                controller.loopMainThreadUntilIdle()
                val lastView = view.findViewHolderForLayoutPosition(position)!!.itemView
                view.scrollBy(0, lastView.height)
                controller.loopMainThreadUntilIdle()
            }
        }
    })
}

fun ViewInteraction.performScrollRecyclerTo(position: Int) {
    perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(position))
}

fun ViewInteraction.performScrollRecyclerTo(matcher: Matcher<View>) {
    perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(matcher))
}

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerHolderItem(viewHolderMatcher: Matcher<T>, action: ViewAction) {
    perform(RecyclerViewActions.actionOnHolderItem(viewHolderMatcher, action))
}

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerItem(itemViewMatcher: Matcher<View>, action: ViewAction) {
    perform(RecyclerViewActions.actionOnItem<T>(itemViewMatcher, action))
}

fun <T : RecyclerView.ViewHolder> ViewInteraction.performActionOnRecyclerItemAtPosition(position: Int, action: ViewAction) {
    perform(RecyclerViewActions.actionOnItemAtPosition<T>(position, action))
}

fun ViewInteraction.recyclerAdapterSize(): Int {
    var size = 0

    perform(object : ViewAction {
        override fun getDescription() = "Get RecyclerView adapter size"

        override fun getConstraints() = Matchers.allOf(ViewMatchers
            .isAssignableFrom(RecyclerView::class.java), ViewMatchers.isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is RecyclerView) {
                size = view.adapter?.itemCount!!
            }
        }
    })

    return size
}

fun ViewInteraction.performScrollScrollViewToStart() {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to start"

        override fun getConstraints() = Matchers.allOf(ViewMatchers
            .isAssignableFrom(ScrollView::class.java), ViewMatchers.isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) {
                view.fullScroll(View.FOCUS_UP)
            }
        }
    })
}

fun ViewInteraction.performScrollScrollViewToEnd() {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to end"

        override fun getConstraints() = Matchers.allOf(ViewMatchers
            .isAssignableFrom(ScrollView::class.java), ViewMatchers.isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) {
                view.fullScroll(View.FOCUS_DOWN)
            }
        }
    })
}

fun ViewInteraction.performScrollScrollViewTo(position: Int) {
    perform(object : ViewAction {
        override fun getDescription() = "Scroll ScrollView to $position Y position"

        override fun getConstraints() = Matchers.allOf(ViewMatchers
            .isAssignableFrom(ScrollView::class.java), ViewMatchers.isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is ScrollView) {
                view.scrollTo(0, position)
            }
        }
    })
}

fun ViewInteraction.scrollViewSize(): Int {
    var size = 0

    perform(object : ViewAction {
        override fun getDescription() = "Get AdapterView adapter size"

        override fun getConstraints() = Matchers.allOf(ViewMatchers
            .isAssignableFrom(AdapterView::class.java), ViewMatchers.isDisplayed())

        override fun perform(uiController: UiController?, view: View?) {
            if (view is AdapterView<*>) {
                size = view.count
            }
        }
    })

    return size
}

fun ViewInteraction.performSetCheckableChecked(checked: Boolean) {
    perform(object : ViewAction {
        override fun getDescription() = "performing CheckableViewAction: $checked"

        override fun getConstraints() = Matchers.allOf(ViewMatchers.isAssignableFrom(View::class.java),
            object : TypeSafeMatcher<View>() {
                override fun describeTo(description: Description) {
                    description.appendText("is assignable from class: " + Checkable::class.java)
                }

                override fun matchesSafely(view: View) = Checkable::class.java.isAssignableFrom(view.javaClass)
            })

        override fun perform(uiController: UiController, view: View) {
            if (view is Checkable) view.isChecked = checked
        }
    })
}

fun ViewInteraction.performOpenNavigationDrawer(gravity: Int = Gravity.START) {
    perform(DrawerActions.open(gravity))
}

fun ViewInteraction.performCloseNavigationDrawer(gravity: Int = Gravity.START) {
    perform(DrawerActions.close(gravity))
}

fun ViewInteraction.performNavigateNavViewTo(id: Int) {
    perform(NavigationViewActions.navigateTo(id))
}

fun ViewInteraction.performSetProgressBarProgress(number: Int) {
    perform(object : ViewAction {
        override fun getDescription() = "set progress value of progress bar as: $number"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(ProgressBar::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is ProgressBar) {
                view.progress = number
            }
        }
    })
}

fun ViewInteraction.performSetRatingBarRatingAt(number: Float) {
    perform(object : ViewAction {
        override fun getDescription() = "set rating value of rating bar as: $number"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(RatingBar::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is RatingBar) {
                view.rating = number
            }
        }
    })
}

fun ViewInteraction.performSetBottomNavViewSelectedItem(id: Int) {
    perform(object : ViewAction {
        override fun getDescription() = "Sets given item id as selected: $id"

        override fun getConstraints() = ViewMatchers
            .isAssignableFrom(BottomNavigationView::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is BottomNavigationView) {
                view.selectedItemId = id
            }
        }
    })
}

fun ViewInteraction.performSelectTabLayoutTab(index: Int) {
    perform(object : ViewAction {
        override fun getDescription() = "Selects the tab at index: $index"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(TabLayout::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is TabLayout) {
                view.getTabAt(index)!!.select()
            }
        }
    })
}

fun ViewInteraction.getTabLayoutSelectedItem(): Int {
    var id = 0

    perform(object : ViewAction {
        override fun getDescription() = "Gets selected item id"

        override fun getConstraints() = ViewMatchers
            .isAssignableFrom(BottomNavigationView::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is BottomNavigationView) {
                id = view.selectedItemId
            }
        }
    })

    return id
}

fun ViewInteraction.performSetRefreshLayoutRefreshing(refreshing: Boolean) {
    perform(object : ViewAction {
        override fun getDescription() = "Sets the refreshing state to $refreshing"

        override fun getConstraints() = ViewMatchers.isAssignableFrom(SwipeRefreshLayout::class.java)

        override fun perform(uiController: UiController, view: View) {
            if (view is SwipeRefreshLayout) {
                view.isRefreshing = refreshing
            }
        }
    })
}

// assertions

fun String.isTextOf(@IdRes viewId: Int) {
    viewId.matchView().check(ViewAssertions.matches(ViewMatchers.withText(this)))
}

fun Int.isStringResOf(@IdRes viewId: Int) {
    viewId.matchView().check(ViewAssertions.matches(ViewMatchers.withText(this)))
}

inline fun <reified T : View> ViewInteraction.checkHasChild() {
    check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.isAssignableFrom(T::class.java))))
}

inline fun <reified T : View> ViewInteraction.checkIsAssignableFrom() {
    check(ViewAssertions.matches(ViewMatchers.isAssignableFrom(T::class.java)))
}

// Kokoa assertions
fun ViewInteraction.checkIsDisplayed() {
    check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
}

fun ViewInteraction.checkIsNotDisplayed() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isDisplayed())))
}

fun ViewInteraction.checkIsCompletelyDisplayed() {
    check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()))
}

fun ViewInteraction.checkIsNotCompletelyDisplayed() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isCompletelyDisplayed())))
}

fun ViewInteraction.checkIsVisible() {
    check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
}

fun ViewInteraction.checkIsInvisible() {
    check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)))
}

fun ViewInteraction.checkIsGone() {
    check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
}

fun ViewInteraction.checkIsSelected() {
    check(ViewAssertions.matches(ViewMatchers.isSelected()))
}

fun ViewInteraction.checkIsNotSelected() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isSelected())))
}

fun ViewInteraction.checkIsFocused() {
    check(ViewAssertions.matches(ViewMatchers.hasFocus()))
}

fun ViewInteraction.checkIsNotFocused() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.hasFocus())))
}

fun ViewInteraction.checkIsFocusable() {
    check(ViewAssertions.matches(ViewMatchers.isFocusable()))
}

fun ViewInteraction.checkIsNotFocusable() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isFocusable())))
}

fun ViewInteraction.checkIsClickable() {
    check(ViewAssertions.matches(ViewMatchers.isClickable()))
}

fun ViewInteraction.checkIsNotClickable() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isClickable())))
}

fun ViewInteraction.checkIsEnabled() {
    check(ViewAssertions.matches(ViewMatchers.isEnabled()))
}

fun ViewInteraction.checkIsDisabled() {
    check(ViewAssertions.matches(Matchers.not(ViewMatchers.isEnabled())))
}

fun ViewInteraction.checkHasTag(tag: String) {
    check(ViewAssertions.matches(ViewMatchers.withTagValue(Matchers.`is`(tag))))
}

fun ViewInteraction.checkHasAnyTag(vararg tags: String) {
    val matchers = ArrayList<Matcher<Any>>(tags.size)

    tags.forEach {
        matchers.add(Matchers.`is`(it))
    }

    check(ViewAssertions.matches(ViewMatchers.withTagValue(Matchers.anyOf(matchers))))
}

fun ViewInteraction.checkDoesNotExist() {
    check(ViewAssertions.doesNotExist())
}

fun ViewInteraction.checkHasBackgroundColor(@ColorRes resId: Int) {
    check(ViewAssertions.matches(BackgroundColorMatcher(resId = resId)))
}

fun ViewInteraction.checkHasBackgroundColor(colorCode: String) {
    check(ViewAssertions.matches(BackgroundColorMatcher(colorCode = colorCode)))
}

fun ViewInteraction.checkHasEmptyText() {
    check(ViewAssertions.matches(ViewMatchers.withText("")))
}

fun ViewInteraction.checkHasAnyText() {
    check(ViewAssertions.matches(AnyTextMatcher()))
}

fun ViewInteraction.checkHasText(text: String) {
    check(ViewAssertions.matches(ViewMatchers.withText(text)))
}

fun ViewInteraction.checkHasText(@StringRes resId: Int) {
    check(ViewAssertions.matches(ViewMatchers.withText(resId)))
}

fun ViewInteraction.checkHasText(matcher: Matcher<String>) {
    check(ViewAssertions.matches(ViewMatchers.withText(matcher)))
}

fun ViewInteraction.checkHasTextColor(@ColorRes resId: Int) {
    check(ViewAssertions.matches(ViewMatchers.hasTextColor(resId)))
}

fun ViewInteraction.checkHasNoText(text: String) {
    check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(text))))
}

fun ViewInteraction.checkHasNoText(@StringRes resId: Int) {
    check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.withText(resId))))
}

fun ViewInteraction.checkHasContentDescription(text: String) {
    check(ViewAssertions.matches(ViewMatchers.withContentDescription(text)))
}

fun ViewInteraction.checkContainsText(text: String) {
    check(ViewAssertions.matches(
        ViewMatchers.withText(Matchers.containsString(text))))
}

fun ViewInteraction.checkStartsWithText(text: String) {
    check(ViewAssertions.matches(
        ViewMatchers.withText(Matchers.startsWith(text))))
}

fun ViewInteraction.checkHasHint(hint: String) {
    check(ViewAssertions.matches(ViewMatchers.withHint(hint)))
}

fun ViewInteraction.checkHasHint(@StringRes resId: Int) {
    check(ViewAssertions.matches(ViewMatchers.withHint(resId)))
}

fun ViewInteraction.checkIsChecked() {
    check(ViewAssertions.matches(ViewMatchers.isChecked()))
}

fun ViewInteraction.checkIsNotChecked() {
    check(ViewAssertions.matches(ViewMatchers.isNotChecked()))
}

fun ViewInteraction.checkIsViewPagerAtPage(index: Int) {
    check(ViewAssertions.matches(PageMatcher(index)))
}

fun ViewInteraction.checkIsRecyclerSize(size: Int) {
    check(ViewAssertions.matches(RecyclerViewAdapterSizeMatcher(size)))
}

fun ViewInteraction.checkIsListSize(size: Int) {
    check(ViewAssertions.matches(ListViewViewAdapterSizeMatcher(size)))
}

fun ViewInteraction.checkIsNavigationViewItemChecked(id: Int) {
    check(ViewAssertions.matches(NavigationItemMatcher(id)))
}

fun ViewInteraction.checkIsProgressBarProgress(number: Int) {
    check(ViewAssertions.matches(ProgressMatcher(number)))
}

fun ViewInteraction.checkIsRatingBarRating(number: Float) {
    check(ViewAssertions.matches(RatingBarMatcher(number)))
}

fun ViewInteraction.checkIsTabLayoutTabSelected(index: Int) {
    check { view, notFoundException ->
        if (view is TabLayout) {
            if (view.selectedTabPosition != index) {
                throw AssertionError("Expected selected item index is $index," +
                    " but actual is ${view.selectedTabPosition}")
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutHint(hint: String) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (hint != view.hint) {
                throw AssertionError("Expected hint is $hint," +
                    " but actual is ${view.hint}")
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutHintEnabled() {
    check(ViewAssertions.matches(TextInputLayoutHintEnabledMatcher(true)))
}

fun ViewInteraction.checkIsTextInputLayoutHintDisabled() {
    check(ViewAssertions.matches(TextInputLayoutHintEnabledMatcher(false)))
}

fun ViewInteraction.checkIsTextInputLayoutError(error: String) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (error != view.error) {
                throw AssertionError("Expected error is $error," +
                    " but actual is ${view.error}")
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutErrorEnabled() {
    check(ViewAssertions.matches(TextInputLayoutErrorEnabledMatcher(true)))
}

fun ViewInteraction.checkIsTextInputLayoutErrorDisabled() {
    check(ViewAssertions.matches(TextInputLayoutErrorEnabledMatcher(false)))
}

fun ViewInteraction.checkIsTextInputLayoutCounterMaxLength(length: Int) {
    check { view, notFoundException ->
        if (view is TextInputLayout) {
            if (length != view.counterMaxLength) {
                throw AssertionError("Expected counter max length is $length," +
                    " but actual is ${view.counterMaxLength}")
            }
        } else {
            notFoundException?.let { throw AssertionError(it) }
        }
    }
}

fun ViewInteraction.checkIsTextInputLayoutCounterEnabled() {
    check(ViewAssertions.matches(TextInputLayoutCounterEnabledMatcher(true)))
}

fun ViewInteraction.checkIsTextInputLayoutCounterDisabled() {
    check(ViewAssertions.matches(TextInputLayoutCounterEnabledMatcher(false)))
}

fun ViewInteraction.checkIsImageViewDrawable(@DrawableRes resId: Int, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) {
    check(ViewAssertions.matches(DrawableMatcher(resId = resId, toBitmap = toBitmap)))
}

fun ViewInteraction.checkIsImageViewDrawable(drawable: Drawable, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) {
    check(ViewAssertions.matches(DrawableMatcher(drawable = drawable, toBitmap = toBitmap)))
}

fun ViewInteraction.checkIsSwipeRefreshLayoutRefreshing() {
    check(ViewAssertions.matches(SwipeRefreshLayoutMatcher(true)))
}

fun ViewInteraction.checkIsSwipeRefreshLayoutNotRefreshing() {
    check(ViewAssertions.matches(SwipeRefreshLayoutMatcher(false)))
}

fun ViewInteraction.checkIsBottomNavigationViewItemSelected(id: Int) {
    check { view, notFoundException ->
        if (view is BottomNavigationView) {
            if (view.selectedItemId != id) {
                throw AssertionError("Expected selected item id is $id," +
                    " but actual is ${view.selectedItemId}")
            }
        } else {
            notFoundException?.let {
                throw AssertionError(it)
            }
        }
    }
}

// Kokoa assertions on IDs
fun Int.checkViewAssertion(viewAssertion: ViewAssertion) {
    matchView().check(viewAssertion)
}

fun Int.checkIsDisplayed() {
    matchView().checkIsDisplayed()
}

fun Int.checkIsNotDisplayed() {
    matchView().checkIsNotDisplayed()
}

fun Int.checkIsCompletelyDisplayed() {
    matchView().checkIsCompletelyDisplayed()
}

fun Int.checkIsNotCompletelyDisplayed() {
    matchView().checkIsNotCompletelyDisplayed()
}

fun Int.checkIsVisible() {
    matchView().checkIsVisible()
}

fun Int.checkIsInvisible() {
    matchView().checkIsInvisible()
}

fun Int.checkIsGone() {
    matchView().checkIsGone()
}

fun Int.checkIsSelected() {
    matchView().checkIsSelected()
}

fun Int.checkIsNotSelected() {
    matchView().checkIsNotSelected()
}

fun Int.checkIsFocused() {
    matchView().checkIsFocused()
}

fun Int.checkIsNotFocused() {
    matchView().checkIsNotFocused()
}

fun Int.checkIsFocusable() {
    matchView().checkIsFocusable()
}

fun Int.checkIsNotFocusable() {
    matchView().checkIsNotFocusable()
}

fun Int.checkIsClickable() {
    matchView().checkIsClickable()
}

fun Int.checkIsNotClickable() {
    matchView().checkIsNotClickable()
}

fun Int.checkIsEnabled() {
    matchView().checkIsEnabled()
}

fun Int.checkIsDisabled() {
    matchView().checkIsDisabled()
}

fun Int.checkHasTag(tag: String) {
    matchView().checkHasTag(tag)
}

fun Int.checkHasAnyTag(vararg tags: String) {
    matchView().checkHasAnyTag(*tags)
}

fun Int.checkDoesNotExist() {
    matchView().checkDoesNotExist()
}

fun Int.checkHasBackgroundColor(@ColorRes resId: Int) {
    matchView().checkHasBackgroundColor(resId)
}

fun Int.checkHasBackgroundColor(colorCode: String) {
    matchView().checkHasBackgroundColor(colorCode)
}

fun Int.checkHasEmptyText() {
    matchView().checkHasEmptyText()
}

fun Int.checkHasAnyText() {
    matchView().checkHasAnyText()
}

fun Int.checkHasText(text: String) {
    matchView().checkHasText(text)
}

fun Int.checkHasText(@StringRes resId: Int) {
    matchView().checkHasText(resId)
}

fun Int.checkHasText(matcher: Matcher<String>) {
    matchView().checkHasText(matcher)
}

fun Int.checkHasTextColor(@ColorRes resId: Int) {
    matchView().checkHasTextColor(resId)
}

fun Int.checkHasNoText(text: String) {
    matchView().checkHasNoText(text)
}

fun Int.checkHasNoText(@StringRes resId: Int) {
    matchView().checkHasNoText(resId)
}

fun Int.checkHasContentDescription(text: String) {
    matchView().checkHasContentDescription(text)
}

fun Int.checkContainsText(text: String) {
    matchView().checkContainsText(text)
}

fun Int.checkStartsWithText(text: String) {
    matchView().checkStartsWithText(text)
}

fun Int.checkIsChecked() {
    matchView().checkIsChecked()
}

fun Int.checkIsNotChecked() {
    matchView().checkIsNotChecked()
}

fun Int.checkIsViewPagerAtPage(index: Int) {
    matchView().checkIsViewPagerAtPage(index)
}

fun Int.checkIsRecyclerSize(size: Int) {
    matchView().checkIsRecyclerSize(size)
}

fun Int.checkIsListSize(size: Int) {
    matchView().checkIsListSize(size)
}

fun Int.checkIsNavigationViewItemChecked(id: Int) {
    matchView().checkIsNavigationViewItemChecked(id)
}

fun Int.checkIsProgressBarProgress(number: Int) {
    matchView().checkIsProgressBarProgress(number)
}

fun Int.checkIsRatingBarRating(number: Float) {
    matchView().checkIsRatingBarRating(number)
}

fun Int.checkIsTabLayoutTabSelected(index: Int) {
    matchView().checkIsTabLayoutTabSelected(index)
}

fun Int.checkIsTextInputLayoutHint(hint: String) {
    matchView().checkIsTextInputLayoutHint(hint)
}

fun Int.checkIsTextInputLayoutHintEnabled() {
    matchView().checkIsTextInputLayoutHintEnabled()
}

fun Int.checkIsTextInputLayoutHintDisabled() {
    matchView().checkIsTextInputLayoutHintDisabled()
}

fun Int.checkIsTextInputLayoutError(error: String) {
    matchView().checkIsTextInputLayoutError(error)
}

fun Int.checkIsTextInputLayoutErrorEnabled() {
    matchView().checkIsTextInputLayoutErrorEnabled()
}

fun Int.checkIsTextInputLayoutErrorDisabled() {
    matchView().checkIsTextInputLayoutErrorDisabled()
}

fun Int.checkIsTextInputLayoutCounterMaxLength(length: Int) {
    matchView().checkIsTextInputLayoutCounterMaxLength(length)
}

fun Int.checkIsTextInputLayoutCounterEnabled() {
    matchView().checkIsTextInputLayoutCounterEnabled()
}

fun Int.checkIsTextInputLayoutCounterDisabled() {
    matchView().checkIsTextInputLayoutCounterDisabled()
}

fun Int.checkIsImageViewDrawable(@DrawableRes resId: Int, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) {
    matchView().checkIsImageViewDrawable(resId, toBitmap)
}

fun Int.checkIsImageViewDrawable(drawable: Drawable, toBitmap: ((drawable: Drawable) -> Bitmap)? = null) {
    matchView().checkIsImageViewDrawable(drawable, toBitmap)
}

fun Int.checkIsSwipeRefreshLayoutRefreshing() {
    matchView().checkIsSwipeRefreshLayoutRefreshing()
}

fun Int.checkIsSwipeRefreshLayoutNotRefreshing() {
    matchView().checkIsSwipeRefreshLayoutNotRefreshing()
}

fun Int.checkIsBottomNavigationViewItemSelected(id: Int) {
    matchView().checkIsBottomNavigationViewItemSelected(id)
}

inline fun <reified T : View> Int.checkHasChild() {
    matchView().checkHasChild<T>()
}

inline fun <reified T : View> Int.checkIsAssignableFrom() {
    matchView().checkIsAssignableFrom<T>()
}


// from Agoda-Com/Kakao
private class IdleAction(val duration: Long = DEFAULT_DURATION) : ViewAction {
    companion object {
        const val DEFAULT_DURATION = 1000L
    }

    override fun getDescription() = "Idle for $duration milliseconds"

    override fun getConstraints() = ViewMatchers.isAssignableFrom(View::class.java)

    override fun perform(uiController: UiController?, view: View?) {
        uiController?.loopMainThreadForAtLeast(duration)
    }
}