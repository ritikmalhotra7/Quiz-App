package com.example.quizheist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizheist.model.Questions
import kotlinx.coroutines.launch
import retrofit2.Response

class MainviewModel(val  repository: MainRepository) : ViewModel() {

    var quizList:MutableLiveData<Resources<Questions>> = MutableLiveData()
     fun getQuizQuestions(category:Int) = viewModelScope.launch{
        quizList.postValue(Resources.Loading())
        val n = repository.questionList(category)
        quizList.postValue(handlegetQuestionResponse(n))
    }
    fun handlegetQuestionResponse(response : Response<Questions>):Resources<Questions>{
        if(response.isSuccessful){
            return Resources.Success(response.body()!!)
        }else{
            return Resources.Error(response.message())
        }
    }

}