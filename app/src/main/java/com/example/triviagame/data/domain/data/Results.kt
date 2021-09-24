package com.example.triviagame.data.domain.data

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("category")val category: String?,
    @SerializedName("type")val type: String?,
    @SerializedName("difficulty")val difficulty: String?,
    @SerializedName("question")val question: String?,
    @SerializedName("correct_answer")val correctAnswer: String?,
    @SerializedName("incorrect_answers")val incorrectAnswers: ArrayList<String>?
)