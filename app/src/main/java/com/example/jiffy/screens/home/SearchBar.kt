package com.example.jiffy.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jiffy.screens.navigation.Screens
import com.example.jiffy.screens.products.ProductItem
import com.example.jiffy.viewModels.CartViewModel
import com.example.jiffy.viewModels.SearchViewModel

// -------------------- Search Bar --------------------
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(Color.LightGray.copy(alpha = 0.2f)),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.Gray)
            Spacer(modifier = Modifier.width(8.dp))
            TextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Search product..", color = Color.Gray, fontSize = 16.sp) },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(onSearch = { onSearch() }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledPrefixColor = Color.Transparent
                )
            )
        }
    }
}

// -------------------- Search Result Section --------------------
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SearchResultSection(
    navController: NavController,
    searchViewModel: SearchViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val searchResults = searchViewModel.searchResults.value
    val isSearching = searchViewModel.isSearching.value

    Column(modifier = Modifier.fillMaxSize()) {

        Text(
            text = "Search Results",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
        )

        if (isSearching) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(searchResults.size) { index ->
                    val product = searchResults[index]
                    ProductItem(
                        product = product,
                        onClick = {
                            navController.navigate(
                                Screens.ProductList.createRoute(product.id)
                            )
                        },
                        onAddToCart = { cartViewModel.addToCart(product) }
                    )
                }
            }
        }
    }
}
