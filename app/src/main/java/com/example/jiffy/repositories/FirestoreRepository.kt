package com.example.jiffy.repositories

import android.util.Log
import com.example.jiffy.model.Category
import com.example.jiffy.screens.home.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton  //only one instnace exist
class FirestoreRepository @Inject constructor(
    private val firestore : FirebaseFirestore
) {
    //returns a flow which is list of category
    //flow is synchronous data stream , only start running when updated
    fun getCategoriesFlow(): Flow<List<Category>> =
        callbackFlow {
            val listenerRegistration = firestore
                .collection( "categories")
                .addSnapshotListener{snapshot , error ->
                    if(error != null){
                        println("Error fetching categories : ${error.message}")
                        return@addSnapshotListener
                    }
                    if(snapshot != null){
                        val categories = snapshot.toObjects(Category::class.java)
                        trySend(categories)
                    }
                }

            //Close the flow when the listener is no longer needed

            awaitClose{
                listenerRegistration.remove()
            }





        }

    suspend fun getProductByCategory(categoryId:String): List<Product>{
        return try{
            val result = firestore.collection("products")
                .whereEqualTo("categoryId" , categoryId)
                .get()
                .await()

                result.toObjects(Product::class.java).also {
                    Log.v("Tagy" , "Mapped product:$it")
                }

        }catch(e:Exception){
            emptyList()

        }
    }

    suspend fun getProductById(productId:String):Product?{
        return try{
            val result = firestore.collection("products")
                .document(productId)
                .get()
                .await()

            result.toObject(Product::class.java)
        }catch (e:Exception){
            null
        }
    }

    suspend fun getAllProductInFirestore():List<Product>{
        return try{
            val allProducts = firestore.collection("products")
                .get()
                .await()
                .documents
                .mapNotNull{it.toObject(Product::class.java)}

            allProducts
        }catch(e : Exception){
            emptyList()
        }
    }

}