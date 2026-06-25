package com.example.koza

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class OmiljeniActivity : BaseActivity() {

    private lateinit var repository: OglasRepository
    private lateinit var adapter: OglasAdapter
    private val listaOmiljenih = mutableListOf<Oglas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_omiljeni)
        setupNavigation()

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar_omiljeni)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_omiljeni)
        adapter = OglasAdapter(listaOmiljenih) { oglas ->
            val intent = Intent(this, DetaljiOglasaActivity::class.java)
            intent.putExtra("OGLAS_ID", oglas.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ucitajOmiljene()
    }

    private fun ucitajOmiljene() {
        lifecycleScope.launch {
            val entities = repository.getFavoriteOglasi()
            listaOmiljenih.clear()
            listaOmiljenih.addAll(entities.map { it.toDomain() })
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        ucitajOmiljene()
        bottomNav?.menu?.findItem(R.id.nav_profile)?.isChecked = true
    }
}
