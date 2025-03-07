package com.example.apiaplicacion

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


//FireBase

class   LoginActivity : AppCompatActivity() {

    //lateinit se usara más adelante
    //var es una consatante

    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var loginGoRegisterButton: Button

    private lateinit var auth: FirebaseAuth


    private val db = FirebaseFirestore.getInstance()

    //Aviso cada 30 segundos

    private val handler = Handler(Looper.getMainLooper())
    private val toastRunnable = object : Runnable {
        override fun run() {
                Toast.makeText(this@LoginActivity, "Logeate", Toast.LENGTH_SHORT).show()
                handler.postDelayed(this, 30000) // 30 segundos en milisegundos
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        loginGoRegisterButton = findViewById(R.id.loginGoRegisterButton)

        loginButton.setOnClickListener {
            val email = loginEmail.text.toString()
            val password = loginPassword.text.toString()

            if (checkEmpty(email, password)) {
                login(email, password)
            } else {
                Toast.makeText(this, "Introduzca los datos", Toast.LENGTH_LONG).show()
            }
        }

        loginGoRegisterButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun checkEmpty(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty()
    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->

                    val sanitizedEmail = email.replace(".", ",")

                    db.collection("usuarios").document(sanitizedEmail).get()
                        .addOnSuccessListener { document ->
                            if (document != null && document.exists()) {
                                val storedEmail = document.getString("email")
                                val storedPassword = document.getString("password")

                                if (storedEmail == email && storedPassword == password) {
                                    //Actualizacion del tiempo de registro
                                    val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                                    val lastLoginDate = dateFormat.format(Date())
                                    dateFormat.format(Date())

                                    db.collection("usuarios").document(sanitizedEmail)
                                        .update("lastLoginDate", lastLoginDate)
                                        startActivity(Intent(this, PantallaContador::class.java))
                                         finish()
                                } else {
                                    Toast.makeText(applicationContext, "email o constraseña incorrectos", Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Toast.makeText(applicationContext, "El usuario es incorrecto", Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(applicationContext, "Fallo tecnico: ${e.message}", Toast.LENGTH_LONG).show()
                        }

            }
    }
}