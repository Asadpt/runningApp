package com.example.study

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.study.coroutinephilip.ACT_CourotinePhilipp
import com.example.study.databinding.ActivityMainBinding
import com.example.study.flowphilipp.ACT_FlowPhilipp
import com.example.study.mvvmshoppingitem.ShoppingActivity
import com.example.study.mvvmshoppingitem.ShoppingHiltActivity
import com.example.study.runningApp.ui.MainActivityRunning

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root)

        bindViews()
    }

    private fun bindViews() {
        binding.dataBinding?.setOnClickListener {
            startActivity(Intent(this,ACT_DataBinding::class.java))
        }

        binding.coroutineStudy?.setOnClickListener {
            startActivity(Intent(this,ACT_Coroutines::class.java))
        }

        binding.shoppingApp?.setOnClickListener {
            startActivity(Intent(this,ShoppingActivity::class.java))
        }

        binding.shoppingAppHilt?.setOnClickListener {
            startActivity(Intent(this,ShoppingHiltActivity::class.java))
        }

        binding.runningApp?.setOnClickListener {
            startActivity(Intent(this, MainActivityRunning::class.java))
        }

        binding.flowPhilippLackner?.setOnClickListener {
            startActivity(Intent(this, ACT_FlowPhilipp::class.java))
        }

        binding.coroutinePhilipLackner?.setOnClickListener {
            startActivity(Intent(this, ACT_CourotinePhilipp::class.java))
        }


    }
}