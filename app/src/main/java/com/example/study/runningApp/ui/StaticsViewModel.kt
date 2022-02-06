package com.example.study.runningApp.ui

import androidx.lifecycle.ViewModel
import com.example.study.runningApp.db.RunningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StaticsViewModel @Inject constructor(val repository:RunningRepository):ViewModel() {







}