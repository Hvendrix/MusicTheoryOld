package com.example.musictheory.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Signs {
    var listData = listOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
    var mapSigns = mapOf<Int, String>(0 to "Фа", 1 to "До", 2 to "Соль", 3 to "Ре", 4 to "Ля", 5 to "Ми", 6 to "Си")
    var listInOrder = mutableListOf<String>()
    var listString = "asd"
    private var _TestString = MutableLiveData<String>()


    val TestString : LiveData<String>
    get() = _TestString

    fun addInList(strVal: String, pos: Int){
        listInOrder.add(strVal)
        _TestString.value = ""
        for(i in listInOrder){
            _TestString.value = _TestString.value + " " + i
        }

    }
}