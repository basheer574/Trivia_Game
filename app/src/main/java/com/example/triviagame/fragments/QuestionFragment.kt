package com.example.triviagame.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.triviagame.R
import com.example.triviagame.data.domain.Results
import com.example.triviagame.data.domain.TriviaResponse
import com.example.triviagame.utils.*
import com.example.triviagame.databinding.QuestionFragmentBinding
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class QuestionFragment : BaseFragment<QuestionFragmentBinding>(){
    lateinit var qNumber :String
    lateinit var category: String
    lateinit var difficulty: String
    lateinit var type: String
    var points: Int = 0
    var index: Int = 0
    val resultList = mutableListOf<Results>()
    val results : List<Results> get() = resultList
    val client =  OkHttpClient()
    lateinit var correctAnswer: String
    var answerQuestions = mutableListOf<String>()
    override val LOG_TAG: String
        get() = "QuestionFragment_LOG"

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> QuestionFragmentBinding
        get() = QuestionFragmentBinding::inflate

    override fun addCallbacks() {

        initArgs()
        addConditions()

        val url = urlBuild(qNumber,category,difficulty,type)
        parser(url)
        nextQuestion()
        getAnswer()
    }
    private fun initArgs(){
        qNumber = arguments?.getStringArray("message")?.get(0).toString()
        category = arguments?.getStringArray("message")?.get(1).toString()
        difficulty = arguments?.getStringArray("message")?.get(2).toString()
        type = arguments?.getStringArray("message")?.get(3).toString()
    }

    private fun parser(jsonUrl: String) {
        cleanJsonString(jsonUrl)
        if(index >= qNumber.toInt())
            displayGameResult()
        else{

            val request = Request.Builder().url(jsonUrl).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //Do Noting For now.
                }
                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { jsonString ->
                        val response = Gson().fromJson(jsonString, TriviaResponse::class.java)
                        response.results.forEach { addResults(it) }

                        val answers = response?.results?.toMutableList()?.get(index)
                        correctAnswer = answers?.correctAnswer.toString()
                        answerQuestions = mutableListOf(
                            answers?.correctAnswer.toString(),
                            answers?.incorrectAnswers?.get(0).toString(),
                            answers?.incorrectAnswers?.get(1).toString(),
                            answers?.incorrectAnswers?.get(2).toString(),
                        ).shuffled().toMutableList()

                        activity?.runOnUiThread {
                            binding?.questionText?.text = answers?.question
                            binding?.answerOne?.text = answerQuestions[0]
                            binding?.answerTwo?.text = answerQuestions[1]
                            binding?.answerThree?.text = answerQuestions[2]
                            binding?.answerFour?.text = answerQuestions[3]
                            binding?.currentQuestionText?.text = index.toString()
                        }
                        index++
                        Log.i("test", results.toString())
                    }
                }
            })
        }
    }
    private fun nextQuestion(){
        val url = urlBuild(qNumber,category,difficulty,type)
        binding?.nextBtn?.setOnClickListener(View.OnClickListener {
            parser(url)
        })
    }
    private fun displayGameResult(){
        val gameResult = GameResultFragment()
        val bundle= Bundle()
        bundle.putInt(Constants.POINTS,points)
        gameResult.arguments = bundle
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,gameResult)
            .addToBackStack(null)
            .commit()
    }
    private fun addResults(results: Results){
        resultList.add(results)
    }
    private fun cleanJsonString(jsonString: String) {
        jsonString.replace("response_code","")
    }
    private fun urlBuild(qNumber: String, category: String, difficulty: String,type: String): String {
        return "https://opentdb.com/api.php?amount=${qNumber}&category=${category}&difficulty=${difficulty}&type=${type}"
    }
    private fun addConditions(){
        val categoryMap = mapOf(
            "General Knowledge" to "9",
            "Entertainment: Books" to "10",
            "Entertainment: Music" to "11",
            "Entertainment: Films" to "12",
            "Entertainment: Musicals and Theatres" to "13",
            "Entertainment: Television" to "14",
            "Entertainment: Video Games" to "15",
            "Entertainment: Board Games" to "16",
            "Science and Nature" to "17",
            "Science: Computers" to "18",
            "Science: Mathematics" to "19",
            "Mythology" to "20",
            "Sports" to "21",
            "Geography" to "22",
            "History" to "23",
            "Politics" to "24",
            "Art" to "25",
            "Celebrities" to "26",
            "Animals" to "27",
            "Vehicles" to "28",
            "Entertainment: Comics" to "29",
            "Science: Gadgets" to "30",
            "Entertainment: Japanese Anime and Manga" to "31",
            "Entertainment: Cartoon and Animation" to "32",
            )
        category = categoryMap[category].toString()
    }


    private fun count(answerOption: RadioButton){
        if(answerOption.text==correctAnswer)
            points++
    }
    private fun getAnswer(){
        binding?.answerOne?.setOnClickListener(View.OnClickListener {
            count(binding!!.answerOne)
            binding?.answerTwo?.isChecked = false
            binding?.answerThree?.isChecked = false
            binding?.answerFour?.isChecked = false
        })
        binding?.answerTwo?.setOnClickListener(View.OnClickListener {
            count(binding!!.answerTwo)
            binding?.answerOne?.isChecked = false
            binding?.answerThree?.isChecked = false
            binding?.answerFour?.isChecked = false
        })
        binding?.answerThree?.setOnClickListener(View.OnClickListener {
            count(binding!!.answerThree)
            binding?.answerTwo?.isChecked = false
            binding?.answerOne?.isChecked = false
            binding?.answerFour?.isChecked = false
        })
        binding?.answerFour?.setOnClickListener(View.OnClickListener {
            count(binding!!.answerFour)
            binding?.answerTwo?.isChecked = false
            binding?.answerThree?.isChecked = false
            binding?.answerOne?.isChecked = false
        })
    }
}