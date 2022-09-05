package com.jprudencio.shortly.ui.home

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.jprudencio.shortly.R
import com.jprudencio.shortly.ui.MainActivity
import com.jprudencio.shortly.ui.home.screens.ShortLinkListTestTag
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class HomeTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Test
    fun shortLinkHistoryIsVisible() {
        composeTestRule.onNodeWithTag(ShortLinkListTestTag).assertIsDisplayed()
        composeTestRule.onNodeWithText("http://shortly.com/1").assertIsDisplayed()
        composeTestRule.onNodeWithText("http://averylongurl.com/1/letstrytomakteit/verybig/indeed")
            .assertIsDisplayed()
    }

    @Test
    fun shortenInputUrlIsVisible() {
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.shortly_box_input_hint))
            .performTextInput("http://sampleurl.com")
        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.shortly_box_short_button))
            .assertIsDisplayed()
    }
}
