package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musictheory.database.AnswerDatabaseDao
import java.lang.IllegalArgumentException

class ResultFragmentViewModelFactory (
    private val dataSource: AnswerDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ResultFragmentViewModel::class.java)){
            return ResultFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}