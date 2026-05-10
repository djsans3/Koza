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

        // PRESKOČI – otvori Home bez prijave
        btnPreskoci.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Molimo unesite email i lozinku", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Firebase Auth
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        btnRegister.setOnClickListener {
            Toast.makeText(this, "Registracija – uskoro dostupno!", Toast.LENGTH_SHORT).show()
        }
    }
}
