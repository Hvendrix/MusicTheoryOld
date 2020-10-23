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
    private val _navigateToResult = MutableLiveData<Int>()
    private var _btnOverFlow = MutableLiveData<Int>()
    private var _currentNumPick = MutableLiveData<Int>()
    private var _recyclerViewNeed = MutableLiveData<Boolean>()
    var testString = MutableLiveData<Answer?>()

    val question: LiveData<String>
    get() = _question



    val btnText: LiveData<Array<String>>
    get() = _btnText


    val listErrors: LiveData<MutableList<String>>
    get() = _listErrors


    val correctAnswer: LiveData<String>
    get() =  _correctAnswer


    val navigateToResult: LiveData<Int>
    get() = _navigateToResult

    val btnOverFlow: LiveData<Int>
    get() = _btnOverFlow


    val currentNumPick: LiveData<Int>
    get() = _currentNumPick

    val recyclerViewNeed: LiveData<Boolean>
        get() = _recyclerViewNeed


    init {
        _currentTest.value = TonalityTest
        _btnText.value = (_currentTest.value as TonalityTest).getBtnTxt()
        _question.value = (_currentTest.value as TonalityTest).getQuestion()
        _correctAnswer.value = (_currentTest.value as TonalityTest).getAnswer()
        _btnOverFlow.value = null
        _recyclerViewNeed.value = null

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


    fun numPickTest(){
        if(_btnText.value?.size!! > 3){
            _btnOverFlow.value = 1
        } else _btnOverFlow.value = null
    }

    fun recyclerViewTest(){
        if(_btnText.value?.get(0) == "table"){
            _recyclerViewNeed.value = true
            Log.i("ttt", "need = true")
        } else _recyclerViewNeed.value = null
    }



    fun onClickAnswer(num: Int){
        if (_recyclerViewNeed.value != null && setCurrentRecView()){
            Log.i("ttt", "in")
                _navigateToResult.value = null
                _currentTest.value?.nextIntermediateQuestion()
                _question.value = _currentTest.value?.getQuestion()
                _btnText.value = _currentTest.value?.getBtnTxt()
                _correctAnswer.value = _currentTest.value?.getAnswer()
                numPickTest()
                recyclerViewTest()
            Log.i("iii", "end")

        } else if(_correctAnswer.value != _btnText.value?.get(num)){
            _correctAnswer.value?.let {
//                _listErrors.value?.add("Твой ответ неверный: " + it)
                _listErrors.value?.add("Твой ответ неверный: ")
                Log.i("ttt", "success")
            }
            printErrors()
        } else {
            _navigateToResult.value = null
            _currentTest.value?.nextIntermediateQuestion()
//        _currentTonality.value = _currentTest.value?.getQuestion()
            _question.value = _currentTest.value?.getQuestion()
            _btnText.value = _currentTest.value?.getBtnTxt()
            _correctAnswer.value = _currentTest.value?.getAnswer()
            numPickTest()
            recyclerViewTest()
        }
    }

    fun onClickAnswer2(num: Int){
            _navigateToResult.value = null
            _currentTest.value?.nextQuestion()
//        _currentTonality.value = _currentTest.value?.getQuestion()
            _question.value = _currentTest.value?.getQuestion()
            _btnText.value = _currentTest.value?.getBtnTxt()
            _correctAnswer.value = _currentTest.value?.getAnswer()
            numPickTest()
            recyclerViewTest()
    }

    fun printErrors(){
        _question.value = _listErrors.value?.get(0) + "\n" + _currentTest.value?.getQuestion()
    }


    fun setCurrentNumPick(num: Int){
        _currentNumPick.value = num
    }

    fun setCurrentRecView():Boolean{
        Log.i("ttt", "start fun")
//        _currentRecView.value = Signs.compareByNum()
        return Signs.compareByNum()

//        _currentRecView.value = _correctAnswer.value?.toInt()?.let { Signs.compareByNum(it) }
//        return _currentRecView.value != false

    }
}