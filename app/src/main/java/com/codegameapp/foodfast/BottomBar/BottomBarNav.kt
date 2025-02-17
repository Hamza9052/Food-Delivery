package com.codegameapp.foodfast.BottomBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.codegameapp.foodfast.Screen.items
import com.codegameapp.foodfast.R
@Composable
fun BottomBarNav(navController: NavController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation(
        modifier = Modifier
            .height(80.dp)
            .fillMaxWidth()
            .fillMaxWidth(),
        backgroundColor = colorResource(R.color.Original)
    ) {
        items.forEach {
            val isSelected = currentRoute == it.route
            BottomNavigationItem(
                icon = {
                    it.icon?.let {
                        Icon(
                            imageVector = it,
                            contentDescription = "",
                            modifier = Modifier.size(25.dp),
                            tint =if (isSelected) colorResource(R.color.LightCoral) else colorResource(R.color.White)
                        )
                    }
                },
                selected = isSelected,
                selectedContentColor = colorResource(R.color.Original),
                unselectedContentColor = Color.White.copy(alpha = 1f),
                onClick = {
                    it.route?.let { it1 ->
                        navController.navigate(it1) {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items

                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}