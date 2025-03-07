package com.example.apiaplicacion

// Importaciones necesarias para el uso de corrutinas y la API
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// Función para buscar un Pokémon en la API
fun searchPokemon(name: String, callback: (Pokemon?) -> Unit) {
    // Se define un scope en el dispatcher de IO para operaciones en segundo plano
    val scope = CoroutineScope(Dispatchers.IO)
    scope.launch {
        try {
            // Realiza la solicitud a la API
            val response = RetrofitInstance.api.getPokemon(name)

            // Cambia al hilo principal para actualizar la UI con la respuesta
            withContext(Dispatchers.Main) {
                callback(response)
            }
        } catch (e: Exception) {
            // En caso de error, retorna un valor nulo en el callback
            withContext(Dispatchers.Main) {
                callback(null)
            }
        }
    }
}
