package com.example.quizheist.model

import com.example.quizheist.Constants.Companion.getDate
import com.example.quizheist.Constants.Companion.getTime
import java.util.*

data class HistoryElements(var score:Int = 0, var type:String = "", var date:String = getDate(),var time:String= getTime())