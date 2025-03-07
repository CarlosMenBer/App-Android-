package com.example.apiaplicacion

// Importaciones necesarias para la comunicación con la API
import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz que define los métodos de la API para obtener datos de Pokémon
interface PokeApiService {
    @GET("pokemon/{name}") // Endpoint para obtener información de un Pokémon por su nombre
    suspend fun getPokemon(@Path("name") name: String): Pokemon // Función suspendida para uso con corrutinas
}
