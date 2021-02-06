package com.example.musictheory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.example.musictheory.R
import com.example.musictheory.databinding.FragmentResultBinding
import com.example.musictheory.databinding.FragmentTheoryBinding
import kotlinx.android.synthetic.main.fragment_theory.*


class TheoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentTheoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_theory, container, false)
//         Замена
//         Inflate the layout for this fragment
//                var rootView = inflater.inflate(R.layout.fragment_theory, container, false)
//                return rootView
        binding.lifecycleOwner = this
        var textHelp = "text"
        binding.helpText = textHelp

        return binding.root

    }


}