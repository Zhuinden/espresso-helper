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
    fun hasAnyText(){
        R.id.tv_sample_text_view.checkHasAnyText()
    }

    @Test
    fun containsText(){
        R.id.tv_sample_text_view.checkContainsText("Hello")
    }

    @Test
    fun startsWithText(){
        R.id.tv_sample_text_view.checkStartsWithText("Hello")
    }

    @Test
    fun hasSpecificText(){
        R.id.tv_sample_text_view.checkHasText("Hello world!")
    }

    @Test
    fun hasSpecificTextWithId(){
        R.id.tv_sample_text_view.checkHasText(R.string.hello_world)
    }

    @Test
    fun doesNotContainSpecifiedText(){
        R.id.tv_empty_text_view.checkHasNoText("Sample")
    }

    @Test
    fun doesNotContainSpecifiedTextWithId(){
        R.id.tv_empty_text_view.checkHasNoText(R.string.app_name)
    }

    @Test
    fun hasContentDescription(){
        R.id.iv_image_view.checkHasContentDescription("espresso-helper")
    }
}