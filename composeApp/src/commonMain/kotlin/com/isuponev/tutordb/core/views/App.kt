package com.isuponev.tutordb.core.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.isuponev.tutordb.core.views.screens.Screen
import com.isuponev.tutordb.core.views.theme.AppTheme

private object AppError : Screen("Error") {
    @Composable
    override fun view(
        navHostController: NavHostController,
        modifier: Modifier
    ) {
        Text(text = "Something went wrong")
    }

}

@Composable
internal expect fun AppMainContainerGeneration(
    navController: NavHostController,
    screens: List<Screen>,
    startDestination: Screen,
    modifier: Modifier = Modifier,
)

internal expect fun appScreens(): List<Screen>

@Composable
private fun AppMainContainer(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    val screens = remember { appScreens().ifEmpty { listOf<Screen>(AppError) } }
    AppMainContainerGeneration(
        navController = navController,
        screens = screens,
        startDestination = screens.first(),
        modifier = modifier,
    )
}

@Composable
fun App() {
    AppTheme {
        val navController = rememberNavController()
        AppMainContainer(
            navController = navController,
            modifier = Modifier.fillMaxSize()
        )
    }
}

