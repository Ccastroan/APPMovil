package com.example.dsy1105_009v_2026.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dsy1105_009v_2026.ui.theme.HomeScreen
import com.example.dsy1105_009v_2026.view.DrawerMenu
import com.example.dsy1105_009v_2026.view.ListaSolicitudesScreen
import com.example.dsy1105_009v_2026.view.NuevaSolicitudScreen
import com.example.dsy1105_009v_2026.view.ResumenSolicitudScreen
import com.example.dsy1105_009v_2026.viewmodel.SolicitudViewModel

@Composable
fun AppNav() {
    val navController = rememberNavController()

    val solicitudViewModel: SolicitudViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            HomeScreen(navController = navController)
        } //fin composable

        composable(
            route = "DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }//fin navArgument
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            DrawerMenu(username = username, navController = navController)
        }//fin composable

        // Lista de solicitudes del cliente.
        composable(
            route = "solicitudes/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }//fin navArgument
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            ListaSolicitudesScreen(
                username = username,
                navController = navController,
                viewModel = solicitudViewModel
            )
        }

        // Formulario para crear una solicitud
        composable(
            route = "nueva/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }//fin navArgument
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            NuevaSolicitudScreen(
                username = username,
                navController = navController,
                viewModel = solicitudViewModel
            )
        }//fin composable

        // Resumen con el código de seguimiento
        composable(
            route = "resumen/{username}",
            arguments = listOf(
                navArgument("username") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->
            val username = backStackEntry.arguments?.getString("username").orEmpty()
            ResumenSolicitudScreen(
                username = username,
                navController = navController,
                viewModel = solicitudViewModel
            )
        }

    }

}