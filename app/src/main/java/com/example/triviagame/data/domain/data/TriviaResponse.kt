package com.example.triviagame.data.domain.data

import com.example.triviagame.data.domain.data.Results
import com.google.gson.annotations.SerializedName

data class TriviaResponse (
    @SerializedName("results") val results: List<Results>
)