package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.lifecycleScope
import com.google.android.material.textfield.TextInputEditText
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {

    private val listaOglasa = mutableListOf<Oglas>()
    private var originalnaLista = listOf<Oglas>()
    private lateinit var adapter: OglasAdapter
    private lateinit var repository: OglasRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupNavigation()

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        val recyclerView = findViewById<RecyclerView>(R.id.rv_oglasi)
        val etSearch = findViewById<TextInputEditText>(R.id.et_search)

        adapter = OglasAdapter(listaOglasa, isMyAdsScreen = false) { oglas ->
            val intent = Intent(this, DetaljiOglasaActivity::class.java)
            intent.putExtra("OGLAS_ID", oglas.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter

        etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                filtriraj(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        osvjeziPodatke()
    }

    private fun filtriraj(query: String) {
        val filtrirano = originalnaLista.filter {
            it.naziv.contains(query, ignoreCase = true) || 
            it.lokacija.contains(query, ignoreCase = true) ||
            it.kategorija.contains(query, ignoreCase = true)
        }
        listaOglasa.clear()
        listaOglasa.addAll(filtrirano)
        adapter.notifyDataSetChanged()
    }

    private fun osvjeziPodatke() {
        lifecycleScope.launch {
            val entities = repository.getAllOglasi()
            if (entities.isNotEmpty() && entities[0].slikaUrl.isEmpty()) { ucitajDemoPodatke() }

            if (entities.isEmpty()) {
                ucitajDemoPodatke()
            } else {
                originalnaLista = entities.map { it.toDomain() }
                listaOglasa.clear()
                listaOglasa.addAll(originalnaLista)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun ucitajDemoPodatke() {
        val demoEntities = listOf(
            Oglas(
                id = "1", naziv = "Levi's 501 traperice",
                cijena = 25.0, lokacija = "Pula",
                kategorija = "Odjeća", velicina = "32/32",
                stanje = "Kao novo", wishlistCount = 12,
                opis = "Klasične originalne Levi's traperice, odlično očuvane.",
                slikaUrl = "levis_501"
            ),
            Oglas(
                id = "2", naziv = "iPhone 12 – 64GB",
                cijena = 280.0, lokacija = "Rovinj",
                kategorija = "Elektronika", velicina = "",
                stanje = "Dobro", wishlistCount = 5,
                opis = "iPhone 12, crne boje, zdravlje baterije 85%.",
                slikaUrl = "iphone_12"
            ),
            Oglas(
                id = "3", naziv = "Nike Air Max 90",
                cijena = 55.0, lokacija = "Poreč",
                kategorija = "Obuća", velicina = "42",
                stanje = "Kao novo", wishlistCount = 8,
                opis = "Nošene svega par puta, bez tragova korištenja.",
                slikaUrl = "nike_air_max"
            ),
            Oglas(
                id = "4", naziv = "Maslinovo ulje – domaće 5L",
                cijena = 35.0, lokacija = "Buje",
                kategorija = "Ostalo", velicina = "",
                stanje = "Novo s etiketom", wishlistCount = 3,
                opis = "Ekstra djevičansko maslinovo ulje, berba 2024.",
                slikaUrl = "maslinovo_ulje"
            ),
            Oglas(
                id = "5", naziv = "Bicikl Peugeot touring",
                cijena = 120.0, lokacija = "Pula",
                kategorija = "Sport", velicina = "",
                stanje = "Dobro", wishlistCount = 17,
                opis = "Vintage touring bicikl u voznom stanju.",
                slikaUrl = "peugeot_bicikl"
            ),
            Oglas(
                id = "6", naziv = "Zara haljina – cvjetni print",
                cijena = 15.0, lokacija = "Umag",
                kategorija = "Odjeća", velicina = "S",
                stanje = "Novo bez etikete", wishlistCount = 21,
                opis = "Lagana ljetna haljina, nikad nošena.",
                slikaUrl = "zara_haljina"
            )
        ).map { it.toEntity() }

        lifecycleScope.launch {
            repository.insertAll(demoEntities)
            osvjeziPodatke()
        }
    }

    override fun onResume() {
        super.onResume()
        osvjeziPodatke()
        bottomNav?.selectedItemId = R.id.nav_home
    }
}
