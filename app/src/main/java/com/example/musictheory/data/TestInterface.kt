package com.example.musictheory.data

interface TestInterface {
    fun getQuestion() : String?

    fun nextQuestion()

    fun nextIntermediateQuestion()

    fun getAnswer(): String?

    fun getBtnTxt(): Array<String>?
}