package com.example.jiffy.screens.home

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.jiffy.screens.navigation.Screens
import com.example.jiffy.viewModels.CartViewModel

@Composable
fun BottomNavigationBar(
    navController : NavController,

) {
    val cartViewModel: CartViewModel = hiltViewModel()
    val cartItemsState = cartViewModel.cartItems.collectAsState(initial = emptyList())

    val cartItems = cartItemsState.value // This is your List<Product>

    val currentRoute = ""
    val items = listOf(
        BottomNavItem(
            title = "Home",
            icon = Icons.Default.Home,
            route = Screens.Home.route
        ),
        BottomNavItem(
            title = "Category",
            icon = Icons.Default.Search,
            route = Screens.CategoryList.route
        ),

        BottomNavItem(
            title = "Cart",
            icon = Icons.Default.ShoppingCart,
            route = Screens.Cart.route,
            badgeCount = cartItems.size
        ),
        BottomNavItem(
            title = "Profile",
            icon = Icons.Default.Person,
            route = Screens.Profile.route,
        ),

    )
    NavigationBar(
      //  modifier = Modifier.height(82.dp),
        containerColor = Color.White,
        tonalElevation = 8.dp
    ){
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach{item ->
            NavigationBarItem(
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(badge = { Badge { Text(item.badgeCount.toString()) } }) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    else{
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title,
                            modifier = Modifier.size(24.dp)
                        )

                    }

                },
                label = { Text(item.title) },
                selected = currentRoute== item.route,
                onClick = {
                    //navigating b/w screens
                    navController.navigate(item.route){
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                },
                alwaysShowLabel = true

            )
        }
    }
}


data class BottomNavItem(
    val title : String,
    val icon : ImageVector,
    val route : String,
    val badgeCount: Int = 0
)


