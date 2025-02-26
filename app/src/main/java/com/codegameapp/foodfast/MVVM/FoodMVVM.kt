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
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodMVVM @Inject constructor(application: Application) : ViewModel() {


    fun action(event: UserEvent, context: Context) {
        when (event) {
            is UserEvent.Login -> Logins(event.email, event.password, event.state, context)
            is UserEvent.CreateAccount -> CreateAccount(
                event.user, event.state, context
            )

            is UserEvent.signOut -> signout(event.state, context)
//            is UserEvent.Upload_Image -> upload(event.image,context)
        }
    }

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

    private var token = MutableStateFlow("")

    private val id = MutableLiveData("")
    private val name = MutableLiveData("")
    val _name: LiveData<String> get() = name
    private val _isemailVerified = MutableLiveData<Boolean>() // Default to logged out
    val isemailVerified: LiveData<Boolean> = _isemailVerified

    private val _isLoggedIn = MutableLiveData<Boolean>() // Default to logged out
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn


    private fun CreateAccount(
        User: UserData,
        state: (state: Boolean) -> Unit,
        context: Context,
    ) {

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                token.update { task.result.toString() }
                Log.d("token success", "CreateAccount: $token")
            }
        }

        Firebase.auth.createUserWithEmailAndPassword(User.emial, User.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    if (task.result.user != null) {

                        task.result.user?.sendEmailVerification()?.addOnSuccessListener {

                            FirebaseFirestore.getInstance().collection("users")
                                .document(task.result.user?.uid!!)
                                .set(
                                    hashMapOf(
                                        "password" to User.password,
                                        "name_user" to User.name,
                                        "FcmToken" to token.value
                                    )
                                ).addOnSuccessListener {
                                    state(true)
                                }.addOnFailureListener {
                                    // Handle Firestore error
                                    Log.e(
                                        "CreateAccount",
                                        "Error saving user to Firestore: ${it.message}"
                                    )
                                    state(false)
                                }


                            Log.d("CreateAccount", "Verification email sent successfully.")


                        }?.addOnFailureListener {
                            Log.e("CreateAccount", "Error saving user to Firestore: ${it.message}")
                            state(false)
                        }


                        Log.e(
                            "CreateAccount",
                            "Account creation failed: ${task.exception?.message}"
                        )
                        state(false)
                    }
                }

            }
    }


    private fun Logins(
        emial: String,
        password: String,
        state: (state: Boolean) -> Unit,
        context: Context,
    ) {
        _isLoggedIn.value = false

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                token.update { task.result.toString() }
            }
        }
        Firebase.auth.signInWithEmailAndPassword(emial, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information

                    Log.d(TAG, "Login:success")
                    id.value = task.result.user?.uid!!
                    state(true)
                    FirebaseFirestore.getInstance()
                        .collection("users").document(id.value.toString())
                        .update("FcmToken", token.value).addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("token Success", "Logins: isSuccessful")
                            } else {
                                Log.e("token Failed", "Logins: ${task.exception}")
                            }
                        }
                _isLoggedIn.value = true
//                FirebaseFirestore.getInstance()
//                    .collection("users")
//                    .document(id.value.toString()).get()
//                    .addOnSuccessListener { document ->
//                        name.value = document.getString("first_name").toString()
//
//                        val sharedPreferences: SharedPreferences =
//                            context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//                        val editor = sharedPreferences.edit()
//                            .putString("uid", id.value)
////                            .putString("login", _isLoggedIn.value.toString())
//                            .putString("name", name.value)
//                            .putString("password", password)
//                            .putString("email", emial)
//                        editor.apply()
//                    }


                } else {
                    // If sign in fails, display a message to the user.
                    Log.e(TAG, "Login:failure", task.exception)
                    Toast.makeText(
                        context,
                        "Password or Email incorrect.",
                        Toast.LENGTH_SHORT,
                    ).show()


                }
            }


    }

    private fun signout(state: (state: Boolean) -> Unit, context: Context) {
        _isemailVerified.value = false
        Log.e("logout", "im here")
        Firebase.auth.signOut()
        state(true)
        FirebaseFirestore.getInstance()
            .collection("users").document(id.value.toString())
            .update("FcmToken", "").addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d("token Success", "Logins: isSuccessful")
                } else {
                    Log.e("token Failed", "Logins: ${task.exception}")
                }
            }

        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit()
            .clear()
            .apply()


    }

    fun checkEmailVerification() {
        val user = Firebase.auth.currentUser
        user?.reload() // Reload the user data to check for updates
        Log.e("Verifiy","${user}")
        Log.e("Verifiy","${user?.isEmailVerified}")
        if (user?.isEmailVerified == true) {
            _isemailVerified.value = user?.isEmailVerified // Update LiveData if email is verified
        } else {
            _isemailVerified.value = user?.isEmailVerified // Keep it false if email is not verified
        }
    }

    fun startEmailVerificationCheck() {
        viewModelScope.launch {
            // Check immediately after sending the verification email
            delay(2000) // Wait for 2 seconds, allowing the user to start verifying
            checkEmailVerification() // Check if email is verified

            // You could use a loop or a delayed check to keep trying after a certain interval
            repeat(10) { // Try 5 times with a 5-second interval
                delay(5000)
                checkEmailVerification()
                if (isemailVerified.value == true) {
                    return@launch // Exit if email is verified
                }
            }
        }

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

