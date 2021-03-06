package com.example.study.mvvmshoppingitem

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


@Database(entities = [ShoppingItem::class], version = 1)
abstract class ShoppingDatabase  :RoomDatabase() {

    abstract fun getShoppingDao():ShoppingDao

    companion object{

        @Volatile
        private var instance:ShoppingDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this){
            instance?:createDatabase(context).also {
                instance = it
            }

        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context.applicationContext,
                ShoppingDatabase::class.java,
                "shoppingDB.db").build()


    }





}