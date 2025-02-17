package com.codegameapp.foodfast.Screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Remove
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codegameapp.foodfast.Data.DataFood
import com.codegameapp.foodfast.R
import com.google.android.datatransport.ProductData
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductScreen(
    navController: NavController,
    dataFoodList: DataFood
) {



    val Image = rememberAsyncImagePainter(
        model = ImageRequest.Builder(navController.context)
            .data(dataFoodList.imageUrl)
            .crossfade(true)
            .error(R.drawable.logo)
            .placeholder(R.drawable.logo)
            .build()
    )
    val name = dataFoodList.name
    val rate = dataFoodList.rate.toString()
    val price = dataFoodList.price.toString()
    val descriptor = dataFoodList.descriptor
    Scaffold(
        contentWindowInsets = WindowInsets.safeDrawing,
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(Color.Transparent),
                modifier = Modifier
                    .height(50.dp)
                    .background(Color.Transparent)
                ,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.Home.route!!)
                        }
                    ) {
                        Icon(
                            contentDescription = "",
                            imageVector = Icons.Outlined.ArrowBack,
                            tint = Color.Black,
                        )
                    }
                },
                title = {},
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(
                            contentDescription = "",
                            imageVector = Icons.Outlined.FavoriteBorder,
                            tint = Color.Black,
                        )
                    }
                }
            )
        }
    ) { innerpadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxSize()
                .safeDrawingPadding(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
                    .size(400.dp)
            ) {
                Image(
                    contentDescription = "",
                    painter = Image,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)
                ) {
                    Text(
                        text = name,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Left
                    )
                }
                Spacer(modifier = Modifier.weight(0.1f))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFC107), // Yellow star color
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = rate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.weight(0.2f))
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = descriptor,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light
                )
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, start = 8.dp, end = 15.dp)
                ) {

                    Text(
                        text = "Portion",
                        fontSize = 28.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.ExtraBold,
                        textAlign = TextAlign.Left
                    )
                    var l = remember { mutableStateOf(0) }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(35.dp)

                            .background(
                                color = colorResource(R.color.Original),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() }, // Prevents ripple
                                indication = null,
                                onClick = {
                                if (l.value != 0){
                                    l.value -= 1
                                }

                            }),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Remove,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(0.08f))
                    Text(
                        text = l.value.toString(),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.weight(0.08f))
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(35.dp)
                            .background(
                                color = colorResource(R.color.Original),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() }, // Prevents ripple
                                indication = null,
                                onClick = {
                                    l.value += 1
                                }
                            ),
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = "",
                            tint = Color.White
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(0.5f))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 15.dp, start = 8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .width(80.dp)
                            .background(
                                color = colorResource(R.color.Original),
                                shape = RoundedCornerShape(13.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = price,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }


                    Button(
                        onClick = {},
                        modifier = Modifier
                            .height(60.dp)
                            .width(200.dp),
                        colors = ButtonDefaults.buttonColors(
                            contentColor = colorResource(R.color.SaddleBrown),
                            containerColor = colorResource(R.color.BrowButton)
                        ),
                        elevation = null,
                        shape = RoundedCornerShape(13.dp)
                    ) {
                        Text(
                            text = "ORDER NOW",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = Color.White
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(0.7f))
            }

            Spacer(modifier = Modifier.weight(1f))
        }

    }

}


//@Preview
//@Composable
//fun test() {
//    ProductScreen()
//}