package com.example.koza

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationEndToEndTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun test_puniNavigacijskiTok() {
        onView(withId(R.id.et_email))
            .perform(typeText("admin@koza.hr"), closeSoftKeyboard())
        onView(withId(R.id.et_password))
            .perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .perform(click())

        onView(withId(R.id.nav_profile))
            .perform(click())

        onView(withId(R.id.tv_ime))
            .check(matches(isDisplayed()))

        onView(withId(R.id.tv_email))
            .check(matches(isDisplayed()))

        onView(withId(R.id.nav_home))
            .perform(click())

        onView(withId(R.id.rv_oglasi))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )

        onView(withId(R.id.rv_oglasi))
            .check(matches(isDisplayed()))
    }
}
