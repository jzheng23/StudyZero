package com.jianzheng.studyzero.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ResponseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(response: Response)

    @Query("SELECT COUNT(id) from responses")
    fun getCount(): Flow<Int>

    @Query("SELECT COUNT(id) from responses WHERE submittingTime > :todayInMillis")
    fun getCountToday(todayInMillis: Long): Flow<Int>

}