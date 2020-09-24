package com.example.musictheory.models

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.musictheory.database.AnswerDatabaseDao

class ResultFragmentViewModel(
    val database: AnswerDatabaseDao, application: Application) : AndroidViewModel(application) {
}