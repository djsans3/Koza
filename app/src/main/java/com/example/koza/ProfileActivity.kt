package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class ProfileActivity : BaseActivity() {

    private lateinit var repository: OglasRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupNavigation()

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        val btnOdjavi = findViewById<MaterialButton>(R.id.btn_odjavi)

        val postaviListener = { id: Int, poruka: String ->
            findViewById<LinearLayout>(id).setOnClickListener {
                Toast.makeText(this, "$poruka – uskoro dostupno", Toast.LENGTH_SHORT).show()
            }
        }

        postaviListener(R.id.item_moji_oglasi, "Moji oglasi")
        findViewById<LinearLayout>(R.id.item_moji_oglasi).setOnClickListener {
            startActivity(Intent(this, MojiOglasiActivity::class.java))
        }

        postaviListener(R.id.item_omiljeni, "Omiljeni artikli 💛")
        findViewById<LinearLayout>(R.id.item_omiljeni).setOnClickListener {
            startActivity(Intent(this, OmiljeniActivity::class.java))
        }

        postaviListener(R.id.item_narudzbe, "Moje narudžbe")
        postaviListener(R.id.item_prodaje, "Moje prodaje")
        postaviListener(R.id.item_postavke, "Postavke računa")
        postaviListener(R.id.item_pomoc, "Pomoć i podrška")

        btnOdjavi.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        osvjeziStatistiku()
    }

    private fun osvjeziStatistiku() {
        lifecycleScope.launch {
            val mojiOglasi = repository.getMyOglasi()
            findViewById<TextView>(R.id.tv_br_oglasa).text = mojiOglasi.size.toString()
            
            // Prodano i ocjena ostaju mock za sada
        }
    }

    override fun onResume() {
        super.onResume()
        osvjeziStatistiku()
        bottomNav?.menu?.findItem(R.id.nav_profile)?.isChecked = true
    }
}
