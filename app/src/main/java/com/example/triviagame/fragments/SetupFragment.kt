package com.example.triviagame.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.triviagame.R
import com.example.triviagame.data.domain.data.Results
import com.example.triviagame.data.domain.data.TriviaResponseMain
import com.example.triviagame.databinding.SetupFragmentBinding
import com.example.triviagame.interfaces.FragmentCommunicator
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class SetupFragment: BaseFragment<SetupFragmentBinding>(){
    var category = ""
    private val resultList = mutableListOf<Results>()
    val client = OkHttpClient()
    var selectedCategory = ""
    var selectedDifficulty = ""
    var type = ""

    override val LOG_TAG =  "SETUP_LOG"

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SetupFragmentBinding = SetupFragmentBinding::inflate

    override fun addCallbacks() {
       // fragmentCommunicator = activity as FragmentCommunicator
        init()
        addConditions()
        binding?.playBtn?.setOnClickListener {
            if(binding?.numberOfQuestions?.text.toString() == ""){
                Toast.makeText(requireContext(),"Please Enter Question Number.",Toast.LENGTH_SHORT).show()
            } else if (binding?.numberOfQuestions?.text.toString().toInt() > 15){
                Toast.makeText(requireContext(),"Invalid Question Number.",Toast.LENGTH_SHORT).show()
            } else {
                parser(urlBuild(binding?.numberOfQuestions.toString(),selectedCategory,selectedDifficulty,type))
            }
        }
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
        selectedCategory = categoryMap[category].toString()

    }

    private fun init() {
        val categoryArrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.category_spinner,
            R.layout.support_simple_spinner_dropdown_item
        )
        val difficultyArrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.difficulty_spinner,
            R.layout.support_simple_spinner_dropdown_item
        )
        val typeArrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.type_spinner,
            R.layout.support_simple_spinner_dropdown_item
        )
        binding?.categorySpinner?.apply {
            adapter = categoryArrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedCategory = p0?.getItemAtPosition(p2).toString()
                    Log.i(LOG_TAG, selectedCategory)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Do Nothing
                }
            }
        }
        binding?.difficultySpinner?.apply {
            adapter = difficultyArrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedDifficulty = p0?.getItemAtPosition(p2).toString().lowercase()
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }
            }
        }
        binding?.typeSpinner?.apply {
            adapter = typeArrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    type = p0?.getItemAtPosition(p2).toString().lowercase()
                    if (type == "True and False") {
                        type.replace(type, "boolean")
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Do Nothing
                }

            }
        }
    }



    fun parser(jsonUrl: String) {
        cleanJsonString(jsonUrl)

            val request = Request.Builder().url(jsonUrl).build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    //Do Noting For now.
                }

                override fun onResponse(call: Call, response: Response) {
                    response.body?.string()?.let { jsonString ->
                        val response = Gson().fromJson(jsonString, TriviaResponseMain::class.java)
                        Log.i(LOG_TAG,response.toString())
                        response.results?.forEach { addResults(it)
                        Log.i("IMHERE",it.toString())}
                    }

                }
            })

    }



    private fun addResults(results: Results) {
        resultList.add(results)
    }

    private fun cleanJsonString(jsonString: String) {
        jsonString.replace("response_code", "")
    }

    private fun urlBuild(amount: String, category: String, difficulty: String, type: String): String {
        return "https://opentdb.com/api.php?amount=5&category=9&difficulty=easy&type=multiple"
    }




}