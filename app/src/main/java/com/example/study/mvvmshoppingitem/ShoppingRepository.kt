package com.example.study.mvvmshoppingitem

import javax.inject.Inject

class ShoppingRepository @Inject constructor(private val db:ShoppingDatabase) {


    suspend fun upsert(item: ShoppingItem) = db.getShoppingDao().upsert(item)

    suspend fun delete(item: ShoppingItem) = db.getShoppingDao().delete(item)

    fun getAllShoppingItems() = db.getShoppingDao().getAllShoppingItems()





}