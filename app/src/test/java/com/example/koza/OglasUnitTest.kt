package com.example.koza

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test


class OglasUnitTest {

    @Test
    fun `getteri vracaju tocne vrijednosti`() {
        val oglas = Oglas(
            id         = "42",
            naziv      = "Levi's 501 traperice",
            opis       = "Malo nošene, bez oštećenja",
            cijena     = 25.0,
            lokacija   = "Pula",
            kategorija = "Odjeća",
            velicina   = "32/32",
            stanje     = "Kao novo",
            korisnikId = "user_01",
            wishlistCount = 7
        )

        assertEquals("Getter 'id' mora vratiti '42'",           "42",            oglas.id)
        assertEquals("Getter 'naziv' mora biti ispravan",       "Levi's 501 traperice", oglas.naziv)
        assertEquals("Getter 'cijena' mora biti 25.0",          25.0,            oglas.cijena, 0.001)
        assertEquals("Getter 'lokacija' mora biti 'Pula'",      "Pula",          oglas.lokacija)
        assertEquals("Getter 'kategorija' mora biti 'Odjeća'",  "Odjeća",        oglas.kategorija)
        assertEquals("Getter 'velicina' mora biti '32/32'",     "32/32",         oglas.velicina)
        assertEquals("Getter 'stanje' mora biti 'Kao novo'",    "Kao novo",      oglas.stanje)
        assertEquals("Getter 'wishlistCount' mora biti 7",      7,               oglas.wishlistCount)
    }

    @Test
    fun `defaultni konstruktor postavlja prazne stringove i nulu`() {
        val oglas = Oglas()

        assertEquals("Default 'id' mora biti prazan string",          "", oglas.id)
        assertEquals("Default 'naziv' mora biti prazan string",       "", oglas.naziv)
        assertEquals("Default 'cijena' mora biti 0.0",                0.0, oglas.cijena, 0.001)
        assertEquals("Default 'kategorija' mora biti prazan string",  "", oglas.kategorija)
        assertEquals("Default 'velicina' mora biti prazan string",    "", oglas.velicina)
        assertEquals("Default 'stanje' mora biti prazan string",      "", oglas.stanje)
        assertEquals("Default 'wishlistCount' mora biti 0",           0, oglas.wishlistCount)
        assertTrue("Default 'datum' mora biti > 0",                   oglas.datum > 0L)
    }

    private fun validirajOglas(oglas: Oglas): Boolean {
        if (oglas.naziv.isBlank())   return false
        if (oglas.cijena <= 0.0)     return false
        if (oglas.lokacija.isBlank()) return false
        return true
    }

    @Test/*
    fun `validan oglas prolazi validaciju`() {
        val oglas = Oglas(
            naziv    = "Nike Air Max",
            cijena   = 55.0,
            lokacija = "Split"
        )
        assertTrue("Oglas s ispravnim podacima mora proći validaciju", validirajOglas(oglas))
    }

    @Test
    fun `oglas s praznim nazivom ne prolazi validaciju`() {
        val oglas = Oglas(naziv = "", cijena = 50.0, lokacija = "Rijeka")
        assertFalse("Oglas s praznim nazivom NE smije proći validaciju", validirajOglas(oglas))
    }

    @Test
    fun `oglas s cijenom nula ne prolazi validaciju`() {
        val oglas = Oglas(naziv = "Cipele", cijena = 0.0, lokacija = "Zagreb")
        assertFalse("Oglas s cijenom 0 NE smije proći validaciju", validirajOglas(oglas))
    }

    @Test
    fun `oglas s negativnom cijenom ne prolazi validaciju`() {
        val oglas = Oglas(naziv = "Cipele", cijena = -5.0, lokacija = "Zagreb")
        assertFalse("Oglas s negativnom cijenom NE smije proći validaciju", validirajOglas(oglas))
    }

    @Test
    fun `oglas s praznom lokacijom ne prolazi validaciju`() {
        val oglas = Oglas(naziv = "Bicikl", cijena = 120.0, lokacija = "")
        assertFalse("Oglas s praznom lokacijom NE smije proći validaciju", validirajOglas(oglas))
    }
    */
    @Test
    fun `copy metoda ispravno mijenja jednu vrijednost`() {
        val original = Oglas(naziv = "Originalni naziv", cijena = 10.0, lokacija = "Osijek")
        val kopija   = original.copy(cijena = 20.0)

        assertEquals("Naziv mora ostati isti nakon copy()",           "Originalni naziv", kopija.naziv)
        assertEquals("Cijena mora biti promijenjena na 20.0",         20.0, kopija.cijena, 0.001)
        assertEquals("Lokacija mora ostati ista nakon copy()",        "Osijek", kopija.lokacija)
    }

    @Test
    fun `buildString s velicinom i stanjem spaja ih tockom i razmakom`() {
        val oglas = Oglas(velicina = "M", stanje = "Kao novo")

        val velicinaStanje = buildString {
            if (oglas.velicina.isNotEmpty()) append(oglas.velicina)
            if (oglas.velicina.isNotEmpty() && oglas.stanje.isNotEmpty()) append(" · ")
            if (oglas.stanje.isNotEmpty()) append(oglas.stanje)
        }

        assertEquals("Format mora biti 'M · Kao novo'", "M · Kao novo", velicinaStanje)
    }

    @Test
    fun `buildString bez velicine prikazuje samo stanje`() {
        val oglas = Oglas(velicina = "", stanje = "Dobro")

        val velicinaStanje = buildString {
            if (oglas.velicina.isNotEmpty()) append(oglas.velicina)
            if (oglas.velicina.isNotEmpty() && oglas.stanje.isNotEmpty()) append(" · ")
            if (oglas.stanje.isNotEmpty()) append(oglas.stanje)
        }

        assertEquals("Format mora biti samo 'Dobro'", "Dobro", velicinaStanje)
    }
}
