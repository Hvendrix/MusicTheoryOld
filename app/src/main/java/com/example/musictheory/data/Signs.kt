package com.example.musictheory.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Signs {
    private var _signList = MutableLiveData<MutableList<String>>()
    var listEnabled = mutableListOf(1, 1, 1, 1, 1, 1, 1)
    private var _listDataEnabled = MutableLiveData<MutableList<Int>>()
    var listInOrder = mutableListOf<String>()
    var rightSharpOrder = listOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
    var rightFlatOrder = listOf("Си", "Ми", "Ля", "Ре", "Соль", "До", "Фа")
    var positionInStaveVert = mutableMapOf(
        -1f to 0.795f,
        -0.5f to 0.745f,
        1f to 0.700f,
        1.5f to 0.645f,
        2f to 0.600f,
        2.5f to 0.545f,
        3f to 0.505f,
        3.5f to 0.445f,
        4f to 0.390f,
        4.5f to 0.340f,
        5f to 0.295f,
        5.5f to 0.235f
    )
    var positionInStaveHorizont = mutableMapOf(
        1f to 0.335f,
        2f to 0.405f,
        3f to 0.555f,
        4f to 0.705f,
        5f to 0.855f,
        6f to 0.999f,
        7f to 0.415f
    )


    var positionInStaveHorTwoBarTwoPart = mutableMapOf(
        1f to 0.200f,
        2f to 0.200f,
        3f to 0.600f,
        4f to 0.600f,
        5f to 0.855f,
        6f to 0.999f,
        7f to 0.415f
    )


// need init fun with step
    var positionHorizontalKeySignature = mutableMapOf(
        1f to 0.080f,
        2f to 0.115f,
        3f to 0.150f,
        4f to 0.185f,
        5f to 0.220f,
        6f to 0.255f,
        7f to 0.290f
    )

    var _signsInStave = MutableLiveData<MutableList<Triple<Float, Float, String>>>()
    var noteInOrderInLines = mapOf(
        "До" to -1f,
        "Ре" to -0.5f,
        "Ми" to 1f,
        "Фа" to 1.5f,
        "Соль" to 2f,
        "Ля" to 2.5f,
        "Си" to 3f,
        "До2" to 3.5f,
        "Ре2" to 4f,
        "Ми2" to 4.5f,
        "Фа2" to 5f,
        "Соль2" to 5.5f,
        "до" to -1f,
        "ре" to -0.5f,
        "ми" to 1f,
        "фа" to 1.5f,
        "соль" to 2f,
        "ля" to 2.5f,
        "си" to 3f,
        "до2" to 3.5f,
        "ре2" to 4f,
        "ми2" to 4.5f,
        "фа2" to 5f,
        "соль2" to 5.5f,


    )


    var x1 = MutableLiveData<Int>()
    var x2: Int = 0


    var currentSignTypeInSigns = mutableListOf<String>()

    private var _TestString = MutableLiveData<String>()


    val TestString: LiveData<String>
        get() = _TestString

    val signList: LiveData<MutableList<String>>
        get() = _signList

    val listDataEnabled: LiveData<MutableList<Int>>
        get() = _listDataEnabled


    val signsInStave: LiveData<MutableList<Triple<Float, Float, String>>>
        get() = _signsInStave


    init {
        _signList.value = mutableListOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
        _listDataEnabled.value = mutableListOf(1, 1, 1, 1, 1, 1, 1)
        _TestString.value = ""
        _signsInStave.value = mutableListOf()
        x1.value = 0
    }

//    fun testDeleteThis(){
//        _signList.value = mutableListOf("Фа", "До", "Соль", "Ре", "Ля", "Ми", "Си")
//    }


    fun getOne() {
        x1.value = x1.value?.plus(1)
    }

    fun addInList(strVal: String, pos: Int) {
        listInOrder.add(strVal)
        _TestString.value = ""
        for (i in listInOrder) _TestString.value += " $i"
    }


    fun compareByNum(tonality: Tonality): Boolean {
        val num = tonality.signCount

        if (listInOrder.size != num) return false

        if (tonality.signType == "Диезы") {
            for (i in 0 until num) {
                if (listInOrder[i] != rightSharpOrder[i]) {
                    return false
                }
            }
            return true
        } else if (tonality.signType == "Бемоли") {
            for (i in 0 until num) {
                if (listInOrder[i] != rightFlatOrder[i]) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun triadTest(tonality: Tonality): Boolean {
        val num = 3
        if (num != listInOrder.size) return false
        val listNeeded = mutableListOf<String>()

        listNeeded.add(tonality.rusName.substringBefore(" ").toLowerCase())
        if (listNeeded[0].indexOf("-") != -1) listNeeded[0] = listNeeded[0].substringBefore("-")
        val numOfTon = Notes.notesTwoOctaves.indexOf(listNeeded[0])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 2])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 4])

        listInOrder.forEachIndexed { i, el ->
            Log.i("ttt", "triad compare \'$el\', \'${listNeeded[i]}\'")
            if (el.toLowerCase() != listNeeded[i]) return false
        }
        return true
    }

    fun twoTonicThird(tonality: Tonality): Boolean {
        val num = 4
        if (num != listInOrder.size) return false
        val listNeeded = mutableListOf<String>()

        listNeeded.add(tonality.rusName.substringBefore(" ").toLowerCase())
        if (listNeeded[0].indexOf("-") != -1) listNeeded[0] = listNeeded[0].substringBefore("-")
        val numOfTon = Notes.notesTwoOctaves.indexOf(listNeeded[0])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 2])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 2])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 4])
        listInOrder.forEachIndexed { i, el ->
            Log.i("ttt", "triad compare \'$el\', \'${listNeeded[i]}\'")
            if (el.toLowerCase() != listNeeded[i]) return false
        }
        return true
    }

    fun twoReducedFifth(tonality: Tonality): Boolean {
        val num = 4
        if (num != listInOrder.size) return false
        val listNeeded = mutableListOf<String>()

        listNeeded.add(tonality.rusName.substringBefore(" ").toLowerCase())
        if (listNeeded[0].indexOf("-") != -1) listNeeded[0] = listNeeded[0].substringBefore("-")
        val numOfTon = Notes.notesTwoOctaves.indexOf(listNeeded[0])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 6])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 3])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 1])
        listNeeded.add(Notes.notesTwoOctaves[numOfTon + 5])
        listNeeded.removeAt(0)
        listInOrder.forEachIndexed { i, el ->
            Log.i("ttt", "triad compare \'$el\', \'${listNeeded[i]}\'")
            if (el.toLowerCase() != listNeeded[i]) return false
        }
        return true
    }


    fun clearEnabledOld() {
        for (i in 0 until listDataEnabled.value?.size!!) {
            listDataEnabled.value!![i] = 1
            Log.i("ttt", "list bind updated = $i ${_listDataEnabled.value!![i]}")
        }
        listInOrder.clear()

        _TestString.value = ""
        signList.value!!.shuffle()
        _signsInStave.value = mutableListOf()
    }

    fun clearEnabled() {
        for (i in 0 until listDataEnabled.value?.size!!) {
            listDataEnabled.value!![i] = 1
        }
        listInOrder.clear()

        _TestString.value = ""
        signList.value!!.shuffle()
//        _signsInStave.value = mutableListOf()


    }

}