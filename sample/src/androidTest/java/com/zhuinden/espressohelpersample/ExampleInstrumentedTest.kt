
package com.zhuinden.espressohelpersample

import android.support.constraint.ConstraintLayout
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zhuinden.espressohelper.*
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
        val activity = rule.activity
        R.id.container.checkIsAssignableFrom<ConstraintLayout>()
        R.id.helloWorld.checkHasText(R.string.hello_world)
        R.id.username.performTypeText("This is a test!")
        R.id.password.performTypeText("hunter2")
        activity.rotateOrientation()
        activity.rotateOrientation()
        R.id.button.performClick()
        activity.rotateOrientation()
        R.id.secondText.checkHasText("Well done!")
        activity.rotateOrientation()
        checkCurrentActivityIs<SecondActivity>()

        rule.waitOnMainThread { callback ->
            // wait fomr something
            callback()
        }
    }
}