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
import com.example.jiffy.screens.categories.CategoryScreen
import com.example.jiffy.screens.home.BottomNavigationBar
import com.example.jiffy.screens.home.HomeScreen
import com.example.jiffy.screens.home.MyTopAppBar
import com.example.jiffy.screens.navigation.Screens
import com.example.jiffy.screens.products.ProductDetailsScreens
import com.example.jiffy.screens.products.ProductScreen
import com.example.jiffy.screens.profile.ProfileScreen
import com.example.jiffy.screens.profile.SignUpScreen
import com.example.jiffy.ui.theme.JiffyTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
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
                        composable(Screens.Home.route) {
                            HomeScreen(
                                navController = navController,
                                onProfileClick = { navController.navigate("Profile") },
                                onCartClick = { navController.navigate(Screens.Cart.route) }
                            )
                        }
                        composable(Screens.Cart.route) {
                            CartScreen(navController = navController)
                        }
                        composable(Screens.Profile.route) {
                            ProfileScreen({}, navController = navController)
                        }
                        composable("Categories") {
                            CategoryScreen(navController)
                        }
                        composable(Screens.ProductDetails.route) {
                            val productId = it.arguments?.getString("productId")
                            if(productId != null){
                                ProductDetailsScreens(productId)
                            }
                        }

                        composable(Screens.ProductList.route) {
                            val categoryId = it.arguments?.getString("categoryId")
                            if(categoryId != null){
                                ProductScreen(categoryId , navController = navController)
                            }
                        }
                        composable(Screens.SignUp.route) {
                            SignUpScreen(
                                onNavigateToLogin = {
                                    navController.navigate(Screens.Login.route)
                                },
                                onSignupSuccess = {
                                    navController.navigate(Screens.Home.route)
                                }
                            )
                        }

                        composable(Screens.CategoryList.route){
                            CategoryScreen(navController)
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