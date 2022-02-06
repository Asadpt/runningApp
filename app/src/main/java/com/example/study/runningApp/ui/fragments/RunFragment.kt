package com.example.study.runningApp.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.study.R
import com.example.study.databinding.FragmentRunBinding
import com.example.study.runningApp.Constant.REQUEST_COE_LOCATION_PERMISSION
import com.example.study.runningApp.adapter.ADPT_Run
import com.example.study.runningApp.other.TrackingUtilty
import com.example.study.runningApp.ui.RunningViewModel
import dagger.hilt.android.AndroidEntryPoint
import hilt_aggregated_deps._com_example_study_runningApp_di_AppModuleForRun
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import java.util.jar.Manifest

@AndroidEntryPoint
class RunFragment : Fragment(),EasyPermissions.PermissionCallbacks {

    private val viewModel:RunningViewModel by viewModels()
    var adptRun = ADPT_Run()

    private var _binding:FragmentRunBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentRunBinding.inflate(inflater,container,false)

        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews()
        requestPermissions()
    }

    private fun bindViews() {
        binding.addFab.setOnClickListener{
            findNavController().navigate(R.id.action_runFragment_to_trackiingFragamnt)
        }

        binding.runRV.layoutManager = LinearLayoutManager(context)
        binding.runRV.adapter = adptRun

        viewModel.runSortedByDate?.observe(viewLifecycleOwner, Observer {
            adptRun.list = it
            adptRun.notifyDataSetChanged()
        })

    }


    private fun requestPermissions(){
        if(TrackingUtilty.hasLocationPermisions(requireContext())){
            return
        }
        if(Build.VERSION.SDK_INT<Build.VERSION_CODES.Q){
            EasyPermissions.requestPermissions(this,"you need to accept location permission",
            REQUEST_COE_LOCATION_PERMISSION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION)
        }else{
            EasyPermissions.requestPermissions(this,"you need to accept location permission",
                REQUEST_COE_LOCATION_PERMISSION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        }

    }



    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            AppSettingsDialog.Builder(this).build().show()
            }else{
                requestPermissions()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }


}