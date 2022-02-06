package com.example.study.runningApp.ui.fragments

import android.content.Intent
import android.hardware.Camera
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.study.R
import com.example.study.databinding.FragmentRunBinding
import com.example.study.databinding.FragmentTrackiingFragamntBinding
import com.example.study.runningApp.Constant.ACTION_PAUSE_SERVICE
import com.example.study.runningApp.Constant.ACTION_START_OR_RESUME_SERVICE
import com.example.study.runningApp.Constant.ACTION_STOP_SERVICE
import com.example.study.runningApp.Constant.MAP_ZOOM
import com.example.study.runningApp.Constant.POLYLINE_COLOR
import com.example.study.runningApp.Constant.POLYLINE_WIDTH
import com.example.study.runningApp.db.Run
import com.example.study.runningApp.other.TrackingUtilty
import com.example.study.runningApp.services.TrackingServises
import com.example.study.runningApp.services.polyline
import com.example.study.runningApp.services.polylines
import com.example.study.runningApp.ui.RunningViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.Polyline
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.math.round


@AndroidEntryPoint
class TrackiingFragamnt : Fragment() {

    private val viewmodel :RunningViewModel by viewModels()
    private var map:GoogleMap? = null
    private var isTracking = false
    private var pathPoints = mutableListOf<polyline>()

    private var _binding: FragmentTrackiingFragamntBinding? = null
    private val binding get() = _binding!!

    private var currenttimeInMilli = 0L

    private var menu:Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        setHasOptionsMenu(true)

        _binding = FragmentTrackiingFragamntBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)
        bindViews()
    }

    private fun bindViews() {
        binding.mapView.getMapAsync {
            map = it
            addAllPolylines()
        }
        binding.startButton?.setOnClickListener {
            toggleRun()
        }

        binding.btnFinishRun?.setOnClickListener {
            zoomToSeeWholeTrack()
            endRunAndSavetoDB()
        }
        subscribeObserver()
    }


    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }

    private fun sendCommandTOService(action:String){

        Intent(requireContext(),TrackingServises::class.java).also {
            it.action = action
            requireActivity().startService(it)
        }

    }

    private fun addLatestPolyine(){
       if(pathPoints.isNotEmpty() && pathPoints.last().size >1){
           val preLastLatlong = pathPoints.last()[pathPoints.last().size-2]
           val lastLatlong = pathPoints.last().last()

           val polyLineOption = PolylineOptions().color(POLYLINE_COLOR).width(POLYLINE_WIDTH).add(preLastLatlong).add(lastLatlong)
           map?.addPolyline(polyLineOption)
       }
    }

    private fun addAllPolylines(){
        for(polyline in pathPoints){
            val polylineOption = PolylineOptions().color(POLYLINE_COLOR).width(POLYLINE_WIDTH).addAll(polyline)
            map?.addPolyline(polylineOption)
        }
    }

    private fun moveCameratoUser(){
        if(pathPoints?.isNotEmpty() && pathPoints.last().isNotEmpty()){
            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(pathPoints.last().last(), MAP_ZOOM))
        }
    }

    private fun updateTracking(isTracking:Boolean){
        this.isTracking = isTracking
        if(!isTracking){
            binding.startButton.text = "Start"

        }else{
            menu?.getItem(0)?.isVisible = true
            binding.startButton.text = "Stop"
        }
    }

    private fun toggleRun(){
        if(isTracking){
            menu?.getItem(0)?.isVisible = true
            sendCommandTOService(ACTION_PAUSE_SERVICE)
        }else{
            sendCommandTOService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun subscribeObserver(){
        TrackingServises.isTracking.observe(this, Observer {
            updateTracking(it)
        })

        TrackingServises.pathPoints?.observe(this, Observer {
            pathPoints = it
            addLatestPolyine()
            moveCameratoUser()
        })

        TrackingServises.timesRunInMilliSeconds?.observe(this, Observer {
            currenttimeInMilli = it
            val formatedTime =  TrackingUtilty.getFormattedStopWatchTime(currenttimeInMilli,true)
            binding.timerText.text = formatedTime
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.toolbar_tracking_menu,menu)
        this.menu = menu

    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)

        if(currenttimeInMilli > 0){
            this.menu?.getItem(0)?.isVisible = true


        }
    }

    private fun showcancelTrackingDialog(){
        val dialog = MaterialAlertDialogBuilder(requireContext())
            .setTitle("cancel the run ?")
            .setMessage("are you sureto cancel the run and delete all related data")
            .setIcon(R.drawable.common_google_signin_btn_icon_dark)
            .setPositiveButton("Yes"){_,_->
                stopRun()
            }
            .setNegativeButton("No"){dialogInterface,_ ->
                dialogInterface.cancel()
            }
            .create()
        dialog.show()
    }

    private fun stopRun(){
        sendCommandTOService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackiingFragamnt_to_runFragment)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.miCancelTracking ->{
                showcancelTrackingDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun zoomToSeeWholeTrack(){
        val bounds = LatLngBounds.Builder()
        for(polyine in pathPoints){
            for(pos in polyine){
                bounds.include(pos)
            }
        }

        map?.moveCamera(CameraUpdateFactory.newLatLngBounds(
            bounds.build(),
            binding.mapView.width,
            binding.mapView.height,
            (binding.mapView.height*0.05f).toInt()
        ))

    }

    private fun endRunAndSavetoDB(){
        map?.snapshot {
            var distanceInmeters = 0
            for(polyline in pathPoints){
                distanceInmeters += TrackingUtilty.calcuatePolylineLength(polyline).toInt()
            }
            val avgSpeed = round ((distanceInmeters/1000f) /(currenttimeInMilli/1000f/60/60)  * 10)/10f
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInmeters/1000f)*weight).toInt()
            val run = Run(it,dateTimeStamp,avgSpeed,distanceInmeters,currenttimeInMilli,caloriesBurned)

            viewmodel.insertRun(run)
            stopRun()
        }
    }

    @set:Inject
    var weight = 80f

}