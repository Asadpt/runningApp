package com.example.study.runningApp.db

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface RunDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRun(run: Run)

    @Delete
    suspend fun deleteRun(run: Run)

    @Query("SELECT * FROM running_table ORDER BY timeStamp DESC")
    fun getAllRunSortedByDate() :LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY timesInMillis DESC")
    fun getAllRunSortedByTimeInMillisc() :LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY caloriesBurned DESC")
    fun getAllRunSortedByCaloriesBurned() :LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY avgSpeedInKMH DESC")
    fun getAllRunSortedByAverageSpeed() :LiveData<List<Run>>

    @Query("SELECT * FROM running_table ORDER BY distanceInMeters DESC")
    fun getAllRunSortedByDistance() :LiveData<List<Run>>


    @Query("SELECT SUM(timesInMillis) FROM running_table")
    fun getTotalTimeInMillisconds():LiveData<Long>

    @Query("SELECT SUM(caloriesBurned) FROM running_table")
    fun getTotalCaloriesBurned():LiveData<Int>

    @Query("SELECT SUM(distanceInMeters) FROM running_table")
    fun getTotalDistance():LiveData<Int>

    @Query("SELECT AVG(avgSpeedInKMH) FROM running_table")
    fun getTotalAverageSpeed():LiveData<Long>





}