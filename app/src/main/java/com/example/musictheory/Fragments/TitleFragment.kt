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
import com.example.musictheory.data.ConstsForTesting
import com.example.musictheory.data.tests.TonalityTest
import com.example.musictheory.data.tests.TrebleClefTest
import com.example.musictheory.data.tests.TritonTest
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.FragmentTitleBinding
import com.example.musictheory.models.TitleFragmentViewModel
import com.example.musictheory.models.TitleFragmentViewModelFactory


class TitleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_title, container, false)


//        titleViewMode = ViewModelProviders.of(this).get(TitleFragmentViewModel::class.java)
        var binding : FragmentTitleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_title, container, false)


        val application = requireNotNull(this.activity).application

        val dataSource = AnswerDatabase.getInstance(application).answerDatabaseDao

        val viewModelFactory = TitleFragmentViewModelFactory(dataSource, application)

        val titleFragmentViewModel = ViewModelProviders.of(this, viewModelFactory).get(TitleFragmentViewModel::class.java)


        binding.lifecycleOwner = this

        binding.titleFragmentViewModel = titleFragmentViewModel
        binding.lastname = "qqqq"

        binding.btn1.setOnClickListener {
            toTestFragment(1)
        }
        binding.btn2.setOnClickListener {
            toTestFragment(2)
        }
        binding.btn3.setOnClickListener {
            toTestFragment(3)
        }

        return binding.root
    }

    fun toTestFragment(numTest: Int) {
        when(numTest){
            1-> ConstsForTesting.testingTest = TonalityTest
            2-> ConstsForTesting.testingTest = TritonTest
            3-> ConstsForTesting.testingTest = TrebleClefTest
        }

        this.findNavController()
            .navigate(TitleFragmentDirections.actionTitleFragmentToTestFragment("test"))
    }


}