package com.example.aichat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.aichat.ui.screens.chat.ChatScreen
import com.example.aichat.ui.screens.settings.SettingsScreen
import kotlinx.serialization.Serializable

sealed class AppScreen {
    @Serializable
    object Chat : AppScreen()
    @Serializable
    object Settings : AppScreen()
}

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: AppScreen = AppScreen.Chat,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable<AppScreen.Chat> {
            ChatScreen(
                navigateToSettings = { navController.navigate(AppScreen.Settings) }
            )
        }
        composable<AppScreen.Settings> {
            SettingsScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
