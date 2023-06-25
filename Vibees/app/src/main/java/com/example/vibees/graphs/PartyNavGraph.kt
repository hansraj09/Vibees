package com.example.vibees.graphs

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.vibees.screens.home.myparties.PartyDetails

fun NavGraphBuilder.partyNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.PARTY,
        startDestination = PartyScreen.Details.route
    ) {
        composable(
            route = PartyScreen.Details.route,
            arguments = listOf(navArgument("id") {
                type = NavType.StringType
            })
        ) {
            PartyDetails(
                navController = navController,
                id = it.arguments?.getString("id").toString()
            )
        }
    }
}

sealed class PartyScreen(val route: String) {
    object Details: PartyScreen(route = "DETAILS/{id}") {
        fun passId(id: String): String {
            return "DETAILS/$id"
        }
    }
}