package com.example.musictheory.data.tests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.*

object TritonTest: TestInterface() {

//    var allTonality: MutableList<Tonality> = mutableListOf()


    val currentTonality : LiveData<Tonality>
        get() = _currentTonality


    init{
        initTonality()
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()

    }

//    private fun initTonality(){
//        for(i in Tonality.values()){
//           allTonality.add(i)
//        }
//    }
//
//
//    private fun choiceTonality(){
//        allTonality.shuffle()
//        _currentTonality.value = allTonality[0]
//        // Дебагинг
////        _currentTonality.value = Tonality.G
//    }


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
            BtnsTextList(mutableListOf("Да", "Нет")),
            BtnsTextList((Signs.signList.value!!), interfaceType = InterfaceTypes.TableWithAnsBtn),
            BtnsTextList((Signs.signList.value!!), interfaceType = InterfaceTypes.TableWithAnsBtn),
            BtnsTextList(Notes.notes.toMutableList(),
                interfaceType = InterfaceTypes.TwoNumPick,
                btnTextList2 = Notes.signs.toMutableList()
            ),
            BtnsTextList((Signs.signList.value!!), interfaceType = InterfaceTypes.TableWithAnsBtn),
            BtnsTextList((Signs.signList.value!!), interfaceType = InterfaceTypes.TableWithAnsBtn)
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
        Log.i("xxx", "harmNote is ${_currentTonality.value?.harmNote ?: "ошибка"}")
        return _currentTonality.value?.harmNote ?: "ошибка"
    }





    override fun nextQuestion() {
        choiceTonality()
        super.nextQuestion()
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