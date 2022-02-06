package com.example.study

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.bumptech.glide.Glide


@BindingAdapter("load_image")
fun ImageView.loadMyImage(url:String){
    this.load(url)
}

@BindingAdapter("load_second_image")
fun ImageView.loadSecond(imageUrl:String){
    Glide.with(this).load(imageUrl).into(this)
}

@BindingAdapter("load_third_image")
fun loadThirdImageUrl(imageView:ImageView,url:String){
    Glide.with(imageView).load(url).into(imageView)
}