package com.example.study

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.study.databinding.ActDataBindingBinding

class ACT_DataBinding : AppCompatActivity() {

    private lateinit var binding:ActDataBindingBinding
    private lateinit var myViewModel:DataBindingViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        binding = DataBindingUtil.setContentView(this,R.layout.act_data_binding)
        binding = ActDataBindingBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        myViewModel = ViewModelProvider(this).get(DataBindingViewModel::class.java)


        binding?.myViewModel = myViewModel
        binding?.lifecycleOwner = this


        bindVIews()

    }

    private fun bindVIews() {

//        myViewModel?.name?.observe(this,{
//            binding.nameField.text = it
//        })

//        var userModel = User("muhammed","asad","asad@yopmail.com",true)
//        binding?.user = userModel


        binding?.myEditText?.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
               myViewModel?.setAMesage(p0.toString())
            }

        })


        var myUserData = User("muhammed",
            "asad",
            "asad@gmail.com",
            true,
            "https://qph.fs.quoracdn.net/main-thumb-1278318002-200-ydzfegagslcexelzgsnplcklfkienzfr.jpeg",
            "https://i.pinimg.com/236x/11/55/7f/11557f02cfbbd1a6f70e3aa19062752c--face-profile-profile-girl.jpg",
            "https://expertphotography.b-cdn.net/wp-content/uploads/2020/08/profile-photos-4.jpg")


        binding.user = myUserData



    }



}