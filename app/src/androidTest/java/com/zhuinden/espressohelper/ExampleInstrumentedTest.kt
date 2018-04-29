
package com.zhuinden.espressohelper

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.*
import android.support.test.espresso.action.*
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4

import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Test

/**
 * Instrumented Test2, which will execute on an Android device.
 *
 * See [Test2ing documentation](http://d.android.com/tools/Test2ing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under Test2.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.zhuinden.espressohelper", appContext.packageName)

        //Espresso.onView(ViewMatchers.withId(R.id.button)).perform(ViewActions.click())
        R.id.button.performClick()
    }
}