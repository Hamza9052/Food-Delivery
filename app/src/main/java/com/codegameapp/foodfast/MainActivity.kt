package com.codegameapp.foodfast

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codegameapp.foodfast.BottomBar.BottomBarNav
import com.codegameapp.foodfast.Screen.HomeScreen
import com.codegameapp.foodfast.Screen.Screen
import com.codegameapp.foodfast.ui.MyAppTheme

class MainActivity : AppCompatActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            actionBar?.hide()
            MyAppTheme(){
                val navController = rememberNavController()
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxWidth(),
                    bottomBar = {
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
                    },
                    floatingActionButtonPosition = FabPosition.Center,
                    isFloatingActionButtonDocked = true,
                    floatingActionButton = {
                        FloatingActionButton(
                            elevation =FloatingActionButtonDefaults.elevation(12.dp) ,
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
                ) {
                    MainScreenNavigation(navController)
                }
            }

        }
    }

}


@Composable
fun MainScreenNavigation(navController: NavHostController) {

    NavHost(navController, startDestination = Screen.Home.route!!) {

        //profile
        composable(Screen.Home.route) {
            HomeScreen()
        }
        //pickUp
        composable(Screen.Profile.route!!) {
            // PickupScreen()
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