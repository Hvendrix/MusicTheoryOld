package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musictheory.database.AnswerDatabaseDao
import java.lang.IllegalArgumentException

class TitleFragmentViewModelFactory(
    private val dataSource: AnswerDatabaseDao,
    private val application: Application
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)){
            return TitleFragmentViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}