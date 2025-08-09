package com.example.jiffy.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager


@Composable
fun HomeScreen() {
    Scaffold(
        topBar = { MyTopAppBar() },
        bottomBar = { BottomNavigationBar() }
    ) {paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues)){
            //search
            val searchQuery = remember { mutableStateOf("") }
            val focusManager = LocalFocusManager.current
            //categoris
            //featured
           //featured product

        }

    }
}