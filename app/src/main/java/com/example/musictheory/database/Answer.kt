package com.example.musictheory.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers_table")
data class Answer(
    @PrimaryKey(autoGenerate = false)
    var ansId: Long = 0L,

    @ColumnInfo(name = "quality")
    var quality: Int = 0,

    @ColumnInfo(name = "stroka")
    var stroka: String = ""
//
//    @ColumnInfo(name = "errorList")
//    var errorList: List<String> = listOf("qwe")


)