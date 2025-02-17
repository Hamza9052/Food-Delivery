package com.codegameapp.foodfast

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomAppBar
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.codegameapp.foodfast.BottomBar.BottomBarNav
import com.codegameapp.foodfast.Data.DataFood
import com.codegameapp.foodfast.MVVM.FoodMVVM
import com.codegameapp.foodfast.MVVM.ViewModelFactory
import com.codegameapp.foodfast.Screen.HomeScreen
import com.codegameapp.foodfast.Screen.LoginScreen
import com.codegameapp.foodfast.Screen.NewAccountScreen
import com.codegameapp.foodfast.Screen.ProductScreen
import com.codegameapp.foodfast.Screen.Screen
import com.codegameapp.foodfast.Screen.Screen_
import com.codegameapp.foodfast.ui.MyAppTheme
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import kotlin.getValue

class MainActivity : AppCompatActivity() {
    private lateinit var MVVM: FoodMVVM

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MVVM = ViewModelProvider(this, ViewModelFactory(application)).get(FoodMVVM::class.java)
        enableEdgeToEdge()
        setContent {
            actionBar?.hide()

            MyAppTheme() {
                val navController = rememberNavController()
                Scaffold(
                    contentWindowInsets = WindowInsets.safeDrawing,
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth(),
                    bottomBar = {
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStackEntry?.destination?.route ?: ""
                        if (
                            currentRoute.startsWith("login") ||
                            currentRoute.startsWith("new_account") ||
                            currentRoute.startsWith("product")
                            ) {
                            Log.e("Screen", "I'm on the correct screen")
                        } else {
                            Log.e("Screen", "I'm on a different screen")
                            BottomAppBar(
                                modifier = Modifier
                                    .height(65.dp)
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp)),
                                cutoutShape = CircleShape,
                                backgroundColor = colorResource(R.color.Original)
                            ) {
                                BottomBarNav(navController = navController)
                            }
                        }

                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        val currentBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = currentBackStackEntry?.destination?.route ?: ""
                        if (
                            currentRoute.startsWith("login") ||
                            currentRoute.startsWith("new_account") ||
                            currentRoute.startsWith("product")
                            ) {
                            Log.e("Screen", "I'm on the correct screen")
                        } else {
                            FloatingActionButton(
                                elevation = FloatingActionButtonDefaults.elevation(12.dp),
                                shape = CircleShape,
                                onClick = {
                                    //this onClick is for add some product
                                },
                                backgroundColor = colorResource(R.color.Original)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Add,
                                    contentDescription = "Add icon",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                ) { padding ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .fillMaxWidth()
                            .safeDrawingPadding()
                    ) {
                        NavHost(navController, startDestination = Screen_.Login.route) {

                            composable(Screen_.Login.route) {
                                LoginScreen(navController)
                            }
                            composable(Screen_.Acc.route) {
                                NewAccountScreen(navController)
                            }
                            //profile
                            composable(Screen.Home.route!!) {
                                HomeScreen(MVVM, navController)
                            }
                            //pickUp
                            composable(Screen.Profile.route!!) {
                                // PickupScreen()
                            }
                            composable("product/{jsonData}")
                            { backStackEntry ->
                                val jsonData = backStackEntry.arguments?.getString("jsonData") ?: ""
                                val decodedJson = Uri.decode(jsonData)
                                val dataProduct = Json.decodeFromString<DataFood>(decodedJson)
                                ProductScreen(navController, dataProduct)
                            }

                            //camera
                            composable(Screen.Message.route!!) {
                                // CameraScreen()
                            }
                            composable(Screen.Favorite.route!!) {
                                // CameraScreen()
                            }
                        }
                    }

                }
            }

        }
    }

}


