
package com.zhuinden.espressohelpersample

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.test.rule.ActivityTestRule
import com.zhuinden.espressohelper.*
import com.zhuinden.espressohelpersample.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented Test2, which will execute on an Android device.
 *
 * See [Testing documentation](http://d.android.com/tools/Testing).
 */
@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @JvmField
    @field:Rule
    var rule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun clickButton() {
        val activity = rule.activity
        container.checkIsAssignableFrom<ConstraintLayout>()
        helloWorld.checkHasText(R.string.hello_world)
        username.performTypeText("This is a test!")
        password.performTypeText("hunter2")

        button.performClick()
        checkCurrentActivityIs<SecondActivity>()
        activity.rotateOrientation()
        secondText.checkHasText("Well done!")
        activity.rotateOrientation()

        rule.waitOnMainThread { callback ->
            // wait fomr something
            callback()
        }
    }
}