package com.codegameapp.foodfast.Screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import com.codegameapp.foodfast.R
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.codegameapp.foodfast.MVVM.FoodMVVM
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    MVVM: FoodMVVM,
    navController: NavController
) {
    var actives by remember { mutableStateOf(false) }
    var active by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val listfood by MVVM.listFood.observeAsState(emptyList())
//    val search by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }
    val allItems = remember { List(100) { "Item $it" } } // Example 100 items
    var displayedItems by remember { mutableStateOf(allItems.take(4)) } // Start with 10 items

    val listState = rememberLazyGridState()
    LaunchedEffect(Unit) {
            MVVM.FetchData_food()
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (visibleItems.isNotEmpty()) {
                    val lastVisibleItem = visibleItems.lastOrNull()?.index ?: 0
                    if (lastVisibleItem >= displayedItems.size - 2) { // When reaching near the end
                        if (displayedItems.size < allItems.size) {
                            displayedItems = allItems.take(displayedItems.size + 10) // Load 10 more
                        }
                    }

                    val firstVisibleItem = visibleItems.firstOrNull()?.index ?: 0
                    if (firstVisibleItem > 0) {
                        actives = true // Hide Row when scrolled down
                    } else {
                        actives = false // Show Row when scrolled back to the top
                    }

                }
            }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.weight(1f))
        AnimatedVisibility(
            visible = !actives, // Toggle visibility based on actives state
            enter = fadeIn(tween(durationMillis = 300)), // Fade in animation
            exit = fadeOut(tween(durationMillis = 300))  // Fade out animation
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.weight(0.1f))
                Column() {
                    Text(
                        text = "Fast Food",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.Black
                    )
                    Text(
                        text = "Order your favourite food!",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(R.drawable.prof),
                    contentDescription = "Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(50.dp)
                        .background(color = Color.Transparent)
                        .clip(RoundedCornerShape(10.dp))
                )
                Spacer(modifier = Modifier.weight(0.1f))

            }
        }

        Spacer(modifier = Modifier.weight(1f))
        SearchBar(
            shadowElevation = 4.dp,
            query = search,
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(end = if (active) 0.dp else 8.dp),
            colors = SearchBarDefaults.colors(containerColor = Color.White),
            onQueryChange = { search = it },
            onSearch = {},
            active = active,
            onActiveChange = { active = it },
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.DarkGray,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            },
            leadingIcon = {
                if (active) {
                    AnimatedVisibility(
                        visible = active,
                        exit = fadeOut(animationSpec = tween(50)) + slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(50, easing = FastOutSlowInEasing)
                        ),
                        enter = fadeIn(animationSpec = tween(50)) + slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(50, easing = FastOutSlowInEasing)
                        )
                    ) {
                        IconButton(onClick = { active = false }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close",
                                modifier = Modifier.size(25.dp),
                                tint = Color.DarkGray
                            )
                        }
                    }
                }
                else {
                    AnimatedVisibility(
                        visible = !active,
                        exit = fadeOut(animationSpec = tween(50)) + slideOutHorizontally(
                            targetOffsetX = { it },
                            animationSpec = tween(50, easing = FastOutSlowInEasing)
                        ),
                        enter = fadeIn(animationSpec = tween(50)) + slideInHorizontally(
                            initialOffsetX = { it },
                            animationSpec = tween(50, easing = FastOutSlowInEasing)
                        )
                    ) {

                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            modifier = Modifier.size(25.dp),
                            tint = Color.DarkGray
                        )

                    }
                    search = ""
                }
            })
        {


        }
        Spacer(modifier = Modifier.weight(1f))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(140.dp),
            state = listState,
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(top = 30.dp, bottom = 50.dp)
        ) {

                items(
                    listfood.size
                ) { item ->
                    val Image = rememberAsyncImagePainter(
                        model = ImageRequest.Builder(navController.context)
                            .data(listfood.get(item).imageUrl)
                            .crossfade(true)
                            .error(R.drawable.prof)
                            .placeholder(R.drawable.prof)
                            .build()
                    )
                    val name = listfood.get(item).name
                    val rate = listfood.get(item).rate.toString()
                    Product(image = Image,name,rate)
                }


        }


    }
}

@Composable
fun Product(
    image:AsyncImagePainter,
    name:String,
    Rate:String
) {

    Card(
        modifier = Modifier
            .width(140.dp)
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Product Image
            Image(
                painter =image, // Replace with actual image
                contentDescription = "Product Image",
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Product Name & Description
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black
            )
            Text(
                text = "Veggie Burger", fontSize = 14.sp, color = Color.Gray
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Rating & Favorite Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Star",
                        tint = Color(0xFFFFC107), // Yellow star color
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = Rate,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }

                IconButton(onClick = { /* Handle favorite click */ }) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = Color.Black
                    )
                }
            }
        }
    }


}


//@Preview
//@Composable
//fun test1() {
//
//    HomeScreen()
//}