package com.example.study.mvvmshoppingitem.di

import android.content.Context
import com.example.study.mvvmshoppingitem.ShoppingDatabase
import com.example.study.mvvmshoppingitem.ShoppingRepository
import com.example.study.mvvmshoppingitem.ShoppingViewModelFactory

class AppContainer(context: Context) {

    private val database = ShoppingDatabase(context)
     val repository = ShoppingRepository(database)
//    private val factory = ShoppingViewModelFactory(repository)


     var shoppingContainer:ShoppingContainer? = null






}