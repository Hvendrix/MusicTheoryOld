package com.example.musictheory.models

import android.app.Application
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.adapters.SignsAdapter
import com.example.musictheory.data.*
import com.example.musictheory.data.tests.TonalityTest
import com.example.musictheory.data.tests.TrebleClefTest
import com.example.musictheory.data.tests.TritonTest
import com.example.musictheory.database.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.*
//test
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

class TestFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application
) : AndroidViewModel(application) {

    private var _btnText = MutableLiveData<BtnsTextList>()
    private val _question = MutableLiveData<String>()
    private val _listErrors = MutableLiveData<MutableList<String>>(mutableListOf())
    private val _correctAnswer = MutableLiveData<String>()
    private val _currentTest = MutableLiveData<TestInterface>()
    private val _navigateToResult = MutableLiveData<Int>()
    private var _currentNumPick = MutableLiveData<Int>()
    private var _currentAnswer = MutableLiveData<String>()
    private var _currentSignType = MutableLiveData<MutableList<String>>()
    private var _interfaceType = MutableLiveData<InterfaceTypes>()

    private var _signInStave = MutableLiveData<MutableList<Triple<Float, Float, String>>>()
    private var _staticSignInStave = MutableLiveData<MutableList<Triple<Float, Float, String>>>()

    var testString = MutableLiveData<Answer?>()
    private var _currentTonality = MutableLiveData<Tonality>()
    private var _parallelTonality = MutableLiveData<Tonality>()
    val adapter = SignsAdapter()


    val question: LiveData<String>
        get() = _question


    val btnText: LiveData<BtnsTextList>
        get() = _btnText


    val navigateToResult: LiveData<Int>
        get() = _navigateToResult


    val currentNumPick: LiveData<Int>
        get() = _currentNumPick


    val signInStave: LiveData<MutableList<Triple<Float, Float, String>>>
    get() = _signInStave


    val staticSignInStave: LiveData<MutableList<Triple<Float, Float, String>>>
        get() = _staticSignInStave

    val currentSignType: LiveData<MutableList<String>>
        get() = _currentSignType

    val interfaceType: LiveData<InterfaceTypes>
        get() = _interfaceType


    val currentTonality: LiveData<Tonality>
        get() = _currentTonality

    val parallelTonality: LiveData<Tonality>
        get() = _parallelTonality


    init {
        _currentTest.value = ConstsForTesting.testingTest
        _btnText.value = (_currentTest.value as TestInterface).getBtnTxt()
        _question.value = (_currentTest.value as TestInterface).getQuestion()
        _correctAnswer.value = (_currentTest.value as TestInterface).getAnswer()
        _currentSignType.value = (_currentTest.value as TestInterface).getCurrentSignType()
        _currentTonality.value = (_currentTest.value as TestInterface).getTonality()
        _interfaceType.value = setInterfaceType()
        _staticSignInStave.value = mutableListOf()
        _currentTest.value?.updateStaticStaveSign(_staticSignInStave, _signInStave)
        pushToFirebase(true)


    }

    fun updateStave(){
        _signInStave.value = _signInStave.value
    }


    fun doneNavigate() {
        _navigateToResult.value = null
    }


    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    private fun setInterfaceType() : InterfaceTypes{
        return _btnText.value?.interfaceType ?: InterfaceTypes.Buttons
    }

    fun onClickAnswer(num: Int) {
        Log.i("xxx", "${_correctAnswer.value} current ${_currentAnswer.value}")
        //нужно исправить здесь
        adapter.notifyDataSetChanged()
        if (_interfaceType.value == InterfaceTypes.TableWithAnsBtn && setCurrentRecView(_currentTonality.value!!)) {
            _currentTest.value?.updateStaticStaveSign(_staticSignInStave, _signInStave)
            standardTransit()
        } else if(_interfaceType.value ==InterfaceTypes.ButtonsWithStave && _btnText.value?.btnTextList?.get(num) == _correctAnswer.value){
            // не пашет
            _currentTest.value?.updateStaticStaveSign(_staticSignInStave, _signInStave)
            standardTransit()


        } else if(_btnText.value?.btnTextList?.get(num) == _correctAnswer.value){
            standardTransit()
        } else if (_correctAnswer.value == _currentAnswer.value) {
            standardTransit()
        } else {
            _correctAnswer.value?.let {
                _listErrors.value?.add("${_correctAnswer.value} : ${_currentAnswer.value} : ${_question.value}")
            }
            printErrors()
            Signs.clearEnabled()
            _signInStave.value = mutableListOf()
            pushToFirebase()
        }

    }

    fun standardTransit(){
        _navigateToResult.value = null
        _currentTest.value?.nextIntermediateQuestion()
        _question.value = _currentTest.value?.getQuestion()
        _btnText.value = _currentTest.value?.getBtnTxt()
        _correctAnswer.value = _currentTest.value?.getAnswer()
        Signs.clearEnabled()
        _signInStave.value = mutableListOf()

        _interfaceType.value = setInterfaceType()


        _currentSignType.value = (_currentTest.value as TestInterface).getCurrentSignType()
        _currentTonality.value = (_currentTest.value as TestInterface).getTonality()
//        _parallelTonality.value = _currentTonality.value?.parallTonRef
        _parallelTonality.value = (_currentTest.value as TestInterface).getParallelTonality()
    }



    fun pushToFirebase(test: Boolean = false){
        val cUser = userStateTesting.mAuth.currentUser

        if(cUser != null){
            val USER_KEY = "User"

            val fBDb: DatabaseReference = FirebaseDatabase.getInstance().getReference(USER_KEY)
            val id = fBDb.key ?: "0"
            val name = cUser.email.toString()
            var pass2 = _listErrors.value ?: mutableListOf()
            if(test) pass2 = mutableListOf("begin")

            val currentTest = _currentTest.value.toString()
            var curData = Date()
            val formatData = SimpleDateFormat("HH:mm:ss  dd.MM.yyyy", Locale.getDefault())
            var timeText = formatData.format(curData)
            val user = TestForFirebase2(id, name, pass2, currentTest, timeText)
            fBDb.push().setValue(user)
        }else{

        }
    }

    fun onClickTonality(num: Int) {
        _navigateToResult.value = null
        _currentTest.value?.nextQuestion()
        _question.value = _currentTest.value?.getQuestion()
        _btnText.value = _currentTest.value?.getBtnTxt()
        _correctAnswer.value = _currentTest.value?.getAnswer()
        _interfaceType.value = setInterfaceType()
    }


    private fun printErrors() {
        Toast.makeText(getApplication(), "Неправильный ответ(", Toast.LENGTH_SHORT).show()
    }


    fun setCurrentNumPick(num: Int) {
        _currentNumPick.value = num
    }

    fun setCurrentAnswer(ans: String) {
        _currentAnswer.value = ans
    }


    private fun updateStaticSignInStave(){
//        _staticSignInStave.value?.let { list1 -> _signInStave.value?.let(list1::addAll) }
    }



    //отслеживает только правильность ответа, статические знаки добавляет в самом тесте
    private fun setCurrentRecView(tonality: Tonality): Boolean {
//        _staticSignInStave.value = mutableListOf()
//        _staticSignInStave.value = Signs._signsInStave.value
//        updateStaticSignInStave()
        when (_correctAnswer.value) {
            "signsInTonality" -> return Signs.compareByNum(tonality)
            "signsInTonalityStatic" -> {
                if (Signs.compareByNum(tonality)) {
//                    _staticSignInStave.value = mutableListOf()
                    updateStaticSignInStave()
//                    _staticSignInStave.value = Signs._signsInStave.value
                }
                return Signs.compareByNum(tonality)
            }
            "tonicTriad" -> return Signs.triadTest(tonality)
            "twoTonicThird" -> return Signs.twoTonicThird(tonality)
            "twoTonicThirdInStatic" -> {
                if(Signs.twoTonicThird(tonality)){
                    updateStaticSignInStave()
//                    Signs._signsInStave.value?.forEach {
//                        _staticSignInStave.value?.add(it)
//                    }
                }
                return Signs.twoTonicThird(tonality)
            }
            "twoReducedFifthInStatic" -> {
                if(Signs.twoReducedFifth(tonality)){
                    updateStaticSignInStave()
//                    Signs.signsInStave?.value?.forEach{
//                        _staticSignInStave.value?.add(it)
//                    }
                }
                return Signs.twoReducedFifth(tonality)
            }

        }
        return false
    }

    fun onClickRecView() {
//        _signInStave.value = Signs._signsInStave.value
        _signInStave.value = _signInStave.value
    }

    fun onClearRecView(adapter: SignsAdapter) {
        Signs.clearEnabled()
        _signInStave.value = mutableListOf()
        adapter.notifyDataSetChanged()
    }


    fun addInSignInStave(text1: String, text2:String ){
        _signInStave.value = mutableListOf()

        _signInStave.value?.add(Triple(
            Signs.noteInOrderInLines.get(text1)?: 0f,
            2f,
            "целая"
        ))

        _signInStave.value?.add(Triple(
            Signs.noteInOrderInLines.get(text1)?: 0f,
            1f,
            text2
        ))
        _signInStave.value = _signInStave.value
    }


}