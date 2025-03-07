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

class RegisterActivity : AppCompatActivity() {
    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerRepeatPassword: EditText
    private lateinit var registerButton: Button
    private lateinit var registerGoLoginButton: Button

    private lateinit var auth: FirebaseAuth


   private val db = FirebaseFirestore.getInstance()
    //Aviso cada 30 segundos

    private val handler = Handler(Looper.getMainLooper())
    private val toastRunnable = object : Runnable {
        override fun run() {
            Toast.makeText(this@RegisterActivity, "Registrate", Toast.LENGTH_SHORT).show()
            handler.postDelayed(this, 30000) // 30 segundos en milisegundos
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()

        registerEmail = findViewById(R.id.registerEmail)
        registerPassword = findViewById(R.id.registerPassword)
        registerRepeatPassword = findViewById(R.id.registerRepeatPassword)
        registerButton = findViewById(R.id.registerButton)
        registerGoLoginButton = findViewById(R.id.registerGoLoginButton)

        registerButton.setOnClickListener {
            val email = registerEmail.text.toString()
            val password = registerPassword.text.toString()
            val repeatPassword = registerRepeatPassword.text.toString()

            if (password == repeatPassword && checkEmpty(email, password, repeatPassword)) {
                register(email, password)
            } else {
                Toast.makeText(this, "Asegurate de introducir bien los datos    ", Toast.LENGTH_LONG).show()
            }
        }

        registerGoLoginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun checkEmpty(email: String, password: String, repeatPassword: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() && repeatPassword.isNotEmpty()
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                val currentDate = dateFormat.format(Date())
                val user = hashMapOf(
                    "email" to email,
                    "password" to password,
                    "registrationDate" to currentDate
                )
                val sanitizedEmail = email.replace(".", ",")
                db.collection("usuarios").document(sanitizedEmail).set(user)
                    .addOnSuccessListener {
                        val intent = Intent(this, PantallaContador::class.java)
                        startActivity(intent)
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(applicationContext, "Error  ${e.message}", Toast.LENGTH_LONG).show()
                    }

            }
    }

}