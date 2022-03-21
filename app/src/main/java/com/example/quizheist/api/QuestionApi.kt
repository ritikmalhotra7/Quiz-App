

import com.example.quizheist.Constants.Companion.quizAmount
import com.example.quizheist.model.Questions
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface QuestionApi {

    @GET("/api.php")
    suspend fun getQuestions(
        @Query("amount")
        amount : Int = quizAmount,
        @Query("category")
        category :Int = 9,
        @Query("difficulty")
        difficulty:String = "easy",
        @Query("type")
        type :String = "multiple"

    ): Response<Questions>


}