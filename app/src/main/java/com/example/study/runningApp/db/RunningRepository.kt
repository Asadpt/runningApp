package com.example.study.runningApp.db

import androidx.room.Index
import javax.inject.Inject

class RunningRepository @Inject constructor(
    val runDao:RunDao
) {

    suspend fun insertRun(run: Run) = runDao.insertRun(run)
    suspend fun deleteRun(run: Run) = runDao.deleteRun(run)

    fun getAllRunSortedByDate() = runDao.getAllRunSortedByDate()
    fun getAllRunSortedByDistance() = runDao.getAllRunSortedByDistance()
    fun getAllRunSortedByTimeInMillisconds() = runDao.getAllRunSortedByTimeInMillisc()
    fun getAllRunSortedByAverageSpeed() = runDao.getAllRunSortedByAverageSpeed()
    fun getAllRunSortedByCaloiesBurned() = runDao.getAllRunSortedByCaloriesBurned()

    fun getTotalVerageSpeed() = runDao.getTotalAverageSpeed()
    fun getTotalDistance() = runDao.getTotalDistance()
    fun getTotalCaloriesBurned() = runDao.getTotalCaloriesBurned()
    fun getTotalTimeInMilliseconds() = runDao.getTotalTimeInMillisconds()

}