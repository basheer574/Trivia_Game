package com.example.triviagame.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import com.example.triviagame.R
import com.example.triviagame.data.domain.data.Results
import com.example.triviagame.data.domain.Status
import com.example.triviagame.data.domain.data.TriviaResponse
import com.example.triviagame.data.domain.data.QuestionsAnswers
import com.example.triviagame.data.domain.repository.TriviaRepository
import com.example.triviagame.utils.*
import com.example.triviagame.databinding.QuestionFragmentBinding
import com.google.gson.Gson
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.*
import java.io.IOException

class QuestionFragment : BaseFragment<QuestionFragmentBinding>() {
    lateinit var results: Results
    lateinit var questionNumber: String
    lateinit var category: String
    lateinit var difficulty: String
    lateinit var type: String
    var points: Int = 0
    var index: Int = 0
    val resultList = mutableListOf<Results>()
    val resultsList: List<Results> get() = resultList
    val client = OkHttpClient()
    lateinit var correctAnswer: String
    var answerQuestions = mutableListOf<String>()
    override val LOG_TAG: String
        get() = "QuestionFragment_LOG"

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> QuestionFragmentBinding
        get() = QuestionFragmentBinding::inflate

    private val disposable = CompositeDisposable()

    override fun addCallbacks() {

        initArgs()
//        addConditions()

       // val url = urlBuild(questionNumber, category, difficulty, type)
  //      parser(url)
    //    nextQuestion()
      //  getAnswer()
    }

    private fun initArgs() {

    }


    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }


    fun getWeatherForCity(
        qNumber: String,
        category: String,
        difficulty: String,
        type: String
    ) {


//        TriviaRepository.getTrivia(qNumber,category,difficulty,type).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(::onTriviaResult).addTo(compositeDisposable)

//        disposable.add(
        TriviaRepository.getTrivia(qNumber, category, difficulty, type)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({},{})
//        )
    }


    private fun onTriviaResult(response: Status<TriviaResponse>) {
//        hideAllViews()
        when (response) {
            is Status.Error -> {
                Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
            }
            is Status.Loading -> {
                Toast.makeText(requireContext(), "is loading", Toast.LENGTH_LONG).show()

//                binding.progressLoading.show()
            }
            is Status.Success -> {
                log(response.data.toString())
                // bindData(response.data)
            }
        }
    }


    fun bindData(data: QuestionsAnswers) {
        answerQuestions.add(data.questions.get(index).incorrectAnswers.toString())
        if (index >= questionNumber.toInt())
            //displayGameResult()
        else {
            binding?.apply {
                questionNumber.run {
                    answerOne.text = answerQuestions[0]
                    answerTwo.text = answerQuestions[1]
                    answerThree.text = answerQuestions[2]
                    answerFour.text = answerQuestions[3]
                    currentQuestionText.text = index.toString()
                }
            }
        }
    }






    }

