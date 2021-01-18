package com.example.musictheory.data.tests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.Notes
import com.example.musictheory.data.TestInterface
import com.example.musictheory.data.Tonality


object TonalityTest : TestInterface {



    // Общие переменные для всех тестов
    private var _allInterQuestions = MutableLiveData<MutableList<String>>()
    private var _currentQuestion = MutableLiveData<String>()
    private var _questionNumTotal = MutableLiveData<Int>()
    private var _currentQuestNum = MutableLiveData<Int>()
    private var _currentAnswer = MutableLiveData<String>()
    private var _currentBtnTxt = MutableLiveData<Array<String>>()
    private var _allBtnText = MutableLiveData<MutableList<Array<String>>>()
    private var _allAnswers = MutableLiveData<Array<String>>()
    private var _specificBtnTxt = MutableLiveData<Array<Array<String>>>()

    // Переменные для данного теста
    private var _currentTonality = MutableLiveData<Tonality>()
    private var _upperTestBool = MutableLiveData<Boolean>()
    private var _mollDur = MutableLiveData<String>()
    private var _parallelTonality = MutableLiveData<String>()



    var allTonality: MutableList<Tonality> = mutableListOf()


    val currentTonality : LiveData<Tonality>
    get() = _currentTonality


    val currentQuestionNum: LiveData<Int>
    get() = _currentQuestNum



    init {
        initTonality()
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
//        _specificBtnTxt.value = arrayOf(arrayOf("ok"), arrayOf("okk"))
    }


    private fun initTonality(){
        for(i in Tonality.values()){
            allTonality.add(i)
        }
    }


    private fun choiceTonality(){
        allTonality.shuffle()
        _currentTonality.value = allTonality[0]
        _parallelTonality.value = ""
        // Дебагинг
        _currentTonality.value = Tonality.G
    }



    private fun allQuestionsInit(){
        upperTest()
        _allInterQuestions.value = mutableListOf(
            "Нужно ли искать параллельную тональность?",
            "На какой ступени строится пареллельный мажор",
            "Параллельная тональность для ${_currentTonality.value?.name} - ${_mollDur.value}:",
            "В данной тональности будут диезы или бемоли?",
            "Какую ищем ступень, чтобы узнать знаки?",
            "Какая это нота?",
            "Выбери знаки в том порядке, в котором они будут стоять при ключе")
        _questionNumTotal.value = _allInterQuestions.value!!.count()
        _currentQuestNum.value = 0
    }

    private fun allBtnInit(){
        _allBtnText.value = mutableListOf(
            arrayOf("Да", "Нет", "Не знаю"),
            arrayOf("I", "II", "III", "IV", "V", "VI", "VII"),
            btnTonalityTextShuff(),
            arrayOf("Диезы", "Бемоли", "Не знаю"),
            arrayOf("I", "II", "III", "IV", "V", "VI", "VII"),
            arrayOf("twoNumPick"),
            arrayOf("table")
        )
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value!![it] }
    }

    private fun allAnswersInit(){
       _allAnswers.value = arrayOf(
           upperTest(),
           "III",
           findParallTonality(),
           signTest(),
           downTest(),
           noteFind(),
           "signsInTonality"
       )
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
    }


    private fun btnTonalityTextShuff(): Array<String>{
        val tmpArray = arrayOf(findParallTonality(), allTonality[1].name, allTonality[2].name, allTonality[3].name)
        tmpArray.shuffle()
        return tmpArray
    }



    private fun upperTest(): String{
        val tmp = _currentTonality.value?.name?.get(0)?.toUpperCase()
        _upperTestBool.value = _currentTonality.value?.name?.get(0) == tmp
        if (_upperTestBool.value!!){
            _mollDur.value = "dur"
        } else _mollDur.value = "moll"
        return if(_currentTonality.value?.name?.get(0) != tmp) "Да" else "Нет"

    }

    private fun findParallTonality(): String {
        return _currentTonality.value?.parallTon.toString()
    }

    private fun signTest(): String{
        return _currentTonality.value?.signType!!
    }

    private fun downTest(): String{
        return if(signTest() == "Диезы") "VII" else "I"
    }

    private fun noteFind(): String{
        if(_upperTestBool.value != true){
            val ton = Tonality.valueOf(
                _currentTonality.value?.parallTon!!)
            val tmp1 = ton.rusName.let {
                findSign(
                    it
                )
            }
            val tmp2 = ton.rusName.let {
                findNote(
                    it,
                    tmp1
                )
            }
            Log.i("ttt", " up bool = false and note In Find = $tmp2 + $tmp1")
            return "$tmp2-$tmp1"
        } else{
            val tmp1 = _currentTonality.value?.rusName?.let {
                findSign(
                    it
                )
            }
            val tmp2 = _currentTonality.value?.rusName?.let {
                findNote(
                    it,
                    tmp1!!
                )
            }
            Log.i("ttt", "note In Find = $tmp2-$tmp1")
            return "$tmp2-$tmp1"
        }
    }


    private fun findNote(string: String, sign: String): String{
        Notes.notes.forEachIndexed { i, el ->
            if(string.contains(el)){
                return if(sign=="бемоль"){
                    Notes.notes[i]
                } else Notes.notes[i-1]
            }
        }
        return "not ok"
    }
    private fun findSign(string: String): String{
        Notes.signs.forEachIndexed { i, el ->
            if(string.contains(el))return el
        }
        return "диез"
    }




    override fun getQuestion(): String? {
//        _currentQuestion.value = "Текущая тональность: ${_currentTonality.value?.name}" + " - ${_mollDur.value}" + "\n(${_currentTonality.value?.rusName})"+ "\n"+
//                "${_parallelTonality.value}" +
//                _currentQuestNum.value?.let {
//            _allInterQuestions.value?.get(
//                it
//            )
//        }

        _currentQuestion.value =
                _currentQuestNum.value?.let {
                    _allInterQuestions.value?.get(
                        it
                    )
                }
        return _currentQuestion.value
    }

    override fun nextQuestion() {
        choiceTonality()
        allQuestionsInit()
        allBtnInit()
        allAnswersInit()
    }

    override fun nextIntermediateQuestion() {

        if(_currentQuestNum.value == 2 && upperTest() =="Да"){
            _upperTestBool.value = false
            _parallelTonality.value = "Параллельный мажор: ${_currentTonality.value?.parallTon.toString()} - dur"
            _currentTonality.value = _currentTonality.value?.parallTonRef
        } else if(_upperTestBool.value!! && upperTest() =="Нет"){
            _upperTestBool.value = false
            _currentQuestNum.value = _currentQuestNum.value?.plus(2)
            //Дебагинг
//            _currentQuestNum.value = _currentQuestNum.value?.plus(3)
        }
        _currentQuestNum.value = _currentQuestNum.value?.plus(1)





        if(_currentQuestNum.value == _questionNumTotal.value){
            nextQuestion()
        }
    }

    override fun getAnswer(): String? {
        _currentAnswer.value = _currentQuestNum.value?.let { _allAnswers.value?.get(it) }
        return _currentAnswer.value
    }

    override fun getBtnTxt(): Array<String>? {
        _currentBtnTxt.value = _currentQuestNum.value?.let { _allBtnText.value?.get(it) }
        if(_currentBtnTxt.value?.get(0)== "twoNumPick"){
            _specificBtnTxt.value = arrayOf(
                Notes.notes,
                Notes.signs
            )
        }
        return _currentBtnTxt.value

    }

    override fun getSpecificBtnTxt(): Array<Array<String>>? {
        return _specificBtnTxt.value
    }

    override fun getCurrentSignType(): MutableList<String> {
        return when(_currentAnswer.value){
            "tonicTriad" -> mutableListOf("целаятрезвучие")
            "twoTonicThirdInStatic", "twoReducedFifthInStatic" -> mutableListOf("дветерции")
            "signsInTonality" -> {
                return when(_currentTonality.value?.signType!!.toLowerCase()){
                    "бемоли" -> mutableListOf("бемолиприключе")
                    "диезы" -> mutableListOf("диезыприключе")
                    else -> mutableListOf("error")
                }
            } else -> mutableListOf(_currentTonality.value?.signType!!.toLowerCase())
        }
    }

    override fun getTonality(): Tonality? {
        return currentTonality.value
    }
}