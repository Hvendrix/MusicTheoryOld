package com.example.musictheory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.musictheory.R
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.FragmentTestBinding
import com.example.musictheory.models.TestFragmentViewModel
import com.example.musictheory.models.TestFragmentViewModelFactory

class TestFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //default fragment inflater
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_test, container, false)

        val binding : FragmentTestBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AnswerDatabase.getInstance(application).answerDatabaseDao

        val viewModelFactory = TestFragmentViewModelFactory(dataSource, application)

        val testFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(TestFragmentViewModel::class.java)

        binding.lifecycleOwner = this

        binding.testFragmentViewModel = testFragmentViewModel

        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = 10

        binding.btn2.setOnClickListener {
            this.findNavController().navigate(TestFragmentDirections.actionTestFragmentToResultFragment())
        }



        return binding.root
    }


}