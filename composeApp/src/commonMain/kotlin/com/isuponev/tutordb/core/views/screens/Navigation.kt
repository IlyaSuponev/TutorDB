package com.isuponev.tutordb.core.views.screens

import androidx.navigation.NavHostController

/**
 * Extension function for safe navigation between screens in the application.
 *
 * This function provides a wrapper around the standard [NavHostController.navigate] method
 * that prevents duplicate navigation attempts to the same screen. It checks if the current
 * destination matches the target screen before performing navigation, which helps avoid
 * common navigation issues and duplicate back stack entries.
 *
 * Note: For parameterized routes with arguments, you may need to use the standard
 * [NavHostController.navigate] method directly, as this extension is designed for
 * simple route matching without parameters.
 *
 * @param target The destination screen to navigate to.
 *
 * @see NavHostController.navigate
 * @see NavHostController.currentDestination
 */
fun NavHostController.navigate(target: Screen) {
    if (this.currentDestination?.route == target.route) return
    navigate(target.route)
}
