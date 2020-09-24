package com.example.musictheory.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.musictheory.R
import com.example.musictheory.data.Sharps
import com.example.musictheory.database.Answer
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.ActivityMainBinding
import com.example.musictheory.models.MyViewModel
import kotlinx.android.synthetic.main.fragment_test.*

class MainActivity : AppCompatActivity() {
    lateinit var myViewModel: MyViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var l = mutableListOf<String>()
        l.add("asd")


//        numberPicker.wrapSelectorWheel = false

//        val myViewModel = MyViewModel() так не надо делать
        // Если просто создавать класс MyViewModel, то он будет пересоздавать его, каждый раз при пересоздании активити



//        myViewModel = ViewModelProviders.of(this).get(MyViewModel::class.java)
//
//        // Привязка данных с помощью генерируемого джава класса
//        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//        binding.name2 = "yyyyy"
//
//
//
//        // Подпись байндинга на события жизненного цикла
//        binding.lifecycleOwner = this
//
//        binding.myViewModel = myViewModel

    }
}