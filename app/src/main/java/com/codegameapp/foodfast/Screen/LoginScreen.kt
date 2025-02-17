package com.codegameapp.foodfast.Screen

import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.codegameapp.foodfast.R
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavController
){
    var showPassword by remember { mutableStateOf(value = false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
            .safeDrawingPadding()
            .background(color = colorResource(R.color.BrowB)),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(75.dp))
        Text(
            "Login",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Spacer(modifier = Modifier.height(75.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .background(color = colorResource(R.color.White), shape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp)),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(0.3f))
            Box (
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(100.dp)
            ){
                Image(
                    painter = painterResource(R.drawable.logo),
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillBounds
                )

            }

            Spacer(modifier = Modifier.weight(0.2f))
            OutlinedTextField(
                value = "state.email" ,
                onValueChange ={

                },label = {
                    Text(
                        text = "Email",
                        color = colorResource(R.color.Original),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(focusedBorderColor =  colorResource(R.color.Original)),
                modifier = Modifier.width(350.dp),
                singleLine = true,
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    color =colorResource(R.color.Black),
                    fontWeight = FontWeight.Bold
                )
            )

            Spacer(modifier = Modifier.weight(0.1f))

            OutlinedTextField(
                value = "state.password" ,
                onValueChange ={

                },label = {
                    Text(
                        text = "Password",
                        color = colorResource(R.color.Original),
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.width(350.dp),
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colorResource(R.color.Original)
                ),
                shape = RoundedCornerShape(15.dp),
                textStyle = TextStyle(
                    color = colorResource(R.color.Black),
                    fontWeight = FontWeight.Bold
                ),
                visualTransformation = if (showPassword){
                    VisualTransformation.None
                }else{
                    PasswordVisualTransformation()
                },keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    if (showPassword) {
                        IconButton(onClick = { showPassword = false }) {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = "hide_password",
                                tint = colorResource(R.color.Original)
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { showPassword = true }) {
                            Icon(
                                imageVector = Icons.Filled.Visibility,
                                contentDescription = "hide_password",
                                tint = colorResource(R.color.BrowB)
                            )
                        }
                    }
                }
            )


            Spacer(modifier = Modifier.weight(0.2f))
            Button(
                onClick = {
                    navController.navigate(Screen.Home.route!!)
                },
                colors = ButtonDefaults.buttonColors(colorResource(R.color.Original)),
                modifier = Modifier
                    .width(330.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(30.dp)

            ) {
                Text(
                    text = "Log In",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(R.color.White)
                )

            }



            Spacer(modifier = Modifier.weight(0.1f))

            Button(
                onClick = {
                    navController.navigate(Screen_.Acc.route)
                },
                modifier = Modifier
                    .width(330.dp)
                    .height(40.dp),
                shape = RoundedCornerShape(30.dp),
                contentPadding = ButtonDefaults.ContentPadding,
                colors = ButtonDefaults.buttonColors( colorResource(R.color.Original))

            ) {
                Text(
                    text = "Create New Account",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = colorResource(R.color.White)
                )

            }

            Spacer(modifier = Modifier.weight(0.4f))
        }

    }
}
//@Preview
//@Composable
//fun tes() {
//
//    LoginScreen()
//}