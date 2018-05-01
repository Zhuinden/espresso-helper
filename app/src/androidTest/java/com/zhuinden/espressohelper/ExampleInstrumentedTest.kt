
package com.zhuinden.espressohelper

import android.support.constraint.ConstraintLayout
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zhuinden.espressohelper.conditionwatcher.ConditionWatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented Test2, which will execute on an Android device.
 *
 * See [Testing documentation](http://d.android.com/tools/Testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @JvmField
    @field:Rule
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickButton() {
        R.id.container.checkIsAssignableFrom<ConstraintLayout>()
        R.id.hello_world.checkHasText("Hello World!")
        R.id.username.performTypeText("This is a test!")
        R.id.username.performCloseSoftKeyboard()
        R.id.password.performTypeText("hunter2")
        R.id.password.performCloseSoftKeyboard()
        R.id.button.performClick()
        ConditionWatcher.waitForCondition { true }
    }
}