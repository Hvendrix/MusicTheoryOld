package com.example.musictheory.data

import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.tests.TritonTest

abstract class TestInterface {


    // Общие переменные для всех тестов
    var _allInterQuestions = MutableLiveData<MutableList<String>>()
    var _currentQuestion = MutableLiveData<String>()
    var _questionNumTotal = MutableLiveData<Int>()
    var _currentQuestNum = MutableLiveData<Int>()
    var _currentAnswer = MutableLiveData<String>()
    var _currentBtnTxt = MutableLiveData<Array<String>>()
    var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
    var _allAnswers = MutableLiveData<Array<String>>()
    var _specificBtnTxt = MutableLiveData<Array<Array<String>>>()


    abstract fun allQuestionsInit()

    abstract fun allBtnInit()

    abstract fun allAnswersInit()

    open fun getQuestion() : String?{
        _currentQuestion.value =
            _currentQuestNum.value?.let {
                _allInterQuestions.value?.get(
                    it
                )
            }
        return _currentQuestion.value
    }

    open fun nextQuestion(){
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }

    open fun nextIntermediateQuestion(){
        _currentQuestNum.value = _currentQuestNum.value?.plus(1)

        if(_currentQuestNum.value == _questionNumTotal.value){
            nextQuestion()
        }
    }

    open fun getAnswer(): String?{
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
        return _currentAnswer.value
    }

    open fun getBtnTxt(): Array<String>?{
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
        if(_currentBtnTxt.value?.get(0)== "twoNumPick"){
            _specificBtnTxt.value = arrayOf(
                Notes.notes,
                Notes.signs
            )
        }
        return _currentBtnTxt.value

    }

    open fun getSpecificBtnTxt(): Array<Array<String>>?{
        return _specificBtnTxt.value
    }

    abstract fun getCurrentSignType(): MutableList<String>

    abstract fun getTonality(): Tonality?


    open fun updateStaticStaveSign(
        staticSignInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>,
        signInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>
    ) {

    }


}