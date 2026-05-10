package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var toolbar: MaterialToolbar
    protected lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    protected fun setupNavigation() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        bottomNav = findViewById(R.id.bottom_nav)

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
                    // Placeholder – ekran još nije napravljen
                    Toast.makeText(this, "Pretraga – uskoro 🔍", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_add -> {
                    if (this !is AddItemActivity) {
                        startActivity(Intent(this, AddItemActivity::class.java))
                    }
                    true
                }

                R.id.nav_chat -> {
                    // Placeholder – ekran još nije napravljen
                    Toast.makeText(this, "Chat – uskoro 💬", Toast.LENGTH_SHORT).show()
                    true
                }

                R.id.nav_profile -> {
                    // Profil gumb → otvara Login ekran
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }
}
