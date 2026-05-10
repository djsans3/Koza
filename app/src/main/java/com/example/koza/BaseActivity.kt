package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var bottomNav: BottomNavigationView
    protected var fabAdd: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupNavigation() {
        bottomNav = findViewById(R.id.bottom_nav)

        fabAdd = findViewById(R.id.fab_add)
        fabAdd?.setOnClickListener {
            if (this !is AddItemActivity) {
                startActivity(Intent(this, AddItemActivity::class.java))
            }
        }

        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    if (this !is HomeActivity) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                    true
                }

                R.id.nav_search -> {
                    false
                }

                R.id.nav_chat -> {
                    false
                }

                R.id.nav_profile -> {
                    if (this !is ProfileActivity) {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
                    true
                }

                else -> false
            }
        }
    }
}
