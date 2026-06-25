package com.example.koza

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class MojiOglasiActivity : BaseActivity() {

    private lateinit var repository: OglasRepository
    private lateinit var adapter: OglasAdapter
    private val listaMojih = mutableListOf<Oglas>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moji_oglasi)
        setupNavigation()

        val db = KozaDatabase.getDatabase(this)
        repository = OglasRepository(db.oglasDao())

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar_moji_oglasi)
        toolbar.setNavigationOnClickListener {
            finish()
        }

        val recyclerView = findViewById<RecyclerView>(R.id.rv_moji_oglasi)
        adapter = OglasAdapter(listaMojih) { oglas ->
            val intent = Intent(this, DetaljiOglasaActivity::class.java)
            intent.putExtra("OGLAS_ID", oglas.id)
            startActivity(intent)
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ucitajMojeOglase()
    }

    private fun ucitajMojeOglase() {
        lifecycleScope.launch {
            val entities = repository.getMyOglasi()
            listaMojih.clear()
            listaMojih.addAll(entities.map { it.toDomain() })
            adapter.notifyDataSetChanged()
        }
    }

    override fun onResume() {
        super.onResume()
        ucitajMojeOglase()
        bottomNav?.menu?.findItem(R.id.nav_profile)?.isChecked = true
    }
}
