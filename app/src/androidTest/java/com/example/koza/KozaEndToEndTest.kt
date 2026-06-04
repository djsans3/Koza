package com.example.koza

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class KozaEndToEndTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun cjelovitiKorisnickiTok_v2() {

        onView(withId(R.id.btn_preskoci)).perform(click())

        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        onView(withId(R.id.fab_add)).perform(click())

        onView(withId(R.id.et_naziv)).perform(typeText("Test oglas v2"), closeSoftKeyboard())
        onView(withId(R.id.et_opis)).perform(typeText("Automatski test v2"), closeSoftKeyboard())
        onView(withId(R.id.et_cijena)).perform(typeText("99"), closeSoftKeyboard())
        onView(withId(R.id.et_lokacija)).perform(typeText("Pula"), closeSoftKeyboard())

        onView(withId(R.id.chip_elektronika)).perform(click())
        onView(withId(R.id.chip_kao_novo)).perform(click())

        onView(withId(R.id.btn_objavi)).perform(scrollTo(), click())

        Thread.sleep(1000)

        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        onView(withId(R.id.nav_profile)).perform(click())

        onView(withId(R.id.btn_odjavi)).perform(click())

        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}
