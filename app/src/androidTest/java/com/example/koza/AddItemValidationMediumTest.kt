package com.example.koza

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class AddItemValidationMediumTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(AddItemActivity::class.java)

    @Test
    fun test1_sviUIElementiSuVidljivi() {
        onView(withId(R.id.et_naziv))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withId(R.id.et_opis))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withId(R.id.et_cijena))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withId(R.id.btn_objavi))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }

    @Test
    fun test2_objaviSaPraznimPoljima_ostajemoNaEkranu() {
        onView(withId(R.id.btn_objavi))
            .perform(scrollTo(), click())

        onView(withId(R.id.btn_objavi))
            .perform(scrollTo())
            .check(matches(isDisplayed()))

        onView(withId(R.id.et_naziv))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun test3_samoNaslovIspunjen_objaviJeBlokiran() {
        onView(withId(R.id.et_naziv))
            .perform(
                scrollTo(),
                click(),
                typeText("Gitara Yamaha C40"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.btn_objavi))
            .perform(scrollTo(), click())

        onView(withId(R.id.et_naziv))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
            
        onView(withId(R.id.btn_objavi))
            .perform(scrollTo())
            .check(matches(isDisplayed()))
    }

    @Test
    fun test4_svaPoljaIspunjena_gumbOstajeAktivan() {
        onView(withId(R.id.et_naziv))
            .perform(
                scrollTo(),
                click(),
                typeText("Gitara Yamaha C40"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.et_opis))
            .perform(
                scrollTo(),
                click(),
                typeText("Odlicno stanje, rijetko koristena."),
                closeSoftKeyboard()
            )

        onView(withId(R.id.et_cijena))
            .perform(
                scrollTo(),
                click(),
                typeText("150"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.btn_objavi))
            .perform(scrollTo())
            .check(matches(isEnabled()))
    }
}
