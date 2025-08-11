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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jiffy.model.Category


@Composable
fun HomeScreen(
    navController: NavController,
    onProfileClick:() -> Unit,
    onCartClick:() -> Unit,

) {
    Scaffold(
        topBar = { MyTopAppBar(onProfileClick,onCartClick) },
        bottomBar = { BottomNavigationBar() }
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
            //Search Result Section

            //Categories Section
            SectionTitle("Categories", "See All") {

            }

            //Featured Products Section
            //mock
            val categories: List<Category> = listOf(
                Category(
                    1,
                    "Electronics",
                    "https://cdn-icons-png.flaticon.com/512/1555/1555401.png"
                ),
                Category(2, "Clothing", "https://cdn-icons-png.flaticon.com/512/2935/2935183.png"),
                Category(3, "")

            )
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
                        }
                    )


                }


            }
            Spacer(modifier = Modifier.height(16.dp))


            SectionTitle("Featured", "See All") {

            }

            val productList = listOf(
                Product("1","Smartphone",999.99 , "https://s.alicdn.com/@sc04/kf/Ha00a1d79fb2b419080c34ff99eda0819F.jpg_720x720q50.jpg"),
                Product("2","Laptop",1499.99 , "https://laptopmedia.com/wp-content/uploads/2022/09/1-23-e1662987139646-680x427.jpg")

            )

            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(productList){ product->
                    FeaturedProductCard(product) {
                        //**click event
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))


        }

    }
}