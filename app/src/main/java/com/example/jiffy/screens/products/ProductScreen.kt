package com.example.jiffy.screens.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jiffy.screens.home.Product
import com.example.jiffy.screens.navigation.Screens
import com.example.jiffy.viewModels.ProductViewModel

@Composable
fun ProductScreen(
    categoryId: String,
    navController: NavController,
    productViewModel : ProductViewModel = hiltViewModel()
) {
    //fetch models from view model
    LaunchedEffect(categoryId) {
        productViewModel.fetchProducts(categoryId)


    }
    //collect the products from view model
    val productState = productViewModel.products.collectAsState()
    val products = productState.value



    //display product
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "Products of Category ID: $categoryId",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(16.dp)
        )
        //if no produt
        if (products.isEmpty()) {
            Text(
                text = "No Product Found!",
                modifier = Modifier.padding(16.dp)

            )


        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(products) { product ->
                    ProductItem( product = product,
                        onClick = {
                            //navigate
                            navController.navigate(Screens.ProductDetails.createRoute(product.id))
                        },
                        onAddToCart = {
                            //add product to cart
                            // using room db


                        })
                }
            }
        }
    }

}