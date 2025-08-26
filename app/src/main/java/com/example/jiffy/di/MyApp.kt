package com.example.jiffy.di

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp:Application() {

    override fun onCreate(){
        super.onCreate()
        //initializes firebase firestore
        FirebaseApp.initializeApp(this)
    }

}