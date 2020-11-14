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
    var positionInStaveVert = mutableMapOf(-1f to 0.335f, -0.5f to 0.235f, 1f to 0.785f, 1.5f to 0.695f, 2f to 0.625f, 2.5f to 0.525f, 3f to 0.415f )
    var positionInStaveHorizont = mutableMapOf(1f to 0.255f, 2f to 0.405f, 3f to 0.555f, 4f to 0.705f, 5f to 0.855f, 6f to 0.999f, 7f to 0.415f )
    var _signsInStave = MutableLiveData<MutableList<Triple<Float, Float, String>>>()
    var noteInOrderInLines = mapOf("До" to -1f, "Ре" to -0.5f, "Ми" to 1f, "Фа" to 1.5f, "Соль" to 2f, "Ля" to 2.5f, "Си" to 3f,
        "до" to -1f, "ре" to -0.5f, "ми" to 1f, "фа" to 1.5f, "соль" to 2f, "ля" to 2.5f, "си" to 3f)

    var currentSignTypeInSigns = mutableListOf<String>()

    private var _TestString = MutableLiveData<String>()


    val TestString : LiveData<String>
    get() = _TestString

    val signList : LiveData<MutableList<String>>
    get() = _signList

    val listDataEnabled : LiveData<MutableList<Int>>
    get() = _listDataEnabled


    val signsInStave : LiveData<MutableList<Triple<Float, Float, String>>>
    get() = _signsInStave



    init{
        _signList.value =  mutableListOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
        _listDataEnabled.value = mutableListOf(1, 1, 1, 1, 1, 1, 1)
        _TestString.value = ""
        _signsInStave.value = mutableListOf()
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


        _signsInStave.value = mutableListOf()
    }
}