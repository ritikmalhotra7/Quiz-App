package com.example.quizheist.model

import android.graphics.drawable.Drawable
import android.media.Image
import java.io.Serializable

data class QuizElements(
    var name:String,
    var number: Int,
    var desc:String,
    var image: Int

    ):Serializable{
    override fun toString(): String {
        return "QuizElements(name='$name', number=$number, desc='$desc', image=$image)"
    }
}