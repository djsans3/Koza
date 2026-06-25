package com.example.koza

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFlowMediumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun loginActivity_prikazujeOcekivaneKomponente() {
        onView(withId(R.id.et_email)).check(matches(isDisplayed()))
        onView(withId(R.id.et_password)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_preskoci)).check(matches(isDisplayed()))
    }

    @Test
    fun login_sPraznimPoljima_ostajeNaLoginActivity() {
        onView(withId(R.id.et_email)).perform(clearText())
        onView(withId(R.id.et_password)).perform(clearText())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.et_email)).check(matches(isDisplayed()))
    }

    @Test
    fun login_sUnesenimPodacima_navigiraUHome() {
        onView(withId(R.id.et_email))
            .perform(typeText("admin@koza.hr"), closeSoftKeyboard())

        onView(withId(R.id.et_password))
            .perform(typeText("1234"), closeSoftKeyboard())

        onView(withId(R.id.btn_login)).perform(click())

        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))
    }

    @Test
    fun preskoci_navigiraUHomeActivity() {
        onView(withId(R.id.btn_preskoci)).perform(click())
        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))
    }
}
