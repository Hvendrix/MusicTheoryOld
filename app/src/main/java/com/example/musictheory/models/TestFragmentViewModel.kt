package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao
import kotlinx.coroutines.*

class TestFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val _quality = MutableLiveData<Float>()


    var testString = database.getOneQuality(10).value.toString()

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
            val answer = Answer(10, 30)

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
}