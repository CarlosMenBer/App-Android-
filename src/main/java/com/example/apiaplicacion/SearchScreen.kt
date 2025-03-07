package com.example.apiaplicacion

// Importaciones necesarias para la interfaz en Jetpack Compose
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun SearchScreen(navController: NavHostController) {
    // Estado para almacenar la consulta de búsqueda
    var searchQuery by remember { mutableStateOf("") }
    // Estado para almacenar los datos del Pokémon encontrado
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }
    // Estado para almacenar mensajes de error
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Campo de texto para ingresar el nombre del Pokémon
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Enter Pokémon name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para realizar la búsqueda del Pokémon
        Button(
            onClick = {
                searchPokemon(searchQuery) { result ->
                    if (result != null) {
                        pokemon = result
                        errorMessage = null
                        navController.navigate("details/${result.name}")
                    } else {
                        errorMessage = "Pokémon not found or there was an error. Please try again."
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Muestra un mensaje de error si la búsqueda falla
        errorMessage?.let {
            Text(it, color = MaterialTheme.colorScheme.error)
        }

        // Muestra el nombre del Pokémon encontrado
        pokemon?.let {
            Text("Found Pokémon: ${it.name}")
        }
    }
}
