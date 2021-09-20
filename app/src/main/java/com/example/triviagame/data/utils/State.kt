package com.example.triviagame.data.utils

sealed class State()

class Fail : State()
{
    fun requestState()
    {

    }
}

class Loading : State()
{
    fun requestState()
    {

    }
}

class Success : State()
{
    fun requestState()
    {

    }
}

