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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.example.jiffy.model.Category


@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { MyTopAppBar() },
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
            Spacer(modifier = Modifier.height(16.dp))


        }

    }
}