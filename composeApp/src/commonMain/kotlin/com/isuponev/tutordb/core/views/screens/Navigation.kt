package com.isuponev.tutordb.core.views.screens

import androidx.navigation.NavHostController

fun NavHostController.navigate(target: Screen) = navigate(target.route)