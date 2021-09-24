package com.example.triviagame.fragments

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.triviagame.R
import com.example.triviagame.databinding.SetupFragmentBinding
import com.example.triviagame.interfaces.FragmentCommunicator

class SetupFragment: BaseFragment<SetupFragmentBinding>(){
    lateinit var selectedCategory: String
    lateinit var selectedDifficulty: String
    lateinit var fragmentCommunicator: FragmentCommunicator
    lateinit var type: String

    override val LOG_TAG: String
        get() = "SETUP_LOG"

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> SetupFragmentBinding
        get() = SetupFragmentBinding::inflate

    override fun addCallbacks() {

        fragmentCommunicator = activity as FragmentCommunicator

        binding?.playBtn?.setOnClickListener(View.OnClickListener {
            if(binding?.numberOfQuestions?.text.toString() == ""){
                Toast.makeText(requireContext(),"Please Enter Question Number.",Toast.LENGTH_SHORT).show()
            } else if (binding?.numberOfQuestions?.text.toString().toInt() > 15){
                Toast.makeText(requireContext(),"Invalid Question Number.",Toast.LENGTH_SHORT).show()
            }
            else{
                fragmentCommunicator.passData(binding?.numberOfQuestions?.text.toString(),selectedCategory,selectedDifficulty,type)
            }
        })
        init()
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
            onItemSelectedListener= object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    selectedCategory = p0?.getItemAtPosition(p2).toString()
                    Log.i(LOG_TAG,selectedCategory)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Do Nothing
                }
            }
        }
        binding?.difficultySpinner?.apply {
            adapter = difficultyArrayAdapter
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
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
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    type = p0?.getItemAtPosition(p2).toString().lowercase()
                    if(type == "True and False"){
                        type.replace(type,"boolean")
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    //Do Nothing
                }

            }
        }
    }
}