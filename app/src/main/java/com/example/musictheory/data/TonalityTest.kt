package com.example.musictheory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.*


object TonalityTest : TestInterface{


    // Общие переменные для всех тестов
    private var _allInterQuestions = MutableLiveData<MutableList<String>>()
    private var _currentQuestion = MutableLiveData<String>()
    private var _questionNumTotal = MutableLiveData<Int>()
    private var _currentQuestNum = MutableLiveData<Int>()
    private var _currentAnswer = MutableLiveData<String>()
    private var _currentBtnTxt = MutableLiveData<Array<String>>()
    private var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
    private var _allAnswers = MutableLiveData<Array<String>>()

    // Переменные для данного теста
    private var _currentTonality = MutableLiveData<Tonality>()
    private var _upperTestBool = MutableLiveData<Boolean>()
    private var _mollDur = MutableLiveData<String>()
    private var _parallelTonality = MutableLiveData<String>()


    var allTonality: MutableList<Tonality> = mutableListOf()



    init {
        initTonality()
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }


    private fun initTonality(){
        for(i in Tonality.values()){
            allTonality.add(i)
        }
    }


    private fun choiceTonality(){
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
        _parallelTonality.value = ""
    }


    private fun allQuestionsInit(){
        upperTest()
        _allInterQuestions.value = mutableListOf(
            "Нужно ли искать парралельную тональность?",
            "На какой ступени строится пареллельный мажор",
            "Параллельная тональность для ${_currentTonality.value?.name} - ${_mollDur.value}:",
            "В искомой тональности будут диезы или бемоли?",
            "Какую ищем ступень, чтобы узнать знаки?",
            "Сколько знаков? (будет 2 вопроса вместо этого)")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }

    private fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
            arrayOf("I", "II", "III", "IV", "V", "VI", "VII"),
            btnTonalityTextShuff(),
            arrayOf("Диезы", "Пусто", "Бемоли"),
            arrayOf("I", "II", "III", "IV", "V", "VI", "VII"),
            arrayOf("0", "1", "2", "3", "4", "5", "6", "7")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!![it] }
    }

    private fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           upperTest(),
           "III",
           findParallTonality(),
           signTest(),
           downTest(),
           _currentTonality.value?.signCount.toString()
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }


    private fun btnTonalityTextShuff(): Array<String>{
        val tmpArray = arrayOf(findParallTonality(), allTonality[1].name, allTonality[2].name, allTonality[3].name)
        tmpArray.shuffle()
        return tmpArray
    }



    private fun upperTest(): String{
        val tmp = _currentTonality.value?.name?.get(0)?.toUpperCase()
        _upperTestBool.value = _currentTonality.value?.name?.get(0) == tmp
        if (_upperTestBool.value!!){
            _mollDur.value = "dur"
        } else _mollDur.value = "moll"
        return if(_currentTonality.value?.name?.get(0) != tmp) "Да" else "Нет"

    }

    private fun findParallTonality(): String {

        return _currentTonality.value?.parallTon.toString()
    }

    private fun signTest(): String{
        return _currentTonality.value?.signType!!
    }

    private fun downTest(): String{
        return if(signTest() == "Диезы") "VII" else "I"
    }





    override fun getQuestion(): String? {
//        _currentQuestion.value = "Сколько знаков в ${_currentTonality.value!!.name}?"
        _currentQuestion.value = "Текущая тональность: ${_currentTonality.value?.name}" + " - ${_mollDur.value}" + "\n"+
                "${_parallelTonality.value}" + "\n\n" +
                _currentQuestNum.value?.let {
            _allInterQuestions.value?.get(
                it
            )
        }
        return _currentQuestion.value
    }

    override fun nextQuestion() {
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }

    override fun nextIntermediateQuestion() {
        if(_upperTestBool.value!! && upperTest()=="Нет"){
            _upperTestBool.value = false
            _currentQuestNum.value = _currentQuestNum.value?.plus(2)
        }
        _currentQuestNum.value = _currentQuestNum.value?.plus(1)



        if(_currentQuestNum.value == _questionNumTotal.value){
            nextQuestion()
        } else if(_currentQuestNum.value == 3){
            _parallelTonality.value = "Параллельный мажор: ${_currentTonality.value?.parallTon.toString()} - dur"
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