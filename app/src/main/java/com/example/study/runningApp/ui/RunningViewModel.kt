package com.example.study.runningApp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.study.runningApp.db.Run
import com.example.study.runningApp.db.RunningRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RunningViewModel @Inject constructor(val repository:RunningRepository):ViewModel() {

    fun insertRun(run: Run) = viewModelScope.launch {
        repository.insertRun(run)
    }

    val runSortedByDate = repository.getAllRunSortedByDate()



}