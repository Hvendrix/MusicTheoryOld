package com.example.musictheory.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel: ViewModel() {
    private val _name = MutableLiveData<String>().apply{ value = "asd" }
    private var score = MutableLiveData<Int>()

    init{
        score.value = 0
    }

    var name : LiveData<String> = _name




    fun onKik(){
        score.value = 102
        _name.value = "new"
    }

}