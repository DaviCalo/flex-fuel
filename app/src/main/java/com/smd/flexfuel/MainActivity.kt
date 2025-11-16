package com.smd.flexfuel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.smd.flexfuel.ui.screens.CreateDataScreen
import com.smd.flexfuel.ui.screens.EditeDataScreen
import com.smd.flexfuel.ui.screens.MainScreen
import com.smd.flexfuel.ui.theme.FlexFuelTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlexFuelTheme {
                val navController: NavHostController = rememberNavController()
                NavHost(navController = navController, startDestination = "mainscreen") {
                    composable("mainscreen") { MainScreen(navController) }
                    composable("createdatascreen") { CreateDataScreen(navController) }
                    composable("editdatascreen/{idPost}") { backStackEntry ->
                        val idPost = backStackEntry.arguments?.getString("idPost")?.toIntOrNull()
                        EditeDataScreen(navController, idPost)
                    }
                }
            }
        }
    }
}