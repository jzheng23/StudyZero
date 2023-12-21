package com.jianzheng.studyzero.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ResponseDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(response: Response)
}