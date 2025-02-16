package com.codegameapp.foodfast.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Message
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String?, val title: String?, val icon: ImageVector?) {
    object Home : Screen("home","Home", Icons.Outlined.Home)
    object Profile : Screen("profile","Profile", Icons.Outlined.Person)
    object Message : Screen("message","Message", Icons.Outlined.Message)
    object Favorite : Screen("favorite","Favorite", Icons.Filled.Favorite)
}

val items = listOf(
    Screen.Home,
    Screen.Profile,
    Screen.Message,
    Screen.Favorite
)