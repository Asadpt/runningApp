package com.example.study

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DataBindingViewModel:ViewModel() {


        var name  = MutableLiveData<String>()

        fun setAMesage(message:String) {
            name?.value = message
        }

}