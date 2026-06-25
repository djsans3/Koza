package com.example.koza // ← PROMIJENI u naziv svog paketa (npr. com.foi.koza)

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

/**
 * END-TO-END TEST – Navigacijski korisnički tok
 *
 * Ovaj E2E test provjerava cjelokupni navigacijski tok koji korisnik
 * prolazi unutar aplikacije – od prijave, prebacivanja tabova,
 * pregleda profila, do skrolanja liste oglasa na početnom ekranu.
 *
 * Za razliku od KozaEndToEndTest koji testira tok KREIRANJA i brisanja
 * oglasa, ovaj test fokus stavlja isključivo na NAVIGACIJU kroz aplikaciju.
 *
 * Testirani korisnički tok:
 *   Korak 1 – Prijava u aplikaciju
 *   Korak 2 – Provjera prikaza Home ekrana (lista oglasa vidljiva)
 *   Korak 3 – Prebacivanje na Profile tab donjom navigacijskom trakom
 *   Korak 4 – Provjera prikaza korisničkih podataka na ekranu Profila
 *   Korak 5 – Povratak na Home tab donjom navigacijskom trakom
 *   Korak 6 – Skrolanje RecyclerViewa na vrh i provjera vidljivosti liste
 *
 * Preduvjeti:
 *   - espresso-core 3.6.1 + espresso-contrib 3.6.1 u androidTestImplementation
 *   - junit 1.2.1 u androidTestImplementation
 *   - Testni korisnički račun mora postojati u bazi (email + lozinka ispod)
 *
 * Gradle dependency za RecyclerViewActions:
 *   androidTestImplementation("androidx.test.espresso:espresso-contrib:3.6.1")
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class NavigationEndToEndTest {

    // Test počinje od ekrana za prijavu.
    // ← PROMIJENI LoginActivity u pravi naziv svoje aktivnosti za prijavu
    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun test_puniNavigacijskiTok() {

        // ── Korak 1: Prijava u aplikaciju ────────────────────────────────────
        //
        // Unosimo e-mail i lozinku testnog korisnika te pritiskamo gumb za
        // prijavu. Ovaj korisnik mora postojati u bazi podataka aplikacije.

        onView(withId(R.id.editTextEmail))          // ← PROMIJENI ID
            .perform(typeText("admin@koza.hr"), closeSoftKeyboard())

        onView(withId(R.id.editTextPassword))       // ← PROMIJENI ID
            .perform(typeText("1234"), closeSoftKeyboard())

        onView(withId(R.id.buttonPrijava))          // ← PROMIJENI ID
            .perform(click())

        // ── Korak 2: Provjera Home ekrana ────────────────────────────────────
        //
        // Nakon uspješne prijave moramo biti na Home ekranu.
        // RecyclerView s listom oglasa mora biti vidljiv.

        onView(withId(R.id.recyclerViewOglasi))     // ← PROMIJENI ID
            .check(matches(isDisplayed()))

        // ── Korak 3: Prebacivanje na Profile tab ─────────────────────────────
        //
        // Klikamo na ikonu Profila u donjoj navigacijskoj traci (BottomNavigationView).
        // Ako tvoja aplikacija koristi NavigationDrawer umjesto BottomNav,
        // zamijeni ovaj korak s otvaranjem drawera i odabirom opcije Profil.

        onView(withId(R.id.nav_profil))             // ← PROMIJENI ID (item u BottomNavigationView)
            .perform(click())

        // ── Korak 4: Provjera Profile ekrana ─────────────────────────────────
        //
        // Na ekranu Profila moraju biti vidljivi elementi koji prikazuju
        // podatke prijavljenog korisnika (korisničko ime i e-mail adresa).

        onView(withId(R.id.textViewImeKorisnika))   // ← PROMIJENI ID
            .check(matches(isDisplayed()))

        onView(withId(R.id.textViewEmailKorisnika)) // ← PROMIJENI ID
            .check(matches(isDisplayed()))

        // ── Korak 5: Povratak na Home tab ────────────────────────────────────
        //
        // Klikamo na ikonu Home u donjoj navigacijskoj traci kako bismo
        // se vratili na početni ekran s listom oglasa.

        onView(withId(R.id.nav_pocetna))            // ← PROMIJENI ID (item u BottomNavigationView)
            .perform(click())

        // ── Korak 6: Skrolanje RecyclerViewa i provjera vidljivosti ──────────
        //
        // Skrolamo RecyclerView na prvu poziciju (pozicija 0 = vrh liste).
        // Ovo simulira korisnika koji se vraća na vrh feed-a oglasa.
        // RecyclerViewActions.scrollToPosition ne zahtijeva da lista
        // ima točno određeni broj elemenata – radi čak i s jednim.

        onView(withId(R.id.recyclerViewOglasi))
            .perform(
                RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0)
            )

        // Konačna provjera: lista oglasa mora biti vidljiva
        // i korisnik mora biti na Home ekranu (nije navigirao dalje).
        onView(withId(R.id.recyclerViewOglasi))
            .check(matches(isDisplayed()))
    }
}
