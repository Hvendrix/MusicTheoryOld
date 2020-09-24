package com.example.musictheory.models

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.data.Sharps
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao
import kotlinx.coroutines.*

class TestFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application) : AndroidViewModel(application) {

    private var _btnText = MutableLiveData<Array<String>>()

    private val _quality = MutableLiveData<Float>()

    private val _question = MutableLiveData<String>()

    private val _listErrors = MutableLiveData<MutableList<String>>(mutableListOf())



    var testString = MutableLiveData<Answer?>()

    val question: LiveData<String>
    get() = _question



    val btnText: LiveData<Array<String>>
    get() = _btnText



    val listErrors: LiveData<MutableList<String>>
    get() = _listErrors



    init {
        testString.value = Answer(1, 2, "qqqq")
        _btnText.value = arrayOf("1", "2", "3")
        _question.value = "Сколько знаков в As-dur?"

    }


    fun onInitializeTestString(){
        uiScope.launch {
            testString.value = getTestStringFromDB()
        }
    }

    private suspend fun getTestStringFromDB() : Answer?{
        return withContext(Dispatchers.IO){
            var str = database.getOneAns(10)
            str
        }
    }

    val quality: LiveData<Float>
    get() = _quality


    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun onStartTracking(){
        uiScope.launch {
            val answer = Answer(10, 30, "rrrr")

            insert(answer)

        }
    }

    private suspend fun insert(answer: Answer){

        withContext(Dispatchers.IO) {
            database.insert(answer)
        }
    }


    fun onClear(){
        uiScope.launch {
            clear()
        }
    }

    suspend fun clear(){
        withContext(Dispatchers.IO){
            database.clear()
        }
    }

    fun onPrint(){
        uiScope.launch {
            printOne()
        }
    }

    fun printOne(){
        
    }



    fun onClickAnswer(){
        _listErrors.value?.add("qq")
        _question.value = _listErrors.value?.get(0)
        printErrors()
    }
    
    fun printErrors(){

    }
}