package com.example.triviagame.data.domain.data


import com.example.triviagame.data.domain.Results
import com.google.gson.annotations.SerializedName

data class QuestionsAnswers (

    @SerializedName("Question")
    val questions: List<Results>

        )

