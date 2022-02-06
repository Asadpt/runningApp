package com.example.study.flowphilipp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.study.R
import com.example.study.databinding.ActivityFlowPhilippBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ACT_FlowPhilipp : AppCompatActivity() {

    val viewModel :FlowViewModel by viewModels()

    private lateinit var binding:ActivityFlowPhilippBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlowPhilippBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()
    }

    private fun bindViews() {




        lifecycleScope.launch {

             viewModel
                 .countValue
                 .filter {
                     (it%2 == 0) }
                 .collect {

                binding.counterTime.text = it.toString()
            }
        }

        lifecycleScope.launch {
            viewModel.getCounterValu().collect{

            }
        }


    }
}