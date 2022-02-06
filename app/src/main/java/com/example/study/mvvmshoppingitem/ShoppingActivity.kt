package com.example.study.mvvmshoppingitem

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study.databinding.ActivityShoppingBinding
import com.example.study.mvvmshoppingitem.di.AppContainer
import com.example.study.mvvmshoppingitem.di.ShoppingContainer

class ShoppingActivity : AppCompatActivity() {

    private lateinit var binding:ActivityShoppingBinding
    private lateinit var appContainer:AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityShoppingBinding.inflate(layoutInflater)
        setContentView(binding.root)

////        val database = ShoppingDatabase(this)
////        val repository = ShoppingRepository(database)
////        val factory = ShoppingViewModelFactory(repository)
//        appContainer = (application as MyApplication).appContainer
//        appContainer.shoppingContainer = ShoppingContainer(appContainer.repository)
//
////        val viewmodel = ViewModelProvider(this,factory).get(ShoppingViewModel::class.java)
////        val viewmodel = appContainer?.shoppingContainer?.shoppingViewModelFactory?.create()
//
//        val viewmodel = appContainer?.shoppingContainer?.shoppingViewModelFactory?.create(ShoppingViewModel::class.java)
//
//        bindViews(viewmodel!!)
    }

//    private fun bindViews(viewmodel: ShoppingViewModel) {
//        binding.add?.setOnClickListener {
//            var dlgf = AddShoppingItem()
//            dlgf.setListener(object :AddItemListener{
//                override fun clcikedOk(item: ShoppingItem) {
//                    viewmodel.upsert(item)
//                }
//
//            })
//            dlgf.show(supportFragmentManager,"asad")
//
//        }
//
//        val adapter = ShoppingItemAdapter(listOf(), viewmodel)
//        binding.itemRV?.layoutManager = LinearLayoutManager(this)
//        binding.itemRV.adapter = adapter
//
//        viewmodel.getAllShoppingItems().observe(this, Observer {
//            adapter.items = it
//            adapter.notifyDataSetChanged()
//        })
//
//
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        appContainer.shoppingContainer = null
//    }
}