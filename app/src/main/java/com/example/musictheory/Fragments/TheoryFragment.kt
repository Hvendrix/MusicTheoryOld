package com.example.musictheory.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.musictheory.R
import com.example.musictheory.databinding.FragmentTheoryBinding


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
                "Если тональность минорная, то делаем еще один шаг: ищем параллельную тональность (там те же знаки). Минор строится на III ступени (нужно построить малую терцию вверх), а дальше действуем по обычному алгоритму." +
                "\n"

        var textHelp2 = "Тритоны состоят только(!) из неустойчивых ступеней и строятся вокруг устойчивых. \n" +
                "Всегда в гармоническом виде мажора и минора.\n" +
                "\n" +
                "Алгоритм:\n" +
                "1. Найти устойчивые ступени, те Т53\n" +
                "2. Найти гармоническую ступень: в мажоре- VI пониженная, в миноре  - VII повышенная.\n" +
                "3. Из найденного Т53 построить две тонические терции (это уже готовые разрешения для тритонов).\n" +
                "4. Построить два тритона вокруг двух терций (Не забыть про знаки в тональностях! и особенно про гармоническую ноту!!!)\n" +
                "\n" +
                "Если нужно найти увеличенные кварты, то делаем два дополнительных шага, либо включаем соображалку.\n" +
                "\n" +
                "2 шага:\n" +
                "1. Ум5 обращаются в ув 4, путем переноса нижней ноты наверх(как и любое обращение)\n" +
                "2. Разрешение (малая и большая терции) обращаются соответственно в большую и малую сексты.\n" +
                "\n" +
                "Соображалка:\n" +
                "1. Можно с самого начала построить тонические сексты на III и V ступенях.\n" +
                "2. Построить два тритона не вокруг, а внутри секст." +
                "\n"


        val nameTest = arguments?.getString("testName")
        when (nameTest) {
            "TonalityTest" -> binding.helpText = textHelp
            "TritonTest" -> binding.helpText = textHelp2
        }


        return binding.root

    }


}