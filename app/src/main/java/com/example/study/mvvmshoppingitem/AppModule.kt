package com.example.study.mvvmshoppingitem

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideyourDatabase(@ApplicationContext context:Context)  = ShoppingDatabase.invoke(context)

    @Singleton
    @Provides
    fun getYourDao(db:ShoppingDatabase) = db.getShoppingDao()

}