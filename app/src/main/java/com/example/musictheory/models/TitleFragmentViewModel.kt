package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabaseDao

import kotlinx.coroutines.*
class TitleFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application
): AndroidViewModel(application) {
    private val _test = MutableLiveData<Int>()
    private val _quality = MutableLiveData<Int>()



    var test : LiveData<Int> = _test
    var quality: LiveData<Int> = _quality

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun onStartTracking(){
        uiScope.launch {
            val answer = Answer(3, 10)

            insert(answer)

        }
    }

    private suspend fun insert(answer: Answer){

        withContext(Dispatchers.IO) {
            database.insert(answer)
        }
    }

    init {
        _test.value = 10000000

    }
}