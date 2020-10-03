package com.example.musictheory.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.*
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao
import kotlinx.coroutines.*

class TestFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var _btnText = MutableLiveData<Array<String>>()

    private val _question = MutableLiveData<String>()

    private val _listErrors = MutableLiveData<MutableList<String>>(mutableListOf())

    private val _correctAnswer = MutableLiveData<String>()

    private val _currentTest = MutableLiveData<TestInterface>()



    var testString = MutableLiveData<Answer?>()

    val question: LiveData<String>
    get() = _question



    val btnText: LiveData<Array<String>>
    get() = _btnText



    val listErrors: LiveData<MutableList<String>>
    get() = _listErrors


    val correctAnswer: LiveData<String>
    get() =  _correctAnswer

    private val _navigateToResult = MutableLiveData<Int>()

    val navigateToResult: LiveData<Int>
    get() = _navigateToResult


    init {
        _currentTest.value = TonalityTest
        _btnText.value = (_currentTest.value as TonalityTest).getBtnTxt()
        _question.value = (_currentTest.value as TonalityTest).getQuestion()
//        _correctAnswer.value = _currentTonality.value!!.signCount.toString()

    }


    fun doneNavigate(){
        _navigateToResult.value = null
    }


    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)



    fun onClickAnswer(num: Int){
        _navigateToResult.value = null
        _currentTest.value?.nextQuestion()
//        _currentTonality.value = _currentTest.value?.getQuestion()
        _question.value = _currentTest.value?.getQuestion()
        _btnText.value = _currentTest.value?.getBtnTxt()


        if(_correctAnswer.value != _btnText.value?.get(num)){
//            _listErrors.value?.add(Test.Fis_dur.s)
//            printErrors()



        }
    }

    fun nextQuestionCheck(){

    }
    fun printErrors(){
        _question.value = _listErrors.value?.get(0)
    }
}