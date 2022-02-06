package com.example.study.mvvmshoppingitem

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.study.R
import com.example.study.databinding.FragmentAddShoppingItemBinding


class AddShoppingItem : DialogFragment() {

    private var listener:AddItemListener? = null
    fun setListener(listener: AddItemListener){
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var _binding:FragmentAddShoppingItemBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        _binding = FragmentAddShoppingItemBinding.inflate(inflater,container,false)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        binding.cancel?.setOnClickListener{
            dismiss()
        }

        binding.ok?.setOnClickListener {
            if(binding.name == null && binding.amount == null)
                return@setOnClickListener


          
            var item = ShoppingItem(binding.name.text.toString(),binding.amount.text.toString().toInt())
            listener?.clcikedOk(item)
            dismiss()

        }

    }


}