package com.example.apiaplicacion
//Gestionar la navegación


// Importaciones necesarias para la navegación en Jetpack Compose
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavigation(navController: NavHostController) {
    // Configuración del sistema de navegación de la aplicación
    NavHost(navController = navController, startDestination = "search") {
        // Definición de la pantalla de búsqueda
        composable("search") { SearchScreen(navController) }

        // Definición de la pantalla de detalles con parámetro dinámico
        composable("details/{name}") { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name")
            DetailsScreen(navController, name)
        }
    }
}
