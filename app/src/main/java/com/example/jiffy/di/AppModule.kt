package com.example.jiffy.di

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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

}