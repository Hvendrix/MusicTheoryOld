package com.example.musictheory.data.tests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.Notes
import com.example.musictheory.data.TestInterface
import com.example.musictheory.data.Tonality

object TritonTest: TestInterface() {

    private var _currentTonality = MutableLiveData<Tonality>()

    var allTonality: MutableList<Tonality> = mutableListOf()


    val currentTonality : LiveData<Tonality>
        get() = _currentTonality


    init{
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
        // Дебагинг
//        _currentTonality.value = Tonality.G
    }


    override fun allQuestionsInit(){
        _allInterQuestions.value = mutableListOf(
            "Укажите да",
            "Укажите знаки в нужном порядке",
            "Постройте тоническое трезвучие",
            "Назови, какая нота появляется в гармоническом виде",
            "Построй две тонические терции",
            "Построй уменьшенные квинты"
            )
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }

    override fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
            arrayOf("table"),
            arrayOf("table"),
            arrayOf("twoNumPick"),
            arrayOf("table"),
            arrayOf("table")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!![it] }
    }


    override fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           "Да",
           "signsInTonalityStatic",
           "tonicTriad",
           findHarm(),
           "twoTonicThirdInStatic",
           "twoReducedFifthInStatic",
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }


    private fun findHarm(): String {
        return _currentTonality.value?.harmNote ?: "ошибка"
    }





    override fun nextQuestion() {
        choiceTonality()
        super.nextQuestion()
    }




    //сейчас является основным методом определения статических знаков
    override fun getCurrentSignType(): MutableList<String> {
        return when(_currentAnswer.value){
            "tonicTriad" -> mutableListOf("целаятрезвучие")
            "twoTonicThirdInStatic", "twoReducedFifthInStatic" -> mutableListOf("дветерции")
            "signsInTonality", "signsInTonalityStatic" -> {
                return when(_currentTonality.value?.signType!!.toLowerCase()){
                    "бемоли" -> mutableListOf("бемолиприключе")
                    "диезы" -> mutableListOf("диезыприключе")
                    else -> mutableListOf("error")
                }
            } else -> mutableListOf(_currentTonality.value?.signType!!.toLowerCase())
        }
    }

    override fun getTonality(): Tonality? {
        return _currentTonality.value
    }

    override fun updateStaticStaveSign(
        staticSignInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>,
        signInStave: MutableLiveData<MutableList<Triple<Float, Float, String>>>
    ) {
        when(_currentAnswer.value){
            "signsInTonalityStatic", "twoTonicThirdInStatic" -> staticSignInStave.value?.let { list1 -> signInStave.value?.let(list1::addAll) }
            "twoReducedFifthInStatic" -> staticSignInStave.value = mutableListOf()
        }

    }

}