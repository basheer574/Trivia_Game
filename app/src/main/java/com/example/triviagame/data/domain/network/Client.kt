package com.example.triviagame.data.domain.network

import com.example.triviagame.data.domain.Status
import com.example.triviagame.data.domain.TriviaResponse
import com.example.triviagame.data.domain.TriviaResponseMain
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

object Client {
    private val okHttpClient = OkHttpClient()
    private val baseUrl = "https://opentdb.com/api.php?"
    private val gson = Gson()

    fun getTrivia(qNumber: String, category: String, difficulty: String,type: String): Status<TriviaResponseMain> {
        val request = Request.Builder().url("${baseUrl}amount=${qNumber}&category=${category}&difficulty=${difficulty}&type=${type}").build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful){
            val weatherResponse = gson.fromJson(response.body!!.string(), TriviaResponseMain::class.java)
            Status.Success(weatherResponse)
        } else {
            Status.Error(response.message)
        }
    }
}