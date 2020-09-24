package com.example.musictheory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.musictheory.R
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.FragmentResultBinding
import com.example.musictheory.databinding.FragmentTestBinding
import com.example.musictheory.models.ResultFragmentViewModel
import com.example.musictheory.models.ResultFragmentViewModelFactory
import com.example.musictheory.models.TestFragmentViewModel
import com.example.musictheory.models.TestFragmentViewModelFactory

class ResultFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_result, container, false)
        val binding : FragmentResultBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_result, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AnswerDatabase.getInstance(application).answerDatabaseDao

        val viewModelFactory = ResultFragmentViewModelFactory(dataSource, application)

        val resultFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            ResultFragmentViewModel::class.java)

        binding.lifecycleOwner = this

        binding.resultFragmentViewModel = resultFragmentViewModel
        return binding.root
    }


}