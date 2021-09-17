package com.example.triviagame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.triviagame.databinding.ActivityMainBinding
import com.example.triviagame.fragments.QuestionFragment
import com.example.triviagame.fragments.SetupFragment
import com.example.triviagame.interfaces.FragmentCommunicator

class MainActivity : AppCompatActivity() , FragmentCommunicator{
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)
        addFragment(SetupFragment())
    }

    private fun addFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,fragment).commit()
    }
    companion object{
        const val LOG_TAG = "HOME_ACTIVITY_LOG"
    }

    override fun passData(qNumber: String, category: String, difficulty: String,type: String) {
        val bundle = Bundle()
        bundle.putStringArray("message", arrayOf(qNumber,category,difficulty,type))
        val transaction = this.supportFragmentManager.beginTransaction()

        val questionFragment = QuestionFragment()
        questionFragment.arguments = bundle

        transaction.replace(R.id.fragment_container,questionFragment,"message")
        transaction.commit()
    }
}