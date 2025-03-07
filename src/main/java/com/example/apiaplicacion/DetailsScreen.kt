package com.example.apiaplicacion
//Se encarga de mostrar la información detallada de un Pokémon que el usuario haya buscado.


// Importaciones necesarias para Jetpack Compose y Coil
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter

@Composable
fun DetailsScreen(navController: NavHostController, pokemonName: String?) {
    // Estado para almacenar la información del Pokémon
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }

    // Efecto lanzado cuando cambia el nombre del Pokémon
    LaunchedEffect(pokemonName) {
        if (pokemonName != null) {
            // Llamada a la función de búsqueda para obtener los datos del Pokémon
            searchPokemon(pokemonName) { result ->
                pokemon = result
            }
        }
    }
    // Si el Pokémon no es nulo, se muestra la información en la UI
    pokemon?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Muestra el nombre del Pokémon
            Text("Name: ${it.name}", style = MaterialTheme.typography.headlineSmall)

            // Imagen del Pokémon utilizando Coil
            Image(
                painter = rememberImagePainter(it.sprites.front_default),
                contentDescription = null,
                modifier = Modifier.size(128.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Muestra detalles adicionales del Pokémon
            Text("ID: ${it.id}", style = MaterialTheme.typography.bodyLarge)
            Text("Height: ${it.height}", style = MaterialTheme.typography.bodyLarge)
            Text("Weight: ${it.weight}", style = MaterialTheme.typography.bodyLarge)

            // Muestra los tipos del Pokémon
            Text("Types: ${it.types.joinToString { type -> type.type.name }}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para regresar a la pantalla de búsqueda
            Button(onClick = { navController.navigate("search") }) {
                Text("Search Another Pokémon")
            }
        }
    }
}