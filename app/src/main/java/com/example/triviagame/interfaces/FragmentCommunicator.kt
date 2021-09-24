package com.example.triviagame.interfaces

import com.example.triviagame.data.domain.data.TriviaResponseMain

interface FragmentCommunicator {
    fun passData(mainResponse: TriviaResponseMain)
}