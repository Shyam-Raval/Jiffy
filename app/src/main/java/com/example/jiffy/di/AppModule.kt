package com.example.jiffy.di

import android.app.Application
import android.content.Context
import com.example.jiffy.repositories.CartRepository
import com.example.jiffy.room.AppDatabase
import com.example.jiffy.room.CartDao
import com.example.jiffy.screens.navigation.Screens
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule{
      @Provides         //provider method
      @Singleton        //only one stance is created
      fun providerFirebaseFirestore(): FirebaseFirestore {
          return FirebaseFirestore.getInstance()
      }

        @Provides
        @Singleton
        fun provideAppDataBase(@ApplicationContext applContext: Context): AppDatabase {
            return AppDatabase.getDatabase(applContext )
        }

    @Provides
    fun provideCartDao(appDatabase:AppDatabase): CartDao {
        return appDatabase.cartDao()
    }

    @Provides
    fun provideCartRepository(cartDao:CartDao):
            CartRepository{
        return CartRepository(cartDao)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = Firebase.auth



}