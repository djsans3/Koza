package com.example.koza // ← PROMIJENI u naziv svog paketa (npr. com.foi.koza)

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isEnabled
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * MEDIUM TEST – Validacija forme za dodavanje novog oglasa
 *
 * Ovaj medium test provjerava integraciju UI komponenti na ekranu
 * za dodavanje oglasa (AddItemActivity). Za razliku od unit testova koji
 * testiraju pojedine metode, ovaj test provjerava kako više UI elemenata
 * funkcionira zajedno – tj. kako forma reagira na korisničke akcije.
 *
 * Testira se:
 *   1. Jesu li svi UI elementi vidljivi pri otvaranju ekrana
 *   2. Što se dogodi kada korisnik klikne "Objavi" s praznim poljima
 *   3. Što se dogodi kada je ispunjeno samo jedno polje
 *   4. Jesu li sva polja ispravno popunjena (gumb aktivan)
 *
 * Preduvjet: espresso-core 3.6.1, junit 1.2.1 u androidTestImplementation.
 * Ako AddItemActivity zahtijeva prethodnu prijavu, pokreni LoginActivity u
 * @Rule i obavi prijavu u @Before metodi.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class AddItemValidationMediumTest {

    // Direktno pokrećemo ekran za dodavanje oglasa.
    // ← PROMIJENI AddItemActivity u pravi naziv svoje aktivnosti
    @get:Rule
    val activityRule = ActivityScenarioRule(AddItemActivity::class.java)

    // ─────────────────────────────────────────────────────────────────────────
    // Test 1 – Svi UI elementi su vidljivi pri otvaranju ekrana
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Provjera da su sva ključna polja forme i gumb vidljivi čim
     * se ekran otvori – bez ikakvog korisničkog unosa.
     */
    @Test
    fun test1_sviUIElementiSuVidljivi() {
        // Polje za naslov oglasa
        onView(withId(R.id.editTextNaslov))       // ← PROMIJENI ID prema svom projektu
            .check(matches(isDisplayed()))

        // Polje za opis oglasa
        onView(withId(R.id.editTextOpis))         // ← PROMIJENI ID
            .check(matches(isDisplayed()))

        // Polje za cijenu
        onView(withId(R.id.editTextCijena))       // ← PROMIJENI ID
            .check(matches(isDisplayed()))

        // Gumb za objavu mora biti vidljiv I aktivan
        onView(withId(R.id.buttonObjavi))         // ← PROMIJENI ID
            .check(matches(isDisplayed()))
            .check(matches(isEnabled()))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 2 – Klik na "Objavi" s potpuno praznim poljima
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Simulira korisnika koji odmah klikne "Objavi" bez ikakvog unosa.
     * Validacija forme mora zaustaviti objavu – korisnik ostaje na ekranu.
     * Potvrda: gumb "Objavi" i polje naslova i dalje su vidljivi,
     * što znači da nismo navigirali na drugi ekran.
     */
    @Test
    fun test2_objaviSaPraznimPoljima_ostajemoNaEkranu() {
        // Klik na gumb bez ikakvog prethodnog unosa
        onView(withId(R.id.buttonObjavi)).perform(click())

        // Ako je validacija ispravna, ostajemo na istom ekranu.
        // Gumb za objavu i polje za naslov moraju i dalje biti vidljivi.
        onView(withId(R.id.buttonObjavi))
            .check(matches(isDisplayed()))

        onView(withId(R.id.editTextNaslov))
            .check(matches(isDisplayed()))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 3 – Ispunjeno samo polje naslova, ostala prazna
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Korisnik ispuni samo naslov, ostavi opis i cijenu prazne, pa klikne
     * "Objavi". Forma ne smije dozvoliti objavu s nepotpunim podacima –
     * potvrđujemo da i dalje ostajemo na ekranu.
     */
    @Test
    fun test3_samoNaslovIspunjen_objaviJeBlokiran() {
        onView(withId(R.id.editTextNaslov))
            .perform(
                click(),
                typeText("Gitara Yamaha C40"),
                closeSoftKeyboard()
            )

        // Opis i cijena ostaju prazni → klik na gumb
        onView(withId(R.id.buttonObjavi)).perform(click())

        // Validacija mora zaustaviti objavu → ostajemo na ekranu
        onView(withId(R.id.editTextNaslov))
            .check(matches(isDisplayed()))
        onView(withId(R.id.buttonObjavi))
            .check(matches(isDisplayed()))
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Test 4 – Sva polja ispravno ispunjena → gumb je aktivan
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Korisnik ispuni sva obavezna polja. Nakon toga gumb "Objavi"
     * mora biti aktivan i klikabilan. Ovaj test ne provjerava stvarnu
     * mrežnu objavu – samo da UI ispravno reagira na potpun unos.
     */
    @Test
    fun test4_svaPoljaIspunjena_gumbOstajeAktivan() {
        onView(withId(R.id.editTextNaslov))
            .perform(
                click(),
                typeText("Gitara Yamaha C40"),
                closeSoftKeyboard()
            )

        onView(withId(R.id.editTextOpis))
            .perform(
                click(),
                typeText("Odlicno stanje, rijetko koristena."),
                closeSoftKeyboard()
            )

        onView(withId(R.id.editTextCijena))
            .perform(
                click(),
                typeText("150"),
                closeSoftKeyboard()
            )

        // Gumb mora biti aktivan s ispunjenim podacima
        onView(withId(R.id.buttonObjavi))
            .check(matches(isEnabled()))
    }
}
