package com.example.koza

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeActivity : BaseActivity() {

    private val listaOglasa = mutableListOf<Oglas>()
    private lateinit var adapter: OglasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupNavigation()

        val recyclerView = findViewById<RecyclerView>(R.id.rv_oglasi)

        adapter = OglasAdapter(listaOglasa) { oglas ->
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        ucitajDemoPodatke()
    }

    private fun ucitajDemoPodatke() {
        listaOglasa.clear()
        listaOglasa.addAll(listOf(
            Oglas(
                id = "1", naziv = "Levi's 501 traperice",
                cijena = 25.0, lokacija = "Pula",
                kategorija = "Odjeća", velicina = "32/32",
                stanje = "Kao novo", wishlistCount = 12
            ),
            Oglas(
                id = "2", naziv = "iPhone 12 – 64GB",
                cijena = 280.0, lokacija = "Rovinj",
                kategorija = "Elektronika", velicina = "",
                stanje = "Dobro", wishlistCount = 5
            ),
            Oglas(
                id = "3", naziv = "Nike Air Max 90",
                cijena = 55.0, lokacija = "Poreč",
                kategorija = "Obuća", velicina = "42",
                stanje = "Kao novo", wishlistCount = 8
            ),
            Oglas(
                id = "4", naziv = "Maslinovo ulje – domaće 5L",
                cijena = 35.0, lokacija = "Buje",
                kategorija = "Ostalo", velicina = "",
                stanje = "Novo s etiketom", wishlistCount = 3
            ),
            Oglas(
                id = "5", naziv = "Bicikl Peugeot touring",
                cijena = 120.0, lokacija = "Pula",
                kategorija = "Sport", velicina = "",
                stanje = "Dobro", wishlistCount = 17
            ),
            Oglas(
                id = "6", naziv = "Zara haljina – cvjetni print",
                cijena = 15.0, lokacija = "Umag",
                kategorija = "Odjeća", velicina = "S",
                stanje = "Novo bez etikete", wishlistCount = 21
            )
        ))
        adapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        bottomNav.menu.findItem(R.id.nav_home).isChecked = true
    }
}
