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
        var textHelp = "Запомнить:\n" +
                "1) Для диезов: фа, до, соль, ре, ля, ми, си, кошка ехала в такси.\n" +
                "2) Для бемолей: си, ми, ля, ре, соль, до, фа, в гости к мышке шла дрофа.\n" +
                "\n" +
                "Если в названии бемоль, то бемольная тональность, \n" +
                "если диез, либо вообще нет знака, то диезная. (кроме фа мажора, там один бемоль)\n" +
                "\n" +
                "Алгоритм для диезной тональности:\n" +
                "1) Ищем VII ступень\n" +
                "2) Перечисляем по первой считалочке знаки, пока не дойдем до VII ступени\n" +
                "\n" +
                "Перечисленные знаки и будут ключевыми знаками при ключе.\n" +
                "\n" +
                "Алгоритм для бемольной тональности:\n" +
                "1) Ищем I ступень .\n" +
                "2) Перечисляем по второй считалочке знаки пока не дойдем до I ступени, а потом прибавляем еще один знак.\n" +
                "\n" +
                "Если тональность минорная, то делаем еще один шаг: ищем параллельную тональность (там те же знаки). Минор строится на III ступени (нужно построить малую терцию вверх), а дальше действуем по обычному алгоритму."
        binding.helpText = textHelp

        return binding.root

    }


}