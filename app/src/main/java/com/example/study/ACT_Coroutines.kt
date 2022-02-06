package com.example.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.study.databinding.ActivityActCoroutinesBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ACT_Coroutines : AppCompatActivity() {

    private lateinit var binding:ActivityActCoroutinesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityActCoroutinesBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        bindViews()

    }

    private fun bindViews() {
        // synchronous -> two sunchronoys must be aware of one another
        // asynchronous -> task are independent

        //suspend functions, Channels and Flows need to study

        studyScope()




    }



    suspend fun task1(){
        Log.i("task name 1",Thread.currentThread().name)
        for(i in 1..100){
            print(i)
        }
    }
    suspend fun task2(){
        Log.i("task name 2",Thread.currentThread().name)
    }

    fun task3(){
        Log.i("task name 3",Thread.currentThread().name)
    }



    fun studyScope(){
        /*

        Global scope: coriutine live long as application lives
                      if coroutine finished its job,it will be destroyed
                      if couroutines has some work to do and if we kill application, then couroutine will die


        Life cycle scope : coroutines launched  within the activiies dies when acivities dies.

        view model scope: coroutines in this scope will lives as long the vide model is lives

         */


        /*


         collect a flow, there are two option

        1 .collect()            -> Suspended fucntion we can call either from suspended or in corutine scope
        2 .launchIn()           -> not a suspending function

         */





    }


    suspend fun firstFun(){
        delay(8000)
        Log.d("muhammedasadpt","8000 millisecond delay : "+Thread.currentThread().name)
    }

    suspend fun secondFun(){
        delay(300)
        Log.d("muhammedasadpt","300 millisecond delay: "+Thread.currentThread().name)
    }









}