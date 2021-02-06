package com.example.musictheory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.musictheory.R
import kotlinx.android.synthetic.main.fragment_theory.*


class TheoryFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        var textHelp ="text"
//        txtTheory.setText(textHelp)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_theory, container, false)


    }


}