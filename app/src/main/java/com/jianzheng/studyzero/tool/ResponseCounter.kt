package com.jianzheng.studyzero.tool

import android.content.Context
import android.util.Log

class ResponseCounter(context: Context) {
    private val sharedPreferences =
        context.getSharedPreferences("MySharedPrefs", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()
    fun responsePlusOne(tag: String) {
        val retrievedValue = sharedPreferences.getInt(tag, 0)
        editor.putInt(tag, retrievedValue + 1)
            .apply()
        Log.d("counter",tag +"is $retrievedValue")
    }

    fun check20Met(): Int {
        return sharedPreferences.getInt("20Met", 0)
    }
    fun check30Met(): Int {
        return sharedPreferences.getInt("30Met", 0)
    }
    fun checkComplete(): Boolean {
        return false
    }
}