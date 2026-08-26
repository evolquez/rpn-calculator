package com.oletob.rpncalc.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oletob.rpncalc.feature.about.AboutScreen
import com.oletob.rpncalc.feature.calculator.CalculatorScreen
import com.oletob.rpncalc.feature.history.HistoryScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Destinations.CALCULATOR) {
        composable(Destinations.CALCULATOR) {
            CalculatorScreen(
                onNavigateToHistory = { navController.navigate(Destinations.HISTORY) },
                onNavigateToAbout = { navController.navigate(Destinations.ABOUT) }
            )
        }
        composable(Destinations.HISTORY) {
            HistoryScreen(onNavigateBack = navController::popBackStack)
        }
        composable(Destinations.ABOUT) {
            AboutScreen(onNavigateBack = navController::popBackStack)
        }
    }
}
