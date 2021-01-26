//package com.example.musictheory.data.tests
//
//import android.util.Log
//import androidx.lifecycle.MutableLiveData
//import com.example.musictheory.data.Notes
//import com.example.musictheory.data.Signs
//import com.example.musictheory.data.TestInterface
//import com.example.musictheory.data.Tonality
//
//object TrebleClefTest: TestInterface() {
//    init {
//        allQuestionsInit()
//        allBtnInit()
//        allAnswersInit()
//
//    }
//
//    override fun allQuestionsInit(){
//        _allInterQuestions.value = mutableListOf(
//            "Какая это нота?")
//        _questionNumTotal.value = _allInterQuestions.value!!.count()
//        _currentQuestNum.value = 0
//    }
//
//    override fun allBtnInit(){
//        _allBtnText.value = mutableListOf(
//            mutableListOf(mutableListOf("twoNumPick"))
//        )
//        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(0)!![it] }
//    }
//
//    override fun allAnswersInit(){
//        _allAnswers.value = arrayOf(
//            noteFind()
//        )
//        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
//    }
//
//    private fun noteFind(): String {
//
//        var notes = Notes.notes
//        notes.shuffle()
//        Signs._signsInStave.value = mutableListOf()
//        Signs._signsInStave.value?.add(
//            Triple(
//                Signs.noteInOrderInLines.get("Фа"),
//                1f,
//                "целая"
//            ) as Triple<Float, Float, String>
//        )
//        Log.i("xxx", "${Signs.signsInStave.value?.get(0)?.first}")
//        Signs._signsInStave.value = Signs._signsInStave.value
//        return "${notes[0]}-бекар"
//    }
//
//
//
//    override fun getCurrentSignType(): MutableList<String> {
//        return mutableListOf("цел")
//    }
//
//    override fun getTonality(): Tonality? {
//        return Tonality.c
//    }
//}