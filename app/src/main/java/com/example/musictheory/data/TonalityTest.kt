package com.example.musictheory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData


object TonalityTest : TestInterface{
    private var _currentTonality = MutableLiveData<Tonality>()
    private var _currentQuestion = MutableLiveData<String>()
    private var _currentAnswer = MutableLiveData<String>()


//    lateinit var currentTonality: Tonality
    val currentTonality: LiveData<Tonality>
    get() = _currentTonality

    var allTonality: MutableList<Tonality> = mutableListOf()

    init {
        initTonality()
        choiceTonality()
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
        _currentQuestion.value = "Сколько знаков в ${_currentTonality.value!!.name}?"
        return _currentQuestion.value
    }

    override fun nextQuestion() {
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
    }

    override fun nextIntermediateQuestion() {
        TODO("Not yet implemented")
    }

    override fun getAnswer(): String {
        TODO("Not yet implemented")
    }

    override fun getBtnTxt(): Array<String> {
        return arrayOf("1", "2", "3")
    }
}