package com.example.vibees.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.vibees.screens.GenericScreen
import com.example.vibees.screens.bottombar.BottomBar

fun NavGraphBuilder.hostNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.HOST,
        startDestination = HostScreens.Step1.route
    ) {
        composable(route = HostScreens.Step1.route) {
            GenericScreen(
                name = "Step1",
                onClick = { navController.navigate(HostScreens.Step2.route) }
            )
        }
        composable(route = HostScreens.Step2.route) {
            GenericScreen(
                name = "Step2",
                onClick = { navController.navigate(HostScreens.Step3.route) }
            )
        }
        composable(route = HostScreens.Step3.route) {
            GenericScreen(
                name = "Step3",
                onClick = { navController.navigate(BottomBar.Host.route) {
                    popUpTo(BottomBar.Host.route) {
                        inclusive = true
                    }
                } }
            )
        }
    }
}

sealed class HostScreens(val route: String) {
    object Step1: HostScreens(route = "Step1")
    object Step2: HostScreens(route = "Step2")
    object Step3: HostScreens(route = "Step3")
}