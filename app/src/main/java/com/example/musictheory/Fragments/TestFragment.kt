package com.example.musictheory.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musictheory.R
import com.example.musictheory.adapters.SignsAdapter
import com.example.musictheory.data.Notes
import com.example.musictheory.data.Signs
import com.example.musictheory.data.Tonality
import com.example.musictheory.data.TonalityTest
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.FragmentTestBinding
import com.example.musictheory.models.TestFragmentViewModel
import com.example.musictheory.models.TestFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_test.*

class TestFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //default fragment inflater
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_test, container, false)

        val binding: FragmentTestBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_test, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = AnswerDatabase.getInstance(application).answerDatabaseDao

        val viewModelFactory = TestFragmentViewModelFactory(dataSource, application)

        val testFragmentViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(TestFragmentViewModel::class.java)


        binding.lifecycleOwner = this

        binding.testFragmentViewModel = testFragmentViewModel


        val manager = GridLayoutManager(activity, 4)
        binding.signList.layoutManager = manager


        val adapter = SignsAdapter()

        binding.signList.adapter = adapter

        Signs.signList.observe(viewLifecycleOwner, Observer {
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        Signs.listDataEnabled.observe(viewLifecycleOwner, Observer {
            adapter.data2 = it
            adapter.notifyDataSetChanged()
        })

        testFragmentViewModel.recViewBool.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        Signs.TestString.observe(viewLifecycleOwner, Observer {
            binding.txtNumPick.text = "Твой ответ будет: ${it}"
        })

        binding.btnClear.setOnClickListener {
            testFragmentViewModel.onClearRecView(adapter)
        }


        testFragmentViewModel.onClearRecView(adapter)


        hideAll(binding)
        binding.btnAns0.visibility = View.VISIBLE
        binding.btnAns1.visibility = View.VISIBLE
        binding.btnAns2.visibility = View.VISIBLE
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = testFragmentViewModel.btnText.value?.size?.minus(1) ?: 0
        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value





        testFragmentViewModel.navigateToResult.observe(viewLifecycleOwner, Observer { num ->
            num?.let {
                this.findNavController().navigate(
                    TestFragmentDirections
                        .actionTestFragmentToResultFragment()
                )
                testFragmentViewModel.doneNavigate()

            }
        })


        testFragmentViewModel.btnOverFlow.observe(viewLifecycleOwner, Observer { num ->
            num?.let {
                if(num==1){
                    binding.numberPicker.maxValue = 0
                    binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value
                    binding.numberPicker.maxValue =
                        testFragmentViewModel.btnText.value?.size?.minus(1) ?: 1
                    binding.txtNumPick.text = "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(0)}"
                    observeForNumPick(binding, testFragmentViewModel, 1)
                    testFragmentViewModel.setCurrentNumPick(0)
                    hideAll(binding)
                    binding.numberPicker.visibility = View.VISIBLE
                    binding.txtNumPick.visibility = View.VISIBLE
                    binding.btnAnswer.visibility = View.VISIBLE
                } else if (num==2) {

                    hideAll(binding)
                    observeForNumPick(binding, testFragmentViewModel, 2)
                    Log.i("ttt", "btnFlow =2")
                    Log.i("ttt", "${testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0)}")
                    binding.numberPicker.maxValue = 0
                    binding.numberPicker.displayedValues =
                        testFragmentViewModel.specificBtnTxt.value?.get(0)
                    Log.i("ttt", "size")
                    binding.numberPicker.maxValue =
                        testFragmentViewModel.specificBtnTxt.value?.get(0)?.size?.minus(1) ?: 0
                    Log.i("ttt", "ok1")
                    binding.numberPicker2.maxValue = 0
                    binding.numberPicker2.displayedValues =
                        testFragmentViewModel.specificBtnTxt.value?.get(1)
                    binding.numberPicker2.maxValue =
                        testFragmentViewModel.specificBtnTxt.value?.get(1)?.size?.minus(1) ?: 0
                    Log.i("ttt", "ok2")

                    //ужас
                    testFragmentViewModel.setCurrentNumPick(0)
                    var text = ""
                    var text1 = ""
                    var text2 = ""

                        text1 = testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0).toString()
                        text2 = testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(0).toString()
                        text = "$text1-$text2"
                        binding.txtNumPick.text =
                            "Твой ответ будет: $text"
                        testFragmentViewModel.setCurrentAnswer(text)

                    //далее тоже ужас, но не настолько
                    binding.numberPicker.visibility = View.VISIBLE
                    binding.txtNumPick.visibility = View.VISIBLE
                    binding.btnAnswer.visibility = View.VISIBLE
                    binding.numberPicker2.visibility = View.VISIBLE
                }

            }
            if (num == null) {
                observeForNumPick(binding, testFragmentViewModel, 0)
                hideAll(binding)
                binding.btnAns0.visibility = View.VISIBLE
                binding.btnAns1.visibility = View.VISIBLE
                binding.btnAns2.visibility = View.VISIBLE
            }
//            num?.let {
//                binding.numberPicker.maxValue = 0
//                binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value
//                binding.numberPicker.maxValue =
//                    testFragmentViewModel.btnText.value?.size?.minus(1) ?: 1
////                binding.txtNumPick.text = "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(0)}"
//                observeForNumPick(binding, testFragmentViewModel, 1)
//                testFragmentViewModel.setCurrentNumPick(0)
//                hideAll(binding)
//                binding.numberPicker.visibility = View.VISIBLE
//                binding.txtNumPick.visibility = View.VISIBLE
//                binding.btnAnswer.visibility = View.VISIBLE
//                if (testFragmentViewModel.btnOverFlow.value == 2) {
//                    observeForNumPick(binding, testFragmentViewModel, 2)
//                    Log.i("ttt", "btnFlow =2")
//                    Log.i("ttt", "${testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0)}")
//                    binding.numberPicker.maxValue = 0
//                    binding.numberPicker.displayedValues =
//                        testFragmentViewModel.specificBtnTxt.value?.get(0)
//                    Log.i("ttt", "size")
//                    binding.numberPicker.maxValue =
//                        testFragmentViewModel.specificBtnTxt.value?.get(0)?.size?.minus(1) ?: 0
//                    Log.i("ttt", "ok1")
//                    binding.numberPicker2.maxValue = 0
//                    binding.numberPicker2.displayedValues =
//                        testFragmentViewModel.specificBtnTxt.value?.get(1)
//                    binding.numberPicker2.maxValue =
//                        testFragmentViewModel.specificBtnTxt.value?.get(1)?.size?.minus(1) ?: 0
//                    Log.i("ttt", "ok2")
//                    binding.numberPicker2.visibility = View.VISIBLE
//                }
//
//            }
//            if (num == null) {
//                observeForNumPick(binding, testFragmentViewModel, 0)
//                hideAll(binding)
//                binding.btnAns0.visibility = View.VISIBLE
//                binding.btnAns1.visibility = View.VISIBLE
//                binding.btnAns2.visibility = View.VISIBLE
//            }
        })



        testFragmentViewModel.recyclerViewNeed.observe(viewLifecycleOwner, Observer { num ->
            num?.let {
                hideAll(binding)
                binding.btnAnswer.visibility = View.VISIBLE
                binding.txtNumPick.visibility = View.VISIBLE
                binding.signList.visibility = View.VISIBLE
                binding.btnClear.visibility = View.VISIBLE
                binding.txtNumPick.text = "Твой ответ будет: ${it}"
                testFragmentViewModel.setCurrentNumPick(0)


            }
        })






        binding.btn2.setOnClickListener {
            this.findNavController()
                .navigate(TestFragmentDirections.actionTestFragmentToResultFragment())
        }

        return binding.root
    }

    fun hideAll(binding: FragmentTestBinding) {
        binding.btnAns0.visibility = View.GONE
        binding.btnAns1.visibility = View.GONE
        binding.btnAns2.visibility = View.GONE
        binding.numberPicker.visibility = View.GONE
        binding.numberPicker2.visibility = View.GONE
        binding.txtNumPick.visibility = View.GONE
        binding.btnAnswer.visibility = View.GONE
        binding.signList.visibility = View.GONE
        binding.btnClear.visibility = View.GONE
        binding.btnClear.visibility = View.GONE
    }

    fun observeForNumPick(
        binding: FragmentTestBinding,
        testFragmentViewModel: TestFragmentViewModel,
        numOfNumPick: Int
    ) {
        when (numOfNumPick) {
            1 -> {
                binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                    var text = ""
                    if (TonalityTest.currentQuestionNum.value == 2) {
                        testFragmentViewModel.btnText.value?.get(newVal)?.let {
                            text = "\n (${Tonality.valueOf(it).rusName})"
                        }
                    } else text = ""
                    binding.txtNumPick.text =
                        "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(newVal)} $text"
                    testFragmentViewModel.setCurrentNumPick(newVal)
                }
            }
            2 -> {
                var text = ""
                var text1 = ""
                var text2 = ""
                text1 = testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0).toString()
                text2 = testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(0).toString()
                binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->
                    text1 = testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "Твой ответ будет: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                }
                binding.numberPicker2.setOnValueChangedListener { picker, oldVal, newVal ->
                    text2 = testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "Твой ответ будет: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                }
            }
            0 -> {

            }

        }
    }


}