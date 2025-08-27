package com.example.jiffy.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jiffy.repositories.FirestoreRepository
import com.example.jiffy.screens.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository : FirestoreRepository
) :ViewModel() {
    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products



    fun fetchProducts(categoryId: String) {
        viewModelScope.launch {
            try {
                val products = repository.getProductsByCategory(categoryId)
                _products.value = products
            } catch (e: Exception) {
                Log.e("Tagy", "Error fetching products ${e.message}")
            }


        }
    }

    private val _allProducts = MutableStateFlow<List<Product>>(emptyList())
    val allProduct: StateFlow<List<Product>> get() = _allProducts

    fun getAllProductsInFirestore(){
        viewModelScope.launch{
            try{
                val allProducts = repository.getAllProductInFirestore()
                _allProducts.value = allProducts
            } catch(e : Exception){
                Log.e("Tagy", "Error fetching products ${e.message}")

            }
        }
    }
}