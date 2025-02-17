package com.codegameapp.foodfast.Screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home

sealed class Screen_(val route: String){
    object Login : Screen_("login")
    object Acc : Screen_("new_account")
}
