package com.zhuinden.espressohelpersample

import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zhuinden.espressohelper.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TextActivityTest {
    @JvmField
    @field:Rule
    var rule = ActivityTestRule(TextActivity::class.java)

    @Test
    fun hasEmptyText(){
        R.id.tv_empty_text_view.checkHasEmptyText()
    }

    @Test
    fun hasEmptyTextFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkHasEmptyText() }
    }

    @Test
    fun hasAnyText(){
        R.id.tv_sample_text_view.checkHasAnyText()
    }

    @Test
    fun hasAnyTextFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkHasAnyText() }
    }

    @Test
    fun containsText(){
        R.id.tv_sample_text_view.checkContainsText("Hello")
    }

    @Test
    fun containsTextFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkContainsText("Halo") }
    }

    @Test
    fun startsWithText(){
        R.id.tv_sample_text_view.checkStartsWithText("Hello")
    }

    @Test
    fun startsWithTextFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkStartsWithText("World!") }
    }

    @Test
    fun hasSpecificText(){
        R.id.tv_sample_text_view.checkHasText("Hello world!")
    }

    @Test
    fun hasSpecificTextFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkHasText("Halo") }
    }

    @Test
    fun hasSpecificTextWithId(){
        R.id.tv_sample_text_view.checkHasText(R.string.hello_world)
    }

    @Test
    fun hasSpecificTextWithIdFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkHasText(R.string.app_name) }
    }

    @Test
    fun doesNotContainSpecifiedText(){
        R.id.tv_empty_text_view.checkHasNoText("Sample")
    }

    @Test
    fun doesNotContainSpecifiedTextFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkHasNoText("Hello world!") }
    }

    @Test
    fun doesNotContainSpecifiedTextWithId(){
        R.id.tv_empty_text_view.checkHasNoText(R.string.app_name)
    }

    @Test
    fun doesNotContainSpecifiedTextWithIdFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkHasNoText(R.string.hello_world) }
    }

    @Test
    fun hasContentDescription(){
        R.id.iv_image_view.checkHasContentDescription("espresso-helper")
    }

    @Test
    fun hasContentDescriptionFailure(){
        verifyAssertion { R.id.iv_image_view.checkHasContentDescription("Halo") }
    }
}