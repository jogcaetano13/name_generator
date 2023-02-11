package com.example.namegenerator.navigation

sealed class NavigationRoutes(val route: String) {
    object Home : NavigationRoutes("home_route")
}