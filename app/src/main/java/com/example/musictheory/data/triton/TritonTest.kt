package com.example.musictheory.data.triton

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.TestInterface
import com.example.musictheory.data.Tonality
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


    private var _currentTonality = MutableLiveData<Tonality>()

    var allTonality: MutableList<Tonality> = mutableListOf()


    val currentTonality : LiveData<Tonality>
        get() = _currentTonality


    init{
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
        initTonality()
        choiceTonality()
    }

    private fun initTonality(){
        for(i in Tonality.values()){
           allTonality.add(i)
        }
    }


    private fun choiceTonality(){
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
        // Дебагинг
//        _currentTonality.value = Tonality.G
    }


    private fun allQuestionsInit(){
        _allInterQuestions.value = mutableListOf(
            "Укажите да",
            "Укажите знаки в нужном порядке",
            "Постройте тоническое трезвучие")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }


    private fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
            arrayOf("table"),
            arrayOf("table")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let {_allBtnText.value!![it] }
    }


    private fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           "Да",
           "signsInTonalityStatic",
           "tonicTriad"
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }



    override fun getQuestion(): String? {
        _currentQuestion.value = "Текущая тональность:" + "${_currentTonality.value?.name}" + "\n" +
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
        choiceTonality()
    }

    override fun nextIntermediateQuestion() {
        _currentQuestNum.value = _currentQuestNum.value?.plus(1)

        if(_currentQuestNum.value == _questionNumTotal.value){
            nextQuestion()
        }
    }

    override fun getAnswer(): String? {
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
        Log.i("ttt", "current answer in triton ${_currentAnswer.value}")
        return _currentAnswer.value
    }

    override fun getBtnTxt(): Array<String>? {
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
        return _currentBtnTxt.value
    }

    override fun getSpecificBtnTxt(): Array<Array<String>>? {
        TODO("Not yet implemented")
    }

    override fun getCurrentSignType(): MutableList<String> {
        if(_currentAnswer.value=="tonicTriad") return mutableListOf("целаятрезвучие")
        return mutableListOf(_currentTonality.value?.signType!!.toLowerCase())

    }

    override fun getTonality(): Tonality? {
        return _currentTonality.value
    }
}