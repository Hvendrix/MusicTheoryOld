package com.example.musictheory.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AnswerDatabaseDao {
    @Insert
    fun insert(answer: Answer)

//    @Query(" SELECT * FROM answers_table ORDER BY ansId DESC LIMIT 1")
    @Query("SELECT * FROM answers_table WHERE ansId = :key")
    fun getOneAns(key: Int): Answer?

    @Query("DELETE FROM answers_table")
    fun clear()

    @Query("SELECT quality FROM answers_table WHERE ansId = :key")
    fun getOneQuality(key: Int):LiveData<Int>

    @Query("SELECT * FROM answers_table ORDER BY ansId DESC")
    fun getAllAnswers():LiveData<List<Answer>>
}