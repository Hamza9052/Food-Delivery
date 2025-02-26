package com.codegameapp.foodfast.Event

import androidx.compose.runtime.Composable
import com.codegameapp.foodfast.Data.UserData

interface UserEvent {
    data class Login(val email: String, val password: String, val state: (state: Boolean) -> Unit) :
        UserEvent
    data class CreateAccount(val user: UserData, val state: (state: Boolean) -> Unit) : UserEvent
    data class signOut(val state: (state: Boolean) -> Unit) : UserEvent
}