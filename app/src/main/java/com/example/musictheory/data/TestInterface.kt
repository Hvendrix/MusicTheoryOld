package com.example.musictheory.data

import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.tests.TonalityTest
import com.example.musictheory.data.tests.TritonTest

abstract class TestInterface {


    // Общие переменные для всех тестов
    var _allInterQuestions = MutableLiveData<MutableList<String>>()
    var _currentQuestion = MutableLiveData<String>()
    var _questionNumTotal = MutableLiveData<Int>()
    var _currentQuestNum = MutableLiveData<Int>()
    var _currentAnswer = MutableLiveData<String>()
    var _currentBtnTxt = MutableLiveData<BtnsTextList>()
    var _allBtnText = MutableLiveData<MutableList<BtnsTextList>>()
    var _allAnswers = MutableLiveData<Array<String>>()
    var _currentTonality = MutableLiveData<Tonality>()
    var allTonality: MutableList<Tonality> = mutableListOf()




    open fun initTonality(){
        for(i in Tonality.values()){
            allTonality.add(i)
        }
    }


    open fun choiceTonality(){
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
        // Дебагинг
//        if(ConstsForTesting.testingTonality==1){
//            _currentTonality.value = ConstsForTesting.testingChoiceTonality
//            _currentTonality.value = Tonality.G
//        }

    }


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

    open fun getBtnTxt(): BtnsTextList?{
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
//        if(_currentBtnTxt.value?.get(0)== "twoNumPick"){
//            _specificBtnTxt.value = arrayOf(
//                Notes.notes,
//                Notes.signs
//            )
//        }
        return _currentBtnTxt.value

    }

    //сейчас является основным методом определения статических знаков
    open fun getCurrentSignType(): MutableList<String>{
        return when(_currentAnswer.value){
            "tonicTriad" -> mutableListOf("целаятрезвучие")
            "twoTonicThirdInStatic" -> mutableListOf("двадвадва")
            "twoReducedFifthInStatic" -> mutableListOf("одиндвадва")
            "signsInTonality", "signsInTonalityStatic" -> {
                return when(_currentTonality.value?.signType!!.toLowerCase()){
                    "бемоли" -> mutableListOf("бемолиприключе")
                    "диезы" -> mutableListOf("диезыприключе")
                    else -> mutableListOf("error")
                }
            } else -> mutableListOf(_currentTonality.value?.signType!!.toLowerCase())
        }
    }

//    open fun getTonality() = TonalityTest.currentTonality.value
    abstract fun getTonality() : Tonality?


    open fun updateStaticStaveSign(
        staticSignInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>,
        signInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>
    ) {

    }


}