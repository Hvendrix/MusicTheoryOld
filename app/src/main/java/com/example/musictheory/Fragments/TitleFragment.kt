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
        return binding.root
    }


}