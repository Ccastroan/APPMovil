package com.example.dsy1105_009v_2026.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dsy1105_009v_2026.ui.home.MuestraDatosScreen
import com.example.dsy1105_009v_2026.ui.theme.HomeScreen
import com.example.dsy1105_009v_2026.view.DrawerMenu

@Composable
fun AppNav(){
    val navController = rememberNavController()

    NavHost(navController=navController, startDestination="login") {

        composable("login") {
            HomeScreen(navController = navController)
        } //fin composable

        composable(
         //   route="muestraDatos/{username}",
            route="DrawerMenu/{username}",
            arguments = listOf(
                navArgument("username"){
                    type= NavType.StringType
                }//fin ListOf
            )
        )//fin composable

        { // inicio back
            backStackEntry ->
            val username=backStackEntry.arguments?.getString("username").orEmpty()
           // MuestraDatosScreen(username=username,navController=navController)
            DrawerMenu(username=username,navController=navController)

        }//fin back


    }//Fin NavHost

}// fin AppNav