package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao
import com.example.musictheory.formatAnswers
import kotlinx.coroutines.*

class ResultFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application) : AndroidViewModel(application) {

    var testString = MutableLiveData<Answer?>()

    private val viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)


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

    private val answers = database.getAllAnswers()

    val answersString = Transformations.map(answers){ answers ->
        formatAnswers(answers, application.resources)

    }
}