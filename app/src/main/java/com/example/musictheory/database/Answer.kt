package com.example.musictheory.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers_table")
data class Answer(
    @PrimaryKey(autoGenerate = true)
    var ansId: Int = 0,

    @ColumnInfo(name = "quality")
    var quality: Int = 0,

    @ColumnInfo(name = "stroka")
    var stroka: String = "",

    @ColumnInfo(name = "errorString")
    var errorString: String = ""
//
//    @ColumnInfo(name = "errorList")
//    var errorList: List<String> = listOf("qwe")


)