package com.example.triviagame.data.domain

import com.google.gson.annotations.SerializedName

data class TriviaResponse (
    @SerializedName("results") val results: List<Results>
)