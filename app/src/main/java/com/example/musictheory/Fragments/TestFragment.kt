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
import com.example.musictheory.data.Signs
import com.example.musictheory.data.Tonality
import com.example.musictheory.data.TonalityTest
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.databinding.FragmentTestBinding
import com.example.musictheory.models.TestFragmentViewModel
import com.example.musictheory.models.TestFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_test.*

class
TestFragment : Fragment() {


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


        val manager = GridLayoutManager(activity, 4)
        binding.signList.layoutManager = manager
        val adapter = SignsAdapter()

        binding.signList.adapter = adapter

        Signs.signList.observe(viewLifecycleOwner, Observer{
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        Signs.listDataEnabled.observe(viewLifecycleOwner, Observer{
            adapter.data2 = it
            adapter.notifyDataSetChanged()
        })

        testFragmentViewModel.recViewBool.observe(viewLifecycleOwner, Observer {
            adapter.notifyDataSetChanged()
        })

        Signs.TestString.observe(viewLifecycleOwner, Observer{
            binding.txtNumPick.text = "Твой ответ будет: ${it}"
        })

        binding.btnClear2.setOnClickListener {
            Signs.clearEnabled()
            adapter.notifyDataSetChanged()
        }


        Signs.clearEnabled()
        adapter.notifyDataSetChanged()


        hideAll(binding)
        binding.btnAns0.visibility = View.VISIBLE
        binding.btnAns1.visibility = View.VISIBLE
        binding.btnAns2.visibility = View.VISIBLE
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = testFragmentViewModel.btnText.value?.size?.minus(1) ?: 0
        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value

        binding.numberPicker.setOnValueChangedListener { picker, oldVal, newVal ->

            var text = ""
            if(TonalityTest.currentQuestionNum.value == 2) {
                testFragmentViewModel.btnText.value?.get(newVal)?.let {
                    text = "\n (${Tonality.valueOf(it).rusName})"
                }
            }else text = ""
            Log.i("ttt", "$text")


            binding.txtNumPick.text = "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(newVal)} $text"
            testFragmentViewModel.setCurrentNumPick(newVal)
        }



        testFragmentViewModel.navigateToResult.observe(viewLifecycleOwner, Observer {
                num ->
            num?.let {
                this.findNavController().navigate(
                    TestFragmentDirections
                        .actionTestFragmentToResultFragment())
                testFragmentViewModel.doneNavigate()
//                sleepTrackerViewModel.doneNavigation()

            }
        })


        testFragmentViewModel.btnOverFlow.observe(viewLifecycleOwner, Observer {
            num ->
            num?.let{
                binding.numberPicker.maxValue = 0
                binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value
                binding.numberPicker.maxValue = testFragmentViewModel.btnText.value?.size?.minus(1) ?: 1
                binding.txtNumPick.text = "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(0)}"
                testFragmentViewModel.setCurrentNumPick(0)
                hideAll(binding)
                binding.numberPicker.visibility = View.VISIBLE
                binding.txtNumPick.visibility = View.VISIBLE
                binding.btnAnswer.visibility = View.VISIBLE

            }
            if(num==null){
                hideAll(binding)
                binding.btnAns0.visibility = View.VISIBLE
                binding.btnAns1.visibility = View.VISIBLE
                binding.btnAns2.visibility = View.VISIBLE
            }
        })

        testFragmentViewModel.recyclerViewNeed.observe(viewLifecycleOwner, Observer {
                num ->
            num?.let{
                hideAll(binding)
                binding.btnAnswer.visibility = View.VISIBLE
                binding.txtNumPick.visibility = View.VISIBLE
                binding.signList.visibility = View.VISIBLE
                binding.btnClear2.visibility = View.VISIBLE
                binding.txtNumPick.text = "Твой ответ будет: ${it}"
                testFragmentViewModel.setCurrentNumPick(0)


            }
        })






        binding.btn2.setOnClickListener {
            this.findNavController().navigate(TestFragmentDirections.actionTestFragmentToResultFragment())
        }

        return binding.root
    }

    fun hideAll(binding: FragmentTestBinding){
        binding.btnAns0.visibility = View.GONE
        binding.btnAns1.visibility = View.GONE
        binding.btnAns2.visibility = View.GONE
        binding.numberPicker.visibility = View.GONE
        binding.txtNumPick.visibility = View.GONE
        binding.btnAnswer.visibility = View.GONE
        binding.signList.visibility = View.GONE
        binding.btnClear.visibility = View.GONE
        binding.btnClear2.visibility = View.GONE
    }



}