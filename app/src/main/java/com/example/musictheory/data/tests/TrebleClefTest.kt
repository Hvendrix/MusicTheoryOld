package com.example.musictheory.data.tests

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.Notes
import com.example.musictheory.data.Signs
import com.example.musictheory.data.TestInterface
import com.example.musictheory.data.Tonality

object TrebleClefTest: TestInterface() {
    // Общие переменные для всех тестов
//    private var _allInterQuestions = MutableLiveData<MutableList<String>>()
//    private var _currentQuestion = MutableLiveData<String>()
//    private var _questionNumTotal = MutableLiveData<Int>()
//    private var _currentQuestNum = MutableLiveData<Int>()
//    private var _currentAnswer = MutableLiveData<String>()
//    private var _currentBtnTxt = MutableLiveData<Array<String>>()
//    private var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
//    private var _allAnswers = MutableLiveData<Array<String>>()
//    private var _specificBtnTxt = MutableLiveData<Array<Array<String>>>()


    init {
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()

    }

    override fun allQuestionsInit(){
        _allInterQuestions.value = mutableListOf(
            "Какая это нота?")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }

    override fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("twoNumPick")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!![it] }
    }

    override fun allAnswersInit(){
        _allAnswers.value = arrayOf(
            noteFind()
        )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }

    private fun noteFind(): String {

        var notes = Notes.notes
        notes.shuffle()
        Signs._signsInStave.value = mutableListOf()
        Signs._signsInStave.value?.add(
            Triple(
                Signs.noteInOrderInLines.get("Фа"),
                1f,
                "целая"
            ) as Triple<Float, Float, String>
        )
        Log.i("xxx", "${Signs.signsInStave.value?.get(0)?.first}")
        Signs._signsInStave.value = Signs._signsInStave.value
        return "${notes[0]}-бекар"
    }

//    override fun getQuestion(): String? {
//        _currentQuestion.value =
//                _currentQuestNum.value?.let {
//                    _allInterQuestions.value?.get(
//                        it
//                    )
//                }
//        return _currentQuestion.value
//    }

//    override fun nextQuestion() {
//        allQuestionsInit()
//        allBtnInit()
//        allAnswersInit()
//    }

//    override fun nextIntermediateQuestion() {
//        _currentQuestNum.value = _currentQuestNum.value?.plus(1)
//
//        if(_currentQuestNum.value == _questionNumTotal.value){
//            nextQuestion()
//        }
//    }

//    override fun getAnswer(): String? {
//        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
//        return _currentAnswer.value
//    }

//    override fun getBtnTxt(): Array<String>? {
//        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
//        if(_currentBtnTxt.value?.get(0)== "twoNumPick"){
//            _specificBtnTxt.value = arrayOf(
//                Notes.notes,
//                Notes.signs
//            )
//        }
//        return _currentBtnTxt.value
//    }

//    override fun getSpecificBtnTxt(): Array<Array<String>>? {
//        return _specificBtnTxt.value
//    }

    override fun getCurrentSignType(): MutableList<String> {
        return mutableListOf("цел")
    }

    override fun getTonality(): Tonality? {
        return Tonality.c
    }
}