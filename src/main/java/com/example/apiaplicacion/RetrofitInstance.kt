package com.example.apiaplicacion

// Importaciones necesarias para configurar Retrofit
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Objeto singleton que gestiona la instancia de Retrofit
object RetrofitInstance {
    val api: PokeApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/") // URL base de la API de Pokémon
            .addConverterFactory(GsonConverterFactory.create()) // Conversor para JSON
            .build()
            .create(PokeApiService::class.java) // Creación del servicio API
    }
}