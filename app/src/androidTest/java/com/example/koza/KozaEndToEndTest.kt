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

        // 1. Zaobiđi prijavu
        onView(withId(R.id.btn_preskoci)).perform(click())

        // 2. HomeActivity prikazuje oglase
        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        // 3. Otvori ekran za dodavanje (FAB)
        onView(withId(R.id.fab_add)).perform(click())

        // 4. Unesi podatke
        onView(withId(R.id.et_naziv)).perform(typeText("Test oglas v2"), closeSoftKeyboard())
        onView(withId(R.id.et_opis)).perform(typeText("Automatski test v2"), closeSoftKeyboard())
        onView(withId(R.id.et_cijena)).perform(typeText("99"), closeSoftKeyboard())
        onView(withId(R.id.et_lokacija)).perform(typeText("Pula"), closeSoftKeyboard())

        // Kategorija i stanje
        onView(withId(R.id.chip_elektronika)).perform(click())
        onView(withId(R.id.chip_kao_novo)).perform(click())

        // 5. Objavi (skrolaj jer ga navbar možda prekriva)
        onView(withId(R.id.btn_objavi)).perform(scrollTo(), click())

        // Čekaj kratko da se vrati na HomeActivity
        Thread.sleep(1000)

        // 6. Povratak na Home
        onView(withId(R.id.rv_oglasi)).check(matches(isDisplayed()))

        // 7. Navigiraj na Profil
        onView(withId(R.id.nav_profile)).perform(click())

        // 8. Odjavi se
        onView(withId(R.id.btn_odjavi)).perform(click())

        // 9. Provjera povratka na Login
        onView(withId(R.id.btn_login)).check(matches(isDisplayed()))
    }
}
