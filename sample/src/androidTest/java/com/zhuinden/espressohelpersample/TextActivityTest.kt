package com.zhuinden.espressohelpersample

import androidx.test.rule.ActivityTestRule
import com.zhuinden.espressohelper.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(androidx.test.ext.junit.runners.AndroidJUnit4::class)
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

    @Test
    fun hasHintWithId(){
        R.id.ed_edit_text.checkHasHint(R.string.hello_world)
    }

    @Test
    fun hasHintWithIdFailure(){
        verifyAssertion { R.id.ed_edit_text.checkHasHint(R.string.app_name) }
    }

    @Test
    fun hasHintWithString(){
        R.id.ed_edit_text.checkHasHint("Hello world!")
    }

    @Test
    fun hasHintWithStringFailure(){
        verifyAssertion { R.id.ed_edit_text.checkHasHint("Halo") }
    }

    @Test
    fun isChecked(){
        R.id.cb_show_profile.checkIsChecked()
    }

    @Test
    fun isCheckedFailure(){
        verifyAssertion { R.id.cb_suggest.checkIsChecked() }
    }

    @Test
    fun isNotChecked(){
        R.id.cb_suggest.checkIsNotChecked()
    }

    @Test
    fun isNotCheckedFailure(){
        verifyAssertion { R.id.cb_show_profile.checkIsNotChecked() }
    }

    @Test
    fun isVisible(){
        R.id.iv_image_view.checkIsVisible()
    }

    @Test
    fun isVisibleFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkIsVisible() }
    }

    @Test
    fun isNotVisible(){
        R.id.tv_empty_text_view.checkIsInvisible()
    }

    @Test
    fun isNotVisibleFailure(){
        verifyAssertion { R.id.iv_image_view.checkIsInvisible() }
    }

    @Test
    fun isGone(){
        R.id.iv_rating.checkIsGone()
    }

    @Test
    fun isGoneFailure(){
        verifyAssertion { R.id.iv_image_view.checkIsGone() }
    }

    @Test
    fun isFocusable(){
        R.id.ed_edit_text.checkIsFocusable()
    }

    @Test
    fun isFocusableFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkIsFocusable() }
    }

    @Test
    fun isNotFocusable(){
        R.id.tv_empty_text_view.checkIsNotFocusable()
    }

    @Test
    fun isNotFocusableFailure(){
        verifyAssertion { R.id.ed_edit_text.checkIsNotFocusable() }
    }

    @Test
    fun isFocused(){
        R.id.ed_edit_text.checkIsFocused()
    }

    @Test
    fun isFocusedFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkIsFocused() }
    }

    @Test
    fun isNotFocused(){
        R.id.tv_empty_text_view.checkIsNotFocused()
    }

    @Test
    fun isNotFocusedFailure(){
        verifyAssertion { R.id.ed_edit_text.checkIsNotFocused() }
    }

    @Test
    fun isClickable(){
        R.id.iv_image_view.checkIsClickable()
    }

    @Test
    fun isClickableFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkIsClickable() }
    }

    @Test
    fun isNotClickable(){
        R.id.tv_empty_text_view.checkIsNotClickable()
    }

    @Test
    fun isNotClickableFailure(){
        verifyAssertion { R.id.iv_image_view.checkIsNotClickable() }
    }

    @Test
    fun isEnabled(){
        R.id.iv_image_view.checkIsEnabled()
    }

    @Test
    fun isEnabledFailure(){
        verifyAssertion { R.id.tv_empty_text_view.checkIsEnabled() }
    }

    @Test
    fun isDisabled(){
        R.id.tv_empty_text_view.checkIsDisabled()
    }

    @Test
    fun isDisabledFailure(){
        verifyAssertion { R.id.iv_image_view.checkIsDisabled() }
    }

    @Test
    fun hasSpecificTag(){
        R.id.iv_image_view.checkHasTag("Hello world!")
    }

    @Test
    fun hasSpecificTagFailure(){
        verifyAssertion { R.id.iv_image_view.checkHasTag("Halo") }
    }

    @Test
    fun hasAnyTag(){
        R.id.iv_image_view.checkHasAnyTag("Hello world!", "Halo")
    }

    @Test
    fun hasAnyTagFailure(){
        verifyAssertion { R.id.ed_edit_text.checkHasAnyTag("Halo") }
    }

    @Test
    fun doesNotExists(){
        val invalidId = -5
        invalidId.checkDoesNotExist()
    }

    @Test
    fun doesNotExistsFailure(){
        verifyAssertion { R.id.tv_sample_text_view.checkDoesNotExist() }
    }

    @Test
    fun hasBackgroundColorId(){
        R.id.iv_image_view.checkHasBackgroundColor(R.color.colorPrimary)
    }

    @Test
    fun hasBackgroundColorIdFailure(){
        verifyAssertion { R.id.iv_image_view.checkHasBackgroundColor(R.color.colorPrimaryDark) }
    }

    @Test
    fun hasBackgroundColorText(){
        R.id.iv_image_view.checkHasBackgroundColor("#3F51B5")
    }

    @Test
    fun hasBackgroundColorTextFailure(){
        verifyAssertion { R.id.iv_image_view.checkHasBackgroundColor("#303F9F") }
    }
}