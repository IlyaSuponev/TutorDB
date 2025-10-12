package com.isuponev.tutordb.core.views.screens

import androidx.navigation.NavHostController

fun NavHostController.navigate(target: Screen) {
    if (this.currentDestination?.route == target.route) return
    navigate(target.route)
}
