package com.example.musictheory.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Answer::class], version = 1, exportSchema = false)
abstract class AnswerDatabase: RoomDatabase() {
    abstract val answerDatabaseDao: AnswerDatabaseDao

    companion object{

        @Volatile
        private var INSTANCE: AnswerDatabase? = null

        fun getInstance(context: Context): AnswerDatabase{
            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AnswerDatabase::class.java,
                        "answer_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                    Log.i("INS", "INS_111")
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}