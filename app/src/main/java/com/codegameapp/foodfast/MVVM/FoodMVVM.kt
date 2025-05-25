package com.codegameapp.foodfast.MVVM

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.codegameapp.foodfast.API.ApiFood
import com.codegameapp.foodfast.Data.DataFood
import com.codegameapp.foodfast.Data.UserData
import com.codegameapp.foodfast.Event.UserEvent
import com.codegameapp.foodfast.SaveToken.save_token
import com.codegameapp.foodfast.supeConnect.SupeBase
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.jan.supabase.gotrue.gotrue
import io.github.jan.supabase.gotrue.providers.builtin.Email
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodMVVM @Inject constructor(application: Application) : ViewModel() {



    private val _listFood = MutableLiveData<List<DataFood>>(mutableListOf<DataFood>())
    val listFood: LiveData<List<DataFood>> get() = _listFood.map { it.toList() }

    suspend fun FetchData_food() {
        val api = ApiFood.create()
        Log.e("foodtest", "$api")
        try {
            Log.e("foodtest", "$api")
            val response = api.getFoods()
            Log.e("foodtest", "$response")
            _listFood.value = response
        } catch (e: Exception) {
            Log.e("foodtest", "$e")
        }

    }

    fun singUp(
        context: Context,
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch(){
            try {
               SupeBase.supabase.gotrue.signUpWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                Log.e("checkIsSuccess","successful singUp")
            }catch (e: Exception){
                Log.e("checkIsSuccess","Error singUp: $e")
            }
        }
    }

    fun login(
        context: Context,
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch(){
            try {
                SupeBase.supabase.gotrue.loginWith(Email){
                    email = userEmail
                    password = userPassword
                }
                saveToken(context)
                Log.e("checkIsSuccess","successful login")
            }catch (e: Exception){
                Log.e("checkIsSuccess","Error login: $e")
            }
        }
    }

    fun logout(
        context: Context,
        userEmail: String,
        userPassword: String
    ){
        viewModelScope.launch(){
            try {
                SupeBase.supabase.gotrue.logout()
                Log.e("checkIsSuccess","successful logout")
            }catch (e: Exception){
                Log.e("checkIsSuccess","Error logout: $e")
            }
        }
    }
    fun isUserLoggedIn(
        context: Context
    ){

        viewModelScope.launch(){
            try {
                val token = getToken(context)
                if (token.isNullOrEmpty()){
                    Log.e("checkIsUserLoggedIn","User is not logged In")
                } else{
                    SupeBase.supabase.gotrue.retrieveUser(token)
                    SupeBase.supabase.gotrue.refreshCurrentSession()
                    saveToken(context)
                    Log.e("checkIsUserLoggedIn","User is logged In")
                }
            }catch (e: Exception){
                Log.e("checkIsUserLoggedIn","Error of checking: $e")
            }
        }

    }




    private fun saveToken(context: Context){
        viewModelScope.launch(){
            val accessToken = SupeBase.supabase.gotrue.currentAccessTokenOrNull()
            val sharedPref = save_token(context)
            sharedPref.saveStringData("accessToken",accessToken)
        }
    }
    private fun getToken(context: Context):String?{
            val sharedPref = save_token(context)
           return sharedPref.getStringData("accessToken")
    }


}
    class ViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return if (modelClass.isAssignableFrom(FoodMVVM::class.java)) {
                FoodMVVM(application) as T
            } else {
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }

    }

