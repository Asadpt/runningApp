package com.example.study.mvvmshoppingitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study.R
import com.example.study.databinding.ActivityShoppingBinding
import com.example.study.databinding.ActivityShoppingHiltBinding
import com.example.study.mvvmshoppingitem.di.AppContainer
import com.example.study.mvvmshoppingitem.di.ShoppingContainer
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShoppingHiltActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShoppingHiltBinding
    private val viewmodel :ShoppingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityShoppingHiltBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindViews()

    }

    private fun bindViews() {
        binding.add?.setOnClickListener {
            var dlgf = AddShoppingItem()
            dlgf.setListener(object :AddItemListener{
                override fun clcikedOk(item: ShoppingItem) {
                    viewmodel.upsert(item)
                }

            })
            dlgf.show(supportFragmentManager,"asad")

        }

        val adapter = ShoppingItemAdapter(listOf(), viewmodel)
        binding.itemRV?.layoutManager = LinearLayoutManager(this)
        binding.itemRV.adapter = adapter

        viewmodel.getAllShoppingItems().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })


    }
}