package ru.vsls.surfquiz.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.vsls.surfquiz.data.remote.model.QuizResponse

interface QuizApiService {
    @GET("api.php")
    suspend fun getQuiz(
        @Query("amount") amount: Int = 5,
        @Query("difficulty") difficulty: String = "medium",
        @Query("type") type: String = "multiple",
    ): QuizResponse
}
