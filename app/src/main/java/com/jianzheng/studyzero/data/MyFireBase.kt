package com.jianzheng.studyzero.data

import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyFireBase {
    private var database = Firebase.database.reference

    fun writeResponse(
        promptTime: Long,
        submitTime: Long,
        responses: List<Int>,
        userID: String,
        responseID: Int
        ){

    }
}