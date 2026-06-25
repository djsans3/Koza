package com.example.koza

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.ChipGroup
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import java.util.UUID

class AddItemActivity : BaseActivity() {

    private lateinit var repository: OglasRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        setupNavigation()

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        val etNaziv = findViewById<TextInputEditText>(R.id.et_naziv)
        val etOpis = findViewById<TextInputEditText>(R.id.et_opis)
        val etCijena = findViewById<TextInputEditText>(R.id.et_cijena)
        val etLokacija = findViewById<TextInputEditText>(R.id.et_lokacija)
        val chipGroupKategorija = findViewById<ChipGroup>(R.id.chip_group_kategorija)
        val chipGroupStanje = findViewById<ChipGroup>(R.id.chip_group_stanje)
        val chipGroupVelicinaOdjeca = findViewById<ChipGroup>(R.id.chip_group_velicina_odjeca)
        val layoutVelicinaOdjeca = findViewById<View>(R.id.layout_velicina_odjeca)
        val layoutVelicinaObuca = findViewById<View>(R.id.layout_velicina_obuca)
        val etVelicinaObuca = findViewById<TextInputEditText>(R.id.et_velicina_obuca)
        val btnObjavi = findViewById<MaterialButton>(R.id.btn_objavi)

        chipGroupKategorija.setOnCheckedStateChangeListener { _, checkedIds ->
            val selectedId = checkedIds.firstOrNull()
            layoutVelicinaOdjeca.visibility = View.GONE
            layoutVelicinaObuca.visibility = View.GONE

            when (selectedId) {
                R.id.chip_odjeća -> layoutVelicinaOdjeca.visibility = View.VISIBLE
                R.id.chip_obuca -> layoutVelicinaObuca.visibility = View.VISIBLE
            }
        }

        btnObjavi.setOnClickListener {
            val naziv = etNaziv.text.toString().trim()
            val opis = etOpis.text.toString().trim()
            val cijenaStr = etCijena.text.toString().trim()
            val lokacija = etLokacija.text.toString().trim()

            if (naziv.isEmpty()) { etNaziv.error = "Unesite naziv"; return@setOnClickListener }
            if (cijenaStr.isEmpty()) { etCijena.error = "Unesite cijenu"; return@setOnClickListener }
            if (lokacija.isEmpty()) { etLokacija.error = "Unesite lokaciju"; return@setOnClickListener }

            val kategorija = when (chipGroupKategorija.checkedChipId) {
                R.id.chip_odjeća -> "Odjeća"
                R.id.chip_obuca -> "Obuća"
                R.id.chip_elektronika -> "Elektronika"
                R.id.chip_namjestaj -> "Namještaj"
                R.id.chip_alati -> "Alati"
                else -> "Ostalo"
            }

            val velicina = when {
                layoutVelicinaOdjeca.visibility == View.VISIBLE -> {
                    when (chipGroupVelicinaOdjeca.checkedChipId) {
                        R.id.chip_xs -> "XS"; R.id.chip_s -> "S"; R.id.chip_m -> "M"
                        R.id.chip_l -> "L"; R.id.chip_xl -> "XL"; R.id.chip_xxl -> "XXL"
                        R.id.chip_xxxl -> "XXXL"; else -> ""
                    }
                }
                layoutVelicinaObuca.visibility == View.VISIBLE -> etVelicinaObuca.text.toString().trim()
                else -> ""
            }

            val stanje = when (chipGroupStanje.checkedChipId) {
                R.id.chip_novo_etiketom -> "Novo s etiketom"
                R.id.chip_novo_bez -> "Novo bez etikete"
                R.id.chip_kao_novo -> "Kao novo"
                R.id.chip_dobro -> "Dobro"
                R.id.chip_prihvatljivo -> "Prihvatljivo"
                else -> ""
            }

            val noviOglas = Oglas(
                id = UUID.randomUUID().toString(),
                naziv = naziv,
                opis = opis,
                cijena = cijenaStr.toDoubleOrNull() ?: 0.0,
                lokacija = lokacija,
                kategorija = kategorija,
                velicina = velicina,
                stanje = stanje,
                isMyAd = true,
                korisnikIme = "Moje Ime" // Mock
            )

            lifecycleScope.launch {
                repository.insertOglas(noviOglas.toEntity())
                Toast.makeText(this@AddItemActivity, "Oglas uspješno objavljen!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Nema nav_add u meniju, FAB je aktivan dodaj gumb
    }
}
