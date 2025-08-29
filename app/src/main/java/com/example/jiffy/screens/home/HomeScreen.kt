package com.example.jiffy.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jiffy.model.Category
import com.example.jiffy.screens.navigation.Screens
import com.example.jiffy.viewModels.CategoryViewModel
import com.example.jiffy.viewModels.ProductViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    onProfileClick:() -> Unit,
    onCartClick:() -> Unit,
    productViewModel: ProductViewModel = hiltViewModel(),
    categoryViewModel : CategoryViewModel = hiltViewModel()

) {
    Scaffold(
        topBar = { MyTopAppBar(onProfileClick,onCartClick) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)) {
            //search
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            SearchBar(
                query = searchQuery.value,
                onQueryChange = { searchQuery.value = it },
                onSearch = {

                    /*search logic*/

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)

            )

            SectionTitle("Categories", "See All") {
                navController.navigate("Categories")
            }

            //Featured Products Section

            //this is a stateflow  , we return a stateflow of list of categories
            val categoriesState = categoryViewModel.categories.collectAsState()
            val categories = categoriesState.value //compose watches for changes
            // categories in now just a list


            val selectedCategory = remember { mutableStateOf(0) }

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories.size) {
                    CategoryChip(
                        icon = categories[it].iconUrl,
                        text = categories[it].name,
                        isSelected = selectedCategory.value == it,
                        onClick = {
                            selectedCategory.value = it
                            navController.navigate(
                                Screens.ProductList.createRoute(categories[it].id.toString())
                            )
                        }
                    )


                }


            }
            Spacer(modifier = Modifier.height(16.dp))


            SectionTitle("Featured", "See All") {
                navController.navigate(
                    Screens.CategoryList.route
                )
            }

           productViewModel.getAllProductsInFirestore()

            val allProductState = productViewModel.allProduct.collectAsState()
            val allproductsFound = allProductState.value
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(allproductsFound){ product->
                    FeaturedProductCard(product) {
                        //**click event
                        navController.navigate(
                            Screens.ProductDetails.createRoute(product.id)
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))


        }

    }
}