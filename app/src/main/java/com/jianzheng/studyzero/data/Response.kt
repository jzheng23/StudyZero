package com.jianzheng.studyzero.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "responses")
data class Response(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val delay: Int,
    val answer1: Int,
    val answer2: Int,
    val startingTime: Long,
    val submittingTime: Long
)
