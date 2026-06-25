package com.example.koza

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class DetaljiOglasaActivity : AppCompatActivity() {

    private lateinit var repository: OglasRepository
    private var oglasId: String? = null
    private var currentOglas: Oglas? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalji_oglasa)

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        oglasId = intent.getStringExtra("OGLAS_ID")

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        ucitajPodatke()
    }

    private fun ucitajPodatke() {
        val id = oglasId ?: return
        lifecycleScope.launch {
            val entity = repository.getOglasById(id)
            if (entity != null) {
                currentOglas = entity.toDomain()
                prikaziOglas(currentOglas!!)
            } else {
                Toast.makeText(this@DetaljiOglasaActivity, "Oglas nije pronađen", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    private fun prikaziOglas(oglas: Oglas) {
        findViewById<TextView>(R.id.tv_naziv_detalji).text = oglas.naziv
        findViewById<TextView>(R.id.tv_cijena_detalji).text = String.format("%.2f €", oglas.cijena)
        findViewById<TextView>(R.id.tv_opis_detalji).text = if (oglas.opis.isEmpty()) "Nema opisa." else oglas.opis
        findViewById<TextView>(R.id.tv_lokacija_detalji).text = oglas.lokacija
        findViewById<TextView>(R.id.tv_kategorija_detalji).text = oglas.kategorija
        findViewById<TextView>(R.id.tv_velicina_detalji).text = if (oglas.velicina.isEmpty()) "-" else oglas.velicina
        findViewById<TextView>(R.id.tv_stanje_detalji).text = oglas.stanje

        if (oglas.slikaUrl.isNotEmpty()) {
            val resourceId = resources.getIdentifier(oglas.slikaUrl, "drawable", packageName)
            if (resourceId != 0) {
                findViewById<ImageView>(R.id.iv_oglas_velika).setImageResource(resourceId)
            } else {
                findViewById<ImageView>(R.id.iv_oglas_velika).setImageResource(R.drawable.koza404)
            }
        } else {
            findViewById<ImageView>(R.id.iv_oglas_velika).setImageResource(R.drawable.koza404)
        }

        val btnOmiljeni = findViewById<ImageButton>(R.id.btn_omiljeni_detalji)
        
        if (oglas.isMyAd) {
            btnOmiljeni.setImageResource(R.drawable.ic_delete)
            btnOmiljeni.setColorFilter(android.graphics.Color.parseColor("#E53935"))
            btnOmiljeni.setOnClickListener {
                obrisiOglas()
            }
        } else {
            updateFavoriteButton(oglas.isFavorite)
            btnOmiljeni.setOnClickListener {
                toggleFavorite()
            }
        }

        findViewById<MaterialButton>(R.id.btn_kontakt_detalji).setOnClickListener {
            Toast.makeText(this, "Kontaktiranje prodavača ${oglas.korisnikIme}...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun obrisiOglas() {
        val id = oglasId ?: return
        lifecycleScope.launch {
            repository.deleteById(id)
            Toast.makeText(this@DetaljiOglasaActivity, "Oglas obrisan", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun toggleFavorite() {
        val oglas = currentOglas ?: return
        val noviStatus = !oglas.isFavorite
        lifecycleScope.launch {
            val updatedOglas = oglas.copy(isFavorite = noviStatus)
            repository.updateOglas(updatedOglas.toEntity())
            currentOglas = updatedOglas
            updateFavoriteButton(noviStatus)
            
            val poruka = if (noviStatus) "Dodano u omiljene 💛" else "Uklonjeno iz omiljenih"
            Toast.makeText(this@DetaljiOglasaActivity, poruka, Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateFavoriteButton(isFavorite: Boolean) {
        val btnOmiljeni = findViewById<ImageButton>(R.id.btn_omiljeni_detalji)
        if (isFavorite) {
            btnOmiljeni.setImageResource(R.drawable.ic_heart_filled)
        } else {
            btnOmiljeni.setImageResource(R.drawable.ic_heart_outline)
        }
    }
}
