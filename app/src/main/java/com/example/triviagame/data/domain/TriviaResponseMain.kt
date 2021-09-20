package com.example.triviagame.data.domain


import com.google.gson.annotations.SerializedName

data class TriviaResponseMain(
    @SerializedName("response_code")
    val responseCode: Int?,
    @SerializedName("results")
    val results: List<Result>?
)