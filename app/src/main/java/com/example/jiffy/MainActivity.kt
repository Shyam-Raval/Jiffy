package com.example.jiffy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jiffy.screens.cart.CartScreen
import com.example.jiffy.screens.home.BottomNavigationBar
import com.example.jiffy.screens.home.HomeScreen
import com.example.jiffy.screens.home.MyTopAppBar
import com.example.jiffy.screens.profile.ProfileScreen
import com.example.jiffy.ui.theme.JiffyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JiffyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    //navigatoin
                    val navController = rememberNavController()

                    // navhost
                    NavHost(
                        navController = navController,
                        startDestination = "Home"
                    ) {
                        composable("Home") {
                            HomeScreen(
                                navController = navController,
                                onProfileClick = { navController.navigate("Profile") },
                                onCartClick = { navController.navigate("Cart") }
                            )
                        }
                        composable("Cart") {
                            CartScreen(navController = navController)
                        }
                        composable("Profile") {
                            ProfileScreen({}, navController = navController)
                        }

                    }

                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    JiffyTheme {
        Greeting("Android")
    }
}