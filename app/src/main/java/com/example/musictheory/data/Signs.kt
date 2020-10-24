package com.example.musictheory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.stream.IntStream.range

object Signs {
    private var _signList = MutableLiveData<MutableList<String>>()
    var listData = mutableListOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
    var listEnabled = mutableListOf(1, 1, 1, 1, 1, 1, 1)
    private var _listDataEnabled = MutableLiveData<MutableList<Int>>()
    var mapSigns = mapOf<String, Int>("Фа" to 1, "До" to 1, "Соль" to 1,  "Ре" to 1, "Ля" to 1,  "Ми" to 1,  "Си" to 1)
    var listInOrder = mutableListOf<String>()
    var rightSharpOrder = listOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
    var rightFlatOrder = listOf("Си", "Ми", "Ля", "Ре", "Соль", "До", "Фа")


    private var _TestString = MutableLiveData<String>()


    val TestString : LiveData<String>
    get() = _TestString

    val signList : LiveData<MutableList<String>>
    get() = _signList

    val listDataEnabled : LiveData<MutableList<Int>>
    get() = _listDataEnabled
    init{
        _signList.value =  mutableListOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
        _listDataEnabled.value = mutableListOf(1, 1, 1, 1, 1, 1, 1)
        _TestString.value = ""
    }


    fun addInList(strVal: String, pos: Int){
        listInOrder.add(strVal)
        _TestString.value = ""
        for(i in listInOrder){
            _TestString.value = _TestString.value + " " + i
        }

    }


    fun compareByNum(): Boolean {
        Log.i("ttt", "start compare fun")
        var test = true
        var num = 0
        TonalityTest.currentTonality.value?.signCount?.let {
            num = it
        }
        if (num != listInOrder.size) return false
        if (TonalityTest.currentTonality.value?.signType == "Диезы") {
            for (i in 0..num - 1) {
                if (listInOrder[i] != rightSharpOrder[i]) {
                    test = false
                }
            }
            return@compareByNum test
        } else if (TonalityTest.currentTonality.value?.signType == "Бемоли") {
            for (i in 0..num - 1) {
                if (listInOrder[i] != rightFlatOrder[i]) {
                    test = false
                }
            }
            return@compareByNum test
        }
        return false
    }

    fun clearEnabled(){
        for (i in 0 until listDataEnabled.value?.size!!){
            listDataEnabled.value!![i] = 1
            Log.i("ttt", "list bind updated = $i ${_listDataEnabled.value!![i]}")
        }
        listInOrder.clear()
        _TestString.value = ""
        signList.value!!.shuffle()
    }
}