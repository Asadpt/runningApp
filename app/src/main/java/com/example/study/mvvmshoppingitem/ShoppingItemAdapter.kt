package com.example.study.mvvmshoppingitem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.study.R
import com.example.study.databinding.ShoppingItemBinding

class ShoppingItemAdapter
    (var items:List<ShoppingItem>, private val viewmodel:ShoppingViewModel)
    :RecyclerView.Adapter<ShoppingItemAdapter.ItemHolder>() {

     class ItemHolder(val binding:ShoppingItemBinding) :RecyclerView.ViewHolder(binding.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val binding = ShoppingItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        var curretItem = items?.get(position)
        holder?.binding.tvName.text = curretItem.name
        holder?.binding.tvAmount.text = ""+curretItem.amount
        holder?.binding.tvDelete.setOnClickListener {
            viewmodel.delete(curretItem)
        }

        holder?.binding.tvAdd?.setOnClickListener {
            curretItem.amount++
            viewmodel.upsert(curretItem)
        }

        holder?.binding.tvMinus?.setOnClickListener {
            if(curretItem.amount > 0){
                curretItem.amount--
                viewmodel.upsert(curretItem)
            }

        }
    }

    override fun getItemCount(): Int {
        return items?.size
    }
}