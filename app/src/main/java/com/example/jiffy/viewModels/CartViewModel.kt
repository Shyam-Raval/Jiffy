package com.example.jiffy.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.jiffy.repositories.CartRepository
import com.example.jiffy.screens.home.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CartRepository
) : ViewModel(){
    val cartItems = repository.allCartItems

    fun addToCart(product: Product) =
        viewModelScope.launch{
        repository.addtoCart(product)
    }

    fun removeFromCart(cartItem:Product) =
        viewModelScope.launch {
            repository.removeCartItem(cartItem)
        }
    fun clearCart() = viewModelScope.launch {
        repository.clearCart()
    }


    fun calculateTotal(items:List<Product>): Double{
        return items.sumOf { it.price }
    }


}