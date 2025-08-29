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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
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
import com.example.jiffy.screens.profile.LoginScreen
import com.example.jiffy.screens.profile.ProfileScreen
import com.example.jiffy.screens.profile.SignUpScreen
import com.example.jiffy.ui.theme.JiffyTheme
import com.example.jiffy.viewModels.AuthViewModel
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
                    //AuthViewModel
                    val authViewModel : AuthViewModel = hiltViewModel()

                    val isLoggedIn by remember {
                        derivedStateOf {
                            authViewModel.isLoggedIn
                        }
                    }



                    // navhost
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.route
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
                            ProfileScreen(onSignOut = {
                                authViewModel.signOut()
                                navController.navigate(Screens.Login.route)
                            }, navController = navController)
                        }
                        composable("Categories") {
                            CategoryScreen(navController ,
                                onCartClick = {
                                    navController.navigate(Screens.Cart.route)
                                },
                                onProfileClick = {
                                    //switch for logging in or display profile

                                    if(isLoggedIn){
                                        navController.navigate(Screens.Home.route)
                                    }
                                    else{
                                        navController.navigate(Screens.Login.route)
                                    }
                                }
                                )
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
                        composable(Screens.Login.route){
                            LoginScreen(
                                onNavigateToSignUp = {
                                    navController.navigate(Screens.SignUp.route)
                                },
                                onLoginSuccess = {
                                    navController.navigate(Screens.Home.route)

                                }
                            )
                        }

//                        composable(Screens.CategoryList.route){
//                            CategoryScreen(navController)
//                        }
//


                    }

                }
            }
        }
    }
}

