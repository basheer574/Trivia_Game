package com.example.triviagame.data.domain

import com.google.gson.annotations.SerializedName

data class ResultsResponse (
    @SerializedName("results") val results: List<Results>
)