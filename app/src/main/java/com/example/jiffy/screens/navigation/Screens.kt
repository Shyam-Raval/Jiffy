package com.example.jiffy.screens.navigation

sealed class Screens(val route : String){
    object Cart : Screens("Cart")
    object ProductDetails : Screens("product_details/{productId}"){
        fun createRoute(productId : String) = "product_details/$productId"

    }

    object Profile : Screens("Profile")

    object ProductList : Screens("product_List/{categoryId}"){
        fun createRoute(categoryId:String) = "product_list/$categoryId"
    }

    object CategoryList: Screens("category_list")
    //object

    object Login: Screens("Login")

    object SignUp:Screens("SignUp")

    object Home:Screens("Home")



}