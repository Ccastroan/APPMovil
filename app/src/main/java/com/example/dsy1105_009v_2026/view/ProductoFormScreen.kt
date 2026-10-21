package com.example.dsy1105_009v_2026.view

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun ProductoFormScreen(
    navController: NavController,
    nombre:String,
    precio:String
){//inicio


}//fin


@Preview(showBackground = true)
@Composable

fun PreviewProductoFormScreen(){
    ProductoFormScreen(
        navController = rememberNavController(),
        nombre="Producto ejemplo",
        precio="$10.000"
    )
}
