package com.example.study.runningApp.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.fragment.findNavController
import com.example.study.R
import com.example.study.databinding.FragmentRunBinding
import com.example.study.databinding.FragmentSetUpBinding
import com.example.study.runningApp.Constant.KEY_FIRST_TIME_TOGGLE
import com.example.study.runningApp.Constant.KEY_NAME
import com.example.study.runningApp.Constant.KEY_WEIGHT
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SetUpFragment : Fragment() {

    @Inject
    lateinit var sharedPrefe:SharedPreferences

    @set:Inject
    var isFirstAppOpen = true



    private var _binding: FragmentSetUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSetUpBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(!isFirstAppOpen){
            val navOption = NavOptions.Builder()
                .setPopUpTo(R.id.setUpFragment,true).build()

            findNavController().navigate(R.id.action_setUpFragment_to_runFragment,savedInstanceState,navOption)
        }
        bindViews()
    }

    private fun bindViews() {
        binding.Continue?.setOnClickListener {
            val success = writePersonalDataToSharedPreference()
            if(success)
            findNavController().navigate(R.id.action_setUpFragment_to_runFragment)
            else
            {
                Snackbar.make(requireView(),"Please enter all fields",Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun writePersonalDataToSharedPreference():Boolean{
        val name = binding.eNAme.text.toString()
        val weight = binding.eWeight.text.toString()

        if(name.isEmpty() || weight.isEmpty())
            return false

        sharedPrefe.edit().putString(KEY_NAME,name)
            .putFloat(KEY_WEIGHT,weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE,false)
            .apply()
        return true
    }


}