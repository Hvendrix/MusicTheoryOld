package com.example.musictheory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.stream.IntStream.range

object Signs {
    var listData = listOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
    var mapSigns = mapOf<Int, String>(0 to "Фа", 1 to "До", 2 to "Соль", 3 to "Ре", 4 to "Ля", 5 to "Ми", 6 to "Си")
    var listInOrder = mutableListOf<String>()
    var listString = "asd"
    var rightSharpOrder = listOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")

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

    fun compareByNum(): Boolean{
        Log.i("ttt", "start compare fun")
        var test = true
        var num = 0
        TonalityTest.currentTonality.value?.signCount?.let{
            num = it
        }
        Log.i("ttt", "$num")
        for(i in 0..num-1){
            if(listInOrder[i] != rightSharpOrder[i]){
                test = false
                Log.i("ttt", "incorrect order")
            }
        }
        Log.i("ttt", "end of compare")
        return test
    }
}