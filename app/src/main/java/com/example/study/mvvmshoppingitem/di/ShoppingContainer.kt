package com.example.study.mvvmshoppingitem.di

import com.example.study.mvvmshoppingitem.ShoppingRepository
import com.example.study.mvvmshoppingitem.ShoppingViewModelFactory

class ShoppingContainer(shoppingRepository: ShoppingRepository) {


    val shoppingViewModelFactory = ShoppingViewModelFactory(shoppingRepository)
}