package com.isuponev.tutordb.core.views.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.isuponev.tutordb.core.HASH_CODE_NUMBER_GENERATOR
import com.isuponev.tutordb.core.views.ToolMenuElement

abstract class Screen(
    val route: String,
    val toolsMenuElements: List<ToolMenuElement> = emptyList()
) {
    @Composable
    abstract fun view(navHostController: NavHostController, modifier: Modifier = Modifier)

    override fun toString(): String {
        return "Screen($route)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Screen) return false
        return route != other.route
    }

    override fun hashCode(): Int {
        return HASH_CODE_NUMBER_GENERATOR * javaClass.name.hashCode() + route.hashCode()
    }
}