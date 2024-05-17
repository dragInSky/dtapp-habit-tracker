package com.example.view

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.view.view.editscreen.EditScreen
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditScreenUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun editScreenUITest() {
        val testApp = App()

        composeTestRule.setContent {
            EditScreen({})
        }

        composeTestRule.onNodeWithTag("topBar").assertTextEquals("Edit")
    }
}