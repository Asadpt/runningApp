package com.example.study.mvvmshoppingitem

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.study.mvvmshoppingitem.di.Factory

class ShoppingViewModelFactory(private val repository: ShoppingRepository):
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShoppingViewModel(repository) as T
    }
}

//class ShoppingViewModelFactory(private val repository: ShoppingRepository) : Factory<ShoppingViewModel>{
//
//    override fun create(): ShoppingViewModel {
//        return ShoppingViewModel(repository)
//    }
//
//}