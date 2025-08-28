package com.example.jiffy.repositories

import android.util.Log
import android.widget.Toast
import com.example.jiffy.room.CartDao
import com.example.jiffy.screens.home.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

//act bridge b/w viewmodel and roomdb
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) {
    val allCartItems: Flow<List<Product>> = cartDao.getAllCartItems()

    suspend fun addtoCart(product : Product){
        val existingItem = cartDao.getCartItemById(product.id)
        if(existingItem!=null){
            Log.v("TAGY" , "Product already added")
             

        }
        else{
            cartDao.insertCartItem(product)
            Log.v("TAGY","product added")
        }

    }

    suspend fun removeCartItem(product: Product){
        cartDao.deleteCartItem(product)
        Log.v("TAGY","Product removed")
    }

    suspend fun clearCart(){
        cartDao.clearCart()
    }


}
