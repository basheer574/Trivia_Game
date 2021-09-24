package com.example.triviagame.data.domain.data


import com.google.gson.annotations.SerializedName

data class QuestionsAnswers (

    @SerializedName("Question")
    val questions: List<Results>

        )

