package com.example.triviagame.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.triviagame.R
import com.example.triviagame.utils.Constants
import com.example.triviagame.databinding.GameResultFragmentBinding

class GameResultFragment : BaseFragment<GameResultFragmentBinding>() {
    override val LOG_TAG: String
        get() = "GAME_RESULT_TAG"
    var points = 0
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> GameResultFragmentBinding
        get() = GameResultFragmentBinding::inflate

    override fun addCallbacks() {
        points = requireArguments().getInt(Constants.POINTS)
        requireActivity().runOnUiThread {
            displayResult()
        }
        binding?.playAgainBtn?.setOnClickListener(View.OnClickListener {
            playAgain()
        })
    }
    private fun playAgain(){
        val setupFragment = SetupFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,setupFragment)
            .addToBackStack(null)
            .commit()
    }
    private fun displayResult(){
        binding?.gameResultText?.text = "Total Points: Points ${points}"
    }
}