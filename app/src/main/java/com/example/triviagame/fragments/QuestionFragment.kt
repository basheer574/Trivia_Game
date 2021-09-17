package com.example.triviagame.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.example.triviagame.R
import com.example.triviagame.data.domain.Results
import com.example.triviagame.data.domain.ResultsResponse
import com.example.triviagame.data.utils.Constants
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
                        val response = Gson().fromJson(jsonString, ResultsResponse::class.java)
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
                            binding?.questionText?.setText(answers?.question)
                            binding?.answerOne?.setText(answerQuestions[0])
                            binding?.answerTwo?.setText(answerQuestions[1])
                            binding?.answerThree?.setText(answerQuestions[2])
                            binding?.answerFour?.setText(answerQuestions[3])
                            binding?.currentQuestionText?.setText(index.toString())
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
        if(category=="General Knowledge")
            category = category.replace(category,"9")
        else if(category=="Entertainment: Books")
            category = category.replace(category,"10")
        else if(category=="Entertainment: Films")
            category = category.replace(category,"11")
        else if(category=="Entertainment: Music")
            category = category.replace(category,"12")
        else if(category=="Entertainment: Musicals and Theatres")
            category = category.replace(category,"13")
        else if(category=="Entertainment: Television")
            category = category.replace(category,"14")
        else if(category=="Entertainment: Video Games")
            category = category.replace(category,"15")
        else if(category=="Entertainment: Board Games")
            category = category.replace(category,"16")
        else if(category=="Science and Nature")
            category = category.replace(category,"17")
        else if(category=="Science: Computers")
            category = category.replace(category,"18")
        else if(category=="Science: Mathematics")
            category = category.replace(category,"19")
        else if(category=="Mythology")
            category = category.replace(category,"20")
        else if(category=="Sports")
            category = category.replace(category,"21")
        else if(category=="Geography")
            category = category.replace(category,"22")
        else if(category=="History")
            category = category.replace(category,"23")
        else if(category=="Politics")
            category = category.replace(category,"24")
        else if(category=="Art")
            category = category.replace(category,"25")
        else if(category=="Celebrities")
            category = category.replace(category,"26")
        else if(category=="Animals")
            category = category.replace(category,"27")
        else if(category=="Vehicles")
            category = category.replace(category,"28")
        else if(category=="Entertainment: Comics")
            category = category.replace(category,"29")
        else if(category=="Science: Gadgets")
            category = category.replace(category,"30")
        else if(category=="Entertainment: Japanese Anime and Manga")
            category = category.replace(category,"31")
        else if(category=="Entertainment: Cartoon and Animation")
            category = category.replace(category,"32")
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