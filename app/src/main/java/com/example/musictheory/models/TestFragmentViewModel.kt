package com.example.musictheory.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.Activities.MainActivity
import com.example.musictheory.Fragments.TestFragment
import com.example.musictheory.R
import com.example.musictheory.adapters.SignsAdapter
import com.example.musictheory.data.*
import com.example.musictheory.data.triton.TritonTest
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao
import com.example.musictheory.databinding.FragmentTestBinding
import kotlinx.android.synthetic.main.fragment_test.*
import kotlinx.android.synthetic.main.fragment_test.view.*
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
    private var _specificBtnText= MutableLiveData<Array<Array<String>>>()
    private var _currentAnswer = MutableLiveData<String>()

    private var _signInStave = MutableLiveData<MutableList<Triple<Float, Float, String>>>()

    private var _recViewBool = MutableLiveData<Boolean>()
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

    val recViewBool: LiveData<Boolean>
    get() = _recViewBool

    val specificBtnTxt: LiveData<Array<Array<String>>>
    get() = _specificBtnText


    val signInStave: LiveData<MutableList<Triple<Float, Float, String>>>
        get() = _signInStave


    init {
        _currentTest.value = TonalityTest
        _btnText.value = (_currentTest.value as TestInterface).getBtnTxt()
        _question.value = (_currentTest.value as TestInterface).getQuestion()
        _correctAnswer.value = (_currentTest.value as TestInterface).getAnswer()
        _btnOverFlow.value = null
        _recyclerViewNeed.value = null
        _recViewBool.value = null

//        _signInStave.value?.add(Triple(1f, 0.99f, "asd"))

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
        }else if (_btnText.value?.get(0) == "twoNumPick"){
            _specificBtnText.value = _currentTest.value?.getSpecificBtnTxt()
            _btnOverFlow.value = 2

        } else _btnOverFlow.value = null
    }


    fun recyclerViewTest(){
        if(_btnText.value?.get(0) == "table"){
            _recyclerViewNeed.value = true
        } else _recyclerViewNeed.value = null
    }



    fun onClickAnswer(num: Int){
        if (_recyclerViewNeed.value != null && setCurrentRecView()){
                _navigateToResult.value = null
                _currentTest.value?.nextIntermediateQuestion()
                _question.value = _currentTest.value?.getQuestion()
                _btnText.value = _currentTest.value?.getBtnTxt()
                _correctAnswer.value = _currentTest.value?.getAnswer()
                numPickTest()
                recyclerViewTest()
                Signs.clearEnabled()
            _recViewBool.value = true

        } else if(_correctAnswer.value == _btnText.value?.get(num)){
            _navigateToResult.value = null
            _currentTest.value?.nextIntermediateQuestion()
//        _currentTonality.value = _currentTest.value?.getQuestion()
            _question.value = _currentTest.value?.getQuestion()
            _btnText.value = _currentTest.value?.getBtnTxt()
            _correctAnswer.value = _currentTest.value?.getAnswer()
            numPickTest()
            recyclerViewTest()
            _recViewBool.value = null
        } else if(_correctAnswer.value == _currentAnswer.value) {
            _navigateToResult.value = null
            _currentTest.value?.nextIntermediateQuestion()
            _question.value = _currentTest.value?.getQuestion()
            _btnText.value = _currentTest.value?.getBtnTxt()
            _correctAnswer.value = _currentTest.value?.getAnswer()
            numPickTest()
            recyclerViewTest()
            _recViewBool.value = null
        } else {
            _correctAnswer.value?.let {
//                _listErrors.value?.add("Твой ответ неверный: " + it)
                _listErrors.value?.add("Твой ответ неверный: ")
//                _signInStave.value = mutableListOf(Triple(1f, 0.2f, "diez"), Triple(2f, 80f, "qwe"))
//                _signInStave.value?.add(Triple(1f, 0.55f, "diez"))
//                _signInStave.value?.add(Triple(2f, 0.90f, "bemol"))


            }
            _recViewBool.value = null
            printErrors()
            Signs.clearEnabled()
        }
    }

    fun onClickTonality(num: Int){
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

    fun setCurrentAnswer(ans: String){
        _currentAnswer.value = ans
        Log.i("ttt", "${_currentAnswer.value} ${_correctAnswer.value}")
    }

    fun setCurrentRecView():Boolean{
        return Signs.compareByNum()
    }

    fun onClickRecView(){
        _signInStave.value = Signs._signsInStave.value
    }

    fun onClearRecView(adapter: SignsAdapter){
        Signs.clearEnabled()
        adapter.notifyDataSetChanged()
    }

}