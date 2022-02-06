package com.example.study.flowphilipp

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


@HiltViewModel
class FlowViewModel @Inject constructor():ViewModel() {



    val countValue = flow<Int> {
        var a = 10
        while (a>0){
            delay(1000)
            a--
            emit(a)
        }
    }

   fun getCounterValu() = flow<Int> {

   }



}