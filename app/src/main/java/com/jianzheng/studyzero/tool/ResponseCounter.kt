package com.jianzheng.studyzero.tool

import android.content.Context
import android.util.Log

class ResponseCounter(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    private val lengthOfTriggerList = 3 //13 for real, 3 for testing
    fun responsePlusOne(tag: String) {
        val retrievedValue = sharedPreferences.getInt(tag, 0)
        editor.putInt(tag, retrievedValue + 1)
            .apply()
        Log.d("counter", tag + "is ${retrievedValue + 1}")
    }

    fun check20Met(): Boolean {
        return sharedPreferences.getInt("20Met", 0) < 1
    }

    fun check30Met(): Boolean {
        return sharedPreferences.getInt("30Met", 0) < 2
    }

    fun checkRandomMet(): Boolean {
        return sharedPreferences.getInt("randomMet", 0) < lengthOfTriggerList
    }

    fun checkComplete(): Boolean {
        return (checkRandomMet() and check20Met() and check30Met())
    }

    fun resetCounter() {
        editor.putInt("20Met", 0)
            .putInt("30Met", 0)
            .putInt("randomMet", 0)
            .apply()
    }
}