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
        // Moramo se prijaviti jer gosti ne mogu dodavati oglase (fab_add je blokiran)
        onView(withId(R.id.et_email))
            .perform(typeText("admin@koza.hr"), closeSoftKeyboard())
        onView(withId(R.id.et_password))
            .perform(typeText("1234"), closeSoftKeyboard())
        onView(withId(R.id.btn_login))
            .perform(click())

        // Provjera jesmo li na početnom ekranu
        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        // Klik na dodaj oglas (FAB)
        onView(withId(R.id.fab_add)).perform(click())

        // Ispunjavanje forme s automatskim skrolanjem
        onView(withId(R.id.et_naziv))
            .perform(scrollTo(), typeText("Test oglas v2"), closeSoftKeyboard())
        
        onView(withId(R.id.et_opis))
            .perform(scrollTo(), typeText("Automatski test v2"), closeSoftKeyboard())
            
        onView(withId(R.id.et_cijena))
            .perform(scrollTo(), typeText("99"), closeSoftKeyboard())
            
        onView(withId(R.id.et_lokacija))
            .perform(scrollTo(), typeText("Pula"), closeSoftKeyboard())

        onView(withId(R.id.chip_elektronika)).perform(scrollTo(), click())
        onView(withId(R.id.chip_kao_novo)).perform(scrollTo(), click())

        // Slanje oglasa
        onView(withId(R.id.btn_objavi)).perform(scrollTo(), click())

        // Čekamo da se baza osvježi i vrati nas na Home
        Thread.sleep(1000)

        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        // Navigacija na profil i odjava
        onView(withId(R.id.nav_profile)).perform(click())
        onView(withId(R.id.btn_odjavi)).perform(scrollTo(), click())

        // Provjera jesmo li se vratili na login
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}
