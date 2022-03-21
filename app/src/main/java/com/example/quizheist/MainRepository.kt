package com.example.quizheist

import com.example.quizheist.api.RetrofitInstance


class MainRepository() {

    suspend fun questionList(category:Int) = RetrofitInstance.api.getQuestions(category = category)

}