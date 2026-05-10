package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        setupNavigation()

        // TODO: Učitaj podatke iz Firebase
        val btnOdjavi = findViewById<MaterialButton>(R.id.btn_odjavi)

        val postaviListener = { id: Int, poruka: String ->
            findViewById<LinearLayout>(id).setOnClickListener {
                Toast.makeText(this, "$poruka – uskoro dostupno", Toast.LENGTH_SHORT).show()
            }
        }

        postaviListener(R.id.item_moji_oglasi, "Moji oglasi")
        postaviListener(R.id.item_omiljeni, "Omiljeni artikli 💛")
        postaviListener(R.id.item_narudzbe, "Moje narudžbe")
        postaviListener(R.id.item_prodaje, "Moje prodaje")
        postaviListener(R.id.item_postavke, "Postavke računa")
        postaviListener(R.id.item_pomoc, "Pomoć i podrška")

        btnOdjavi.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        bottomNav.menu.findItem(R.id.nav_profile).isChecked = true
    }
}
