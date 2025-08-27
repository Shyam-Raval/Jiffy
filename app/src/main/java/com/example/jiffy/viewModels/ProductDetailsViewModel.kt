package com.example.jiffy.viewModels

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
class ProductDetailsViewModel @Inject constructor(
    private val repository: FirestoreRepository
): ViewModel(){
    private val _product = MutableStateFlow<Product?>(null)
    val product: StateFlow<Product?> get() = _product

    fun fetchProductDetails(productId : String){
        viewModelScope.launch{
            try{
                val product  = repository.getProductById(productId)
                _product.value = product
            }
            catch (e:Exception){

            }
        }
    }
}