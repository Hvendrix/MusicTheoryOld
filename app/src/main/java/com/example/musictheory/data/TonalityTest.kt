package com.example.musictheory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


object TonalityTest : TestInterface{

    var allTonality: MutableList<Tonality> = mutableListOf()

    private var _allInterQuestions = MutableLiveData<MutableList<String>>()
    private var _currentTonality = MutableLiveData<Tonality>()
    private var _currentQuestion = MutableLiveData<String>()
    private var _questionNumTotal = MutableLiveData<Int>()
    private var _currentQuestNum = MutableLiveData<Int>()

    private var _currentAnswer = MutableLiveData<String>()
    private var _currentBtnTxt = MutableLiveData<Array<String>>()
    private var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
    private var _allAnswers = MutableLiveData<Array<String>>()



//    lateinit var currentTonality: Tonality
    val currentTonality: LiveData<Tonality>
    get() = _currentTonality


    init {
        initTonality()
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }

    fun allQuestionsInit(){
        _allInterQuestions.value = mutableListOf(
            "Нужно ли искать парралельную тональность?",
            "Какая тональность параллельная?",
            "Диезы или бемоли?",
        "Насколько нужно спускаться(если не нужно, то ответьте 0)?",
        "Сколько знаков?")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }

    fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
            arrayOf("2","1", "3", "q"),
            arrayOf("Диезы", "Пусто", "Бемоли"),
            arrayOf("На полу-тон", "На месте", "на три"),
            arrayOf("1", "2", "0", "3", "4", "5")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!![it] }
    }

    fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           upperTest(),
           "1",
           signTest(),
           downTest(),
           _currentTonality.value?.signCount.toString()
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value!![it] }
    }

    fun upperTest(): String{
        val tmp = _currentTonality.value?.name?.toUpperCase()
        return if(_currentTonality.value?.name != tmp) "Да" else "Нет"

    }

    fun signTest(): String{
        return _currentTonality.value?.signType!!
    }

    fun downTest(): String{
        return if(signTest() == "Диезы") "На полу-тон" else "На месте"
    }

    fun initTonality(){
        for(i in Tonality.values()){
            allTonality.add(i)
        }
    }
    fun choiceTonality(){
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
    }



    override fun getQuestion(): String? {
//        _currentQuestion.value = "Сколько знаков в ${_currentTonality.value!!.name}?"
        _currentQuestion.value = "Текущая тональность ${_currentTonality.value?.name}" + "\n" +
                _currentQuestNum.value?.let {
            _allInterQuestions.value?.get(
                it
            )
        } + "\n" + "${_questionNumTotal.value}"
        return _currentQuestion.value
    }

    override fun nextQuestion() {
        choiceTonality()
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
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value!!.get(it) }
        return _currentAnswer.value
    }

    override fun getBtnTxt(): Array<String>? {

        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!!.get(it) }
        return _currentBtnTxt.value
    }
}