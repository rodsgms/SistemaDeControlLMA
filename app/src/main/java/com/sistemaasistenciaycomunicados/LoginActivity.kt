package com.sistemaasistenciaycomunicados

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        auth = Firebase.auth

        val email: TextInputLayout = findViewById(R.id.login_email)
        val password = findViewById<TextInputLayout>(R.id.login_password)
        val btnLogin = findViewById<Button>(R.id.login_btn_login)

        btnLogin.setOnClickListener {
            if (email.editText?.text.toString().isEmpty() || password.editText?.text.toString()
                    .isEmpty()
            ) {
                if (email.editText?.text.toString().isEmpty()) {
                    email.isErrorEnabled = true
                    email.error = getString(R.string.field_requiered)
                } else {
                    email.isErrorEnabled = false
                    email.error = null
                }

                if (password.editText?.text.toString().isEmpty()) {
                    password.isErrorEnabled = true
                    password.error = getString(R.string.field_requiered)
                } else {
                    password.isErrorEnabled = false
                    password.error = null
                }
            } else {
                email.isErrorEnabled = false
                email.error = null
                password.isErrorEnabled = false
                password.error = null


                auth.signInWithEmailAndPassword(email.editText?.text.toString().trim(), password.editText?.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            startActivity(Intent(this, MainActivity::class.java))
                        } else {
                            Toast.makeText(
                                baseContext,
                                "Authentication failed." + task.getResult().toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }

    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null)
            startActivity(Intent(this, MainActivity::class.java))

    }
}