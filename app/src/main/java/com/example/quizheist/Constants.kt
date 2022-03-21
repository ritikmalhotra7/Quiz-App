package com.example.quizheist

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.quizheist.model.History
import com.example.quizheist.model.HistoryElements
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*

class Constants {
    companion object{
        val BASE_URL = "https://opentdb.com"
        var firebaseAuth = FirebaseAuth.getInstance()
        var dbReferenceUsers = FirebaseDatabase.getInstance().reference.child("user")
        var dbReferenceHistory = FirebaseDatabase.getInstance().reference.child("histories")
        var quizAmount = 10
        val quiz_name = arrayListOf<String>("General Knowledge","Music","Films","Sports","Computer Science","Comics","Technology")
        val quiz_numbers = arrayListOf<Int>(9,12,11,21,18,29,30)

        fun addDataToFirebaseHistory(o : History,uid:String,name :String,score:Int,type:String){
            dbReferenceHistory.child(uid).child(UUID.randomUUID().toString()).setValue(HistoryElements(score,type,getDate(),
                getTime()))
        }
        fun getDate():String{
            val c = Calendar.getInstance()
            val date = c.get(Calendar.DATE)
            val month = c.get(Calendar.MONTH)+1
            val year = c.get(Calendar.YEAR)


            return "$date/$month/$year"
        }
        fun getTime():String{
            val c = Calendar.getInstance()
            var hour = c.get(Calendar.HOUR_OF_DAY)
            val minute = c.get(Calendar.MINUTE)
            val seconds = c.get(Calendar.SECOND)
            var timeStamp = ""
            var am = "am"
            if(minute<10 ){
                if(hour == 0){
                    hour = 12
                }
                if(hour>12){
                    hour -= 12
                    am = "pm"
                }
                timeStamp = "$hour:0$minute $am"
            }else {
                if (hour == 0) {
                    hour = 12
                }
                if (hour > 12) {
                    hour -= 12
                    am = "pm"
                }
                timeStamp = "$hour:$minute $am"
            }
            return timeStamp;
        }

    }
}