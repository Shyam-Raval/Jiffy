package com.example.jiffy.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.jiffy.screens.home.Product

@Database(
    entities = [Product::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    //define DAO here to interact with DB
    abstract fun cartDao(): CartDao


    //singleton db instance
    companion object {
        @Volatile //visibility og changes across threads
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE?: synchronized(this){ //prevents race condition
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "cart_database"
                ).build()
                INSTANCE =  instance
                instance
            }

        }
    }
}