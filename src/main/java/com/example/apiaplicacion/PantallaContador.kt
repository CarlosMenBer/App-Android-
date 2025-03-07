package com.example.apiaplicacion

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PantallaContador : AppCompatActivity() {

    private lateinit var dateTextView: TextView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_contador)

        dateTextView = findViewById(R.id.dateTextView)
        val navigateButton: Button = findViewById(R.id.navigateButton)

        val currentDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())
        dateTextView.text = "Fecha actual: $currentDate"

        navigateButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // Inicializar Handler y Runnable para mostrar el toast cada 5 segundos
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                Toast.makeText(applicationContext, "Accede a la Pokedex", Toast.LENGTH_SHORT).show()
                handler.postDelayed(this, 5000) // 5000 milisegundos = 5 segundos
            }
        }
        handler.post(runnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
    }
}
