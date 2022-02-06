package com.example.study.runningApp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.study.databinding.RowRunBinding
import com.example.study.runningApp.db.Run

class ADPT_Run: RecyclerView.Adapter<ADPT_Run.RunHolder>() {

    var list:List<Run> = ArrayList()
    var context:Context? = null

    class RunHolder(val binding:RowRunBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunHolder {
        context = parent?.context
        val binding = RowRunBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RunHolder(binding)
    }

    override fun onBindViewHolder(holder: RunHolder, position: Int) {
        var item = list.get(position)

        holder.binding.apply {
            Glide.with(context!!).load(item.img).into(screenShot)
            totalDistance?.text = ""+(item.distanceInMeters/1000)+" km"
            averageSpeed.text = ""+item.avgSpeedInKMH+" km/h"
            calorieBurned.text = ""+item.caloriesBurned+" kcal"
        }

    }

    override fun getItemCount(): Int {
       return list.size
    }
}