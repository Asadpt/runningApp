package com.example.study.coroutinephilip

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.study.R
import com.example.study.databinding.ActivityCourotinePhilippBinding
import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

class ACT_CourotinePhilipp : AppCompatActivity() {

    private lateinit var binding:ActivityCourotinePhilippBinding
    val TAG = "ACT_CourotinePhilipp"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCourotinePhilippBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() {
        /*

        -> many courotine can run in one thread
        -> suspend function, so we can pause and restart functions
        -> change the context
        -> when we kill app, main thread finished so, couroutine get stopped

         */


/*
        GlobalScope.launch {
            Log.d(TAG, "thread1 ${Thread.currentThread().name}")
        }

        Log.d(TAG, "thread2 ${Thread.currentThread().name}")

        GlobalScope.launch(Dispatchers.Main) {
            Log.d(TAG, "thread3 ${Thread.currentThread().name}")
        }

        GlobalScope.launch(Dispatchers.IO) {
            Log.d(TAG, "thread4 ${Thread.currentThread().name}")
        }
        GlobalScope.launch(Dispatchers.Default) {
            Log.d(TAG, "thread5 ${Thread.currentThread().name}")
        }

        GlobalScope.launch(Dispatchers.Unconfined) {
            Log.d(TAG, "thread6 ${Thread.currentThread().name}")
        }
        GlobalScope.launch(newSingleThreadContext("asad")) {
            Log.d(TAG, "thread7 ${Thread.currentThread().name}")
        }

*/

//        GlobalScope.launch(Dispatchers.IO) {
//            Log.d(TAG, "thread name 1 ${Thread.currentThread().name}")
//            withContext(Dispatchers.Main){
//                Log.d(TAG, "thread name 2 ${Thread.currentThread().name}")
//                binding.textField.text = call1()
//            }
//
//
//
//        }


        /*


        run block will block the main thread. below it will bloack main thread by three seconds

        runBlocking {
            delay(3000)
            Log.d(TAG, "thread name 1 ${Thread.currentThread().name}")
        }



         */


//            GlobalScope.launch(Dispatchers.Main) {
//                delay(5000)
//                Log.d(TAG, "thread name 2 ${Thread.currentThread().name}")
//            }
//
//            runBlocking {
//                delay(3000)
//                Log.d(TAG, "thread name 1 ${Thread.currentThread().name}")
//            }

/*

            GlobalScope.launch(Dispatchers.Main) {
                delay(5000)
                Log.d(TAG, "thread name 1 ${Thread.currentThread().name}")
            }

            GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            Log.d(TAG, "thread name 2 ${Thread.currentThread().name}")
            }

            GlobalScope.launch(Dispatchers.Main) {
            delay(5000)
            Log.d(TAG, "thread name 3 ${Thread.currentThread().name}")
            }

*/

//        val job = GlobalScope.launch {
//
//            //after 3 seconds, job gets cancelled automatically
//            withTimeout(3000){
//                repeat(10){
//                    Log.d(TAG, "thread name 1 ${Thread.currentThread().name}")
//                    delay(845)
//                }
//            }
//
//
//        }



//        CoroutineScope(Dispatchers.IO).launch {
//            delay(5000)
//            job.cancel()
//            Log.d(TAG, "finished")
//
//
//        }

        GlobalScope.launch(Dispatchers.IO) {

            val v1 = async { call1() }.await()
            val v2 = async { call2(v1) }.await()



            withContext(Dispatchers.Main){
                Toast.makeText(this@ACT_CourotinePhilipp,"value is $v2",Toast.LENGTH_SHORT).show()

            }

            Log.d(TAG,"time measered: $v2")




        }




















    }

    suspend fun call1() :String{
        delay(5000)
        return "hello asad"
    }

    suspend fun call2(value:String) :String{
        delay(5000)
        return value+" call2"
    }







}