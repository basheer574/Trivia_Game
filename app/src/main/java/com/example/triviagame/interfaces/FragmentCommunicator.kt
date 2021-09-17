package com.example.triviagame.interfaces

interface FragmentCommunicator {
    fun passData(qNumber: String,category: String,difficulty: String,type: String)
}