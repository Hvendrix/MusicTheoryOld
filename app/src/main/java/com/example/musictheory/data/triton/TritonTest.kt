package com.example.musictheory.data.triton

import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.TestInterface
import com.example.musictheory.data.TonalityTest

object TritonTest: TestInterface {
    // Общие переменные для всех тестов
    private var _allInterQuestions = MutableLiveData<MutableList<String>>()
    private var _currentQuestion = MutableLiveData<String>()
    private var _questionNumTotal = MutableLiveData<Int>()
    private var _currentQuestNum = MutableLiveData<Int>()
    private var _currentAnswer = MutableLiveData<String>()
    private var _currentBtnTxt = MutableLiveData<Array<String>>()
    private var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
    private var _allAnswers = MutableLiveData<Array<String>>()


    init{
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }


    private fun allQuestionsInit(){
        _allInterQuestions.value = mutableListOf(
            "Нужно ли искать парралельную тональность?")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }


    private fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let {_allBtnText.value!![it] }
    }


    private fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           "Да"
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }



    override fun getQuestion(): String? {
        _currentQuestion.value = "Текущая тональность:" +
                _currentQuestNum.value?.let {
                    _allInterQuestions.value?.get(
                        it
                    )
                }
        return _currentQuestion.value
    }

    override fun nextQuestion() {
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }

    override fun nextIntermediateQuestion() {
        _currentQuestNum.value = _currentQuestNum.value?.plus(1)

        if(_currentQuestNum.value == _questionNumTotal.value){
            nextQuestion()
        }
    }

    override fun getAnswer(): String? {
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
        return _currentAnswer.value
    }

    override fun getBtnTxt(): Array<String>? {
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
        return _currentBtnTxt.value
    }
}