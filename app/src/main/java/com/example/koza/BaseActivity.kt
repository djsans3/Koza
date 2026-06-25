package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton

abstract class BaseActivity : AppCompatActivity() {

    protected var bottomNav: BottomNavigationView? = null
    protected var fabAdd: FloatingActionButton? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupNavigation() {
        bottomNav = findViewById(R.id.bottom_nav)

        fabAdd = findViewById(R.id.fab_add)
        
        fabAdd?.setOnClickListener {
            if (UserSession.isGuest) {
                Toast.makeText(this, "Morate se prijaviti za objavu artikla", Toast.LENGTH_LONG).show()
            } else {
                if (this !is AddItemActivity) {
                    startActivity(Intent(this, AddItemActivity::class.java))
                }
            }
        }

        bottomNav?.setOnItemSelectedListener { item ->
            when (item.itemId) {

                R.id.nav_home -> {
                    if (this !is HomeActivity) {
                        startActivity(Intent(this, HomeActivity::class.java))
                        finish()
                    }
                    true
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
