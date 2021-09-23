package com.example.triviagame.data.domain.data


import com.google.gson.annotations.SerializedName

data class Questions (

    @SerializedName("answerQuestionsOne")
    var answerQuestionsOne: String,
    @SerializedName("answerQuestionsTwo")
    var answerQuestionsTwo: String,
    @SerializedName("answerQuestionsThree")
    var answerQuestionsThree: String,
    @SerializedName("answerQuestionsFour")
    var answerQuestionsFour: String,
    @SerializedName("correctAnswer")
    var correctAnswer: Int,

        )

