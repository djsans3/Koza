package com.example.koza

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etEmail = findViewById<TextInputEditText>(R.id.et_email)
        val etPassword = findViewById<TextInputEditText>(R.id.et_password)
        val btnLogin = findViewById<MaterialButton>(R.id.btn_login)
        val btnRegister = findViewById<MaterialButton>(R.id.btn_register)
        val btnPreskoci = findViewById<MaterialButton>(R.id.btn_preskoci)

        btnPreskoci.setOnClickListener {
            UserSession.setGuestMode()
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty()) {
                etEmail.error = "Unesite email"
                return@setOnClickListener
            }
            if (!email.contains("@")) {
                etEmail.error = "Email mora sadržavati @"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                etPassword.error = "Unesite lozinku"
                return@setOnClickListener
            }

            if (email == "admin@koza.hr" && password == "1234") {
                UserSession.setAdminMode()
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Pogrešan email ili lozinka", Toast.LENGTH_SHORT).show()
            }
        }

        btnRegister.setOnClickListener {
            Toast.makeText(this, "Registracija – uskoro dostupno!", Toast.LENGTH_SHORT).show()
        }
    }
}
