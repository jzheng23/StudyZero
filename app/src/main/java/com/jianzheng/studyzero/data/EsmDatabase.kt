package com.jianzheng.studyzero.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Response::class], version = 1, exportSchema = false)
abstract class EsmDatabase : RoomDatabase() {
    abstract fun responseDao(): ResponseDao

    companion object{
        @Volatile
        private var Instance: EsmDatabase? = null

        fun getDatabase(context: Context): EsmDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, EsmDatabase::class.java, "response_database")
                    .build().also { Instance = it }
            }
        }
    }
}