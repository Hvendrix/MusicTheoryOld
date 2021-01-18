package com.example.musictheory.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.R
import com.example.musictheory.adapters.SignItemDecoration
import com.example.musictheory.data.Signs
import com.example.musictheory.data.Tonality
import com.example.musictheory.data.tests.TonalityTest
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


//        val adapter = SignsAdapter()
        val adapter = testFragmentViewModel.adapter

        binding.signList.adapter = adapter

//        binding.signList.addItemDecoration(DividerItemDecoration(context, 48))
//        binding.signList.addItemDecoration(SignItemDecoration(489))
//        binding.signList.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))


        Signs.signList.observe(viewLifecycleOwner, Observer {
            adapter.viewModel = testFragmentViewModel
            adapter.data = it
            adapter.notifyDataSetChanged()
        })

        testFragmentViewModel.currentSignType.observe(viewLifecycleOwner, Observer {
            Log.i("xxx", "observ done")
            Signs.currentSignTypeInSigns = it
            Log.i("xxx", "${Signs.currentSignTypeInSigns[0]}")
        })

        Signs.listDataEnabled.observe(viewLifecycleOwner, Observer {
            adapter.data2 = it
            adapter.notifyDataSetChanged()
        })
7
//        testFragmentViewModel.recViewBool.observe(viewLifecycleOwner, Observer {
//            adapter.notifyDataSetChanged()
//        })

        Signs.TestString.observe(viewLifecycleOwner, Observer {
            binding.txtNumPick.text = "${it}"
//            binding.txtNumPick.text = "Твой ответ будет: ${it}"
        })

        binding.btnClear.setOnClickListener {
            testFragmentViewModel.onClearRecView(adapter)
        }




        testFragmentViewModel.onClearRecView(adapter)



        binding.btnTxtTonFirst.setOnClickListener {
            Signs.getOne() ?: Log.i("xxx", "something wrong xx = ${Signs.x1.value}   ${Signs.x2}")
            Signs.x2++
            Log.i("xxx", "values xx = ${Signs.x1.value}   ${Signs.x2}")
        }


        hideAll(binding)
        binding.numberPicker.minValue = 0
        binding.numberPicker.maxValue = testFragmentViewModel.btnText.value?.size?.minus(1) ?: 0
        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value

        val notesViewInLineList : MutableList<ImageView> = mutableListOf()
        val staticNotesViewInLineList : MutableList<ImageView> = mutableListOf()

        testFragmentViewModel.interfaceType.observe(viewLifecycleOwner, Observer{ type ->
            type?.let {
                adapter.notifyDataSetChanged()
                when (type) {
                    "numPick" -> {
                        binding.numberPicker.maxValue = 0
                        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value
                        binding.numberPicker.maxValue =
                            testFragmentViewModel.btnText.value?.size?.minus(1) ?: 1
                        binding.txtNumPick.text = "${testFragmentViewModel.btnText.value?.get(0)}"
//                        binding.txtNumPick.text = "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(0)}"
                        observeForNumPick(binding, testFragmentViewModel, 1)
                        testFragmentViewModel.setCurrentNumPick(0)
                        hideAll(binding)
                        binding.numberPicker.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.btnAnswer.visibility = View.VISIBLE

                    }
                    "twoNumPick" -> {

                        hideAll(binding)
                        observeForNumPick(binding, testFragmentViewModel, 2)
                        binding.numberPicker.maxValue = 0
                        binding.numberPicker.displayedValues =
                            testFragmentViewModel.specificBtnTxt.value?.get(0)
                        binding.numberPicker.maxValue =
                            testFragmentViewModel.specificBtnTxt.value?.get(0)?.size?.minus(1) ?: 0
                        binding.numberPicker2.maxValue = 0
                        binding.numberPicker2.displayedValues =
                            testFragmentViewModel.specificBtnTxt.value?.get(1)
                        binding.numberPicker2.maxValue =
                            testFragmentViewModel.specificBtnTxt.value?.get(1)?.size?.minus(1) ?: 0

                        //ужас
                        testFragmentViewModel.setCurrentNumPick(0)
                        val text = "${testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0).toString()}-${testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(0).toString()}"
                        binding.txtNumPick.text = "${text}"
//                        binding.txtNumPick.text = "Твой ответ будет: ${text}"
                        testFragmentViewModel.setCurrentAnswer(text)

                        //далее тоже ужас, но не настолько
                        binding.numberPicker.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.btnAnswer.visibility = View.VISIBLE
                        binding.numberPicker2.visibility = View.VISIBLE
                        binding.groupStave.visibility = View.VISIBLE

                        Toast.makeText(context, "ttttttttttt", Toast.LENGTH_SHORT).show()
                        testFragmentViewModel.signInStave.observe(viewLifecycleOwner, Observer { signTripleList ->

                            signTripleList?.let{

                                if(signTripleList.isEmpty()){
                                    for(i in notesViewInLineList){
//                        binding.constraintLayout.removeViewAt(i)
                                        Log.i("ttt", "remove index $i")
                                        binding.constraintLayout.removeView(i)
                                    }
                                }
                                for(i in signTripleList){
                                    Toast.makeText(context, "tttttt   ${i.first}  ${i.second} ${i.third}", Toast.LENGTH_SHORT).show()
                                    Log.i("xxx", "yes observe it")
                                    var choiceImg = 0
                                    var vertChang = 0f
                                    when (i.third) {
                                        "диезы", "диез" -> choiceImg = R.drawable.sharp
                                        "бекар" -> choiceImg = R.drawable.bekar
                                        "бемоли", "бемоль" -> {
                                            choiceImg = R.drawable.bemol
                                            vertChang = -0.05f

                                        }
                                        "целая" -> choiceImg = R.drawable.int_note
                                        "целаятрезвучие" -> {
                                            Log.i("xxx", "start")
                                            choiceImg = R.drawable.int_note
                                            val noteView = ImageView(this.context)
                                            noteView.id = View.generateViewId()
                                            notesViewInLineList.add(noteView)
                                            noteView.setImageResource(choiceImg)
                                            binding.constraintLayout.addView(noteView)
                                            noteView.layoutParams.height = 52
                                            noteView.layoutParams.width = 52
                                            val set = ConstraintSet()
                                            set.clone(binding.constraintLayout)
                                            set.connect(noteView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
                                            set.connect(noteView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
                                            set.connect(noteView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
                                            set.connect(noteView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
                                            set.setHorizontalBias(noteView.id, 0.9f)
                                            set.setVerticalBias(noteView.id, Signs.positionInStaveVert[i.first]!! + vertChang)
                                            set.applyTo(binding.constraintLayout)
                                            Log.i("xxx", "stop")
                                            continue
                                        }
                                        "дветерции" ->{
                                            choiceImg = R.drawable.int_note
                                        }
                                        "диезыприключе" ->{
                                            choiceImg = R.drawable.sharp
                                        }
                                        "бемолиприключе" ->{
                                            choiceImg = R.drawable.bemol
                                            vertChang = -0.05f
                                        }
                                    }
                                    createSignView(binding, choiceImg, notesViewInLineList, i, vertBias = vertChang)

//                                    Log.i("xxx", "next")
//                                    val noteView = ImageView(this.context)
//                                    noteView.id = View.generateViewId()
//                                    notesViewInLineList.add(noteView)
//                                    noteView.setImageResource(choiceImg)
//                                    binding.constraintLayout.addView(noteView)
//                                    noteView.layoutParams.height = 52
//                                    noteView.layoutParams.width = 52
//                                    val set = ConstraintSet()
//                                    set.clone(binding.constraintLayout)
//                                    set.connect(noteView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
//                                    set.connect(noteView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
//                                    set.connect(noteView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
//                                    set.connect(noteView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
//                                    set.setHorizontalBias(noteView.id, Signs.positionInStaveHorizont[i.second]!!)
//                                    set.setVerticalBias(noteView.id, Signs.positionInStaveVert[i.first]!! + vertChang)
//                                    set.applyTo(binding.constraintLayout)
                                }

                            }
                        })


                    }
                    "table" -> {
                        hideAll(binding)

                        testFragmentViewModel.signInStave.observe(viewLifecycleOwner, Observer { signTripleList ->
                            signTripleList?.let{
                                if(signTripleList.isEmpty()){
                                    for(i in notesViewInLineList){
//                        binding.constraintLayout.removeViewAt(i)
                                        Log.i("ttt", "remove index $i")
                                        binding.constraintLayout.removeView(i)
                                    }
                                }
                                for(i in signTripleList){
                                    var choiceImg = 0
                                    var vertChang = 0f
                                    when (i.third) {
                                        "диезы", "диез" -> choiceImg = R.drawable.sharp
                                        "бекар" -> choiceImg = R.drawable.bekar
                                        "бемоли", "бемоль" -> {
                                            choiceImg = R.drawable.bemol
                                            vertChang = -0.05f
                                        }
                                        "целая" -> choiceImg = R.drawable.int_note
                                        "целаятрезвучие" -> {
                                            Log.i("xxx", "start")
                                            choiceImg = R.drawable.int_note
                                            val noteView = ImageView(this.context)
                                            noteView.id = View.generateViewId()
                                            notesViewInLineList.add(noteView)
                                            noteView.setImageResource(choiceImg)
                                            binding.constraintLayout.addView(noteView)
                                            noteView.layoutParams.height = 52
                                            noteView.layoutParams.width = 52
                                            val set = ConstraintSet()
                                            set.clone(binding.constraintLayout)
                                            set.connect(noteView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
                                            set.connect(noteView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
                                            set.connect(noteView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
                                            set.connect(noteView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
                                            set.setHorizontalBias(noteView.id, 0.9f)
                                            set.setVerticalBias(noteView.id, Signs.positionInStaveVert[i.first]!! + vertChang)
                                            set.applyTo(binding.constraintLayout)
                                            Log.i("xxx", "stop")
                                            continue
                                        }
                                        "дветерции" ->{
                                            choiceImg = R.drawable.int_note
                                        }
                                        "диезыприключе" ->{
                                            choiceImg = R.drawable.sharp
                                        }
                                        "бемолиприключе" ->{
                                            vertChang = -0.05f
                                            choiceImg = R.drawable.bemol
                                        }
                                    }

                                    createSignView(binding, choiceImg, notesViewInLineList, i, vertBias = vertChang)
//                                    val noteView = ImageView(this.context)
//                                    noteView.id = View.generateViewId()
//                                    notesViewInLineList.add(noteView)
//                                    noteView.setImageResource(choiceImg)
//                                    binding.constraintLayout.addView(noteView)
//                                    noteView.layoutParams.height = 52
//                                    noteView.layoutParams.width = 52
//                                    val set = ConstraintSet()
//                                    set.clone(binding.constraintLayout)
//                                    set.connect(noteView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
//                                    set.connect(noteView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
//                                    set.connect(noteView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
//                                    set.connect(noteView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
//                                    set.setHorizontalBias(noteView.id, Signs.positionInStaveHorizont[i.second]!!)
//                                    set.setVerticalBias(noteView.id, Signs.positionInStaveVert[i.first]!! + vertChang)
//                                    set.applyTo(binding.constraintLayout)
                                }

                            }
                        })
                        

                        testFragmentViewModel.staticSignInStave.observe(viewLifecycleOwner, Observer { signTripleList ->
                            signTripleList?.let{
                                if(signTripleList.isEmpty()){
                                    for(i in staticNotesViewInLineList){
                                        binding.constraintLayout.removeView(i)
                                    }
                                }
                                for(i in signTripleList){
                                    var choiceImg = 0
                                    var vertChang = 0f
                                    when (i.third) {
                                        "диезы", "диез" -> choiceImg = R.drawable.sharp
                                        "бекар" -> choiceImg = R.drawable.bekar
                                        "бемоли", "бемоль" -> {
                                            choiceImg = R.drawable.bemol
                                            vertChang = -0.05f
                                        }
                                        "целая" -> choiceImg = R.drawable.int_note
                                        "дветерции" ->{
                                            choiceImg = R.drawable.int_note
                                        }
                                    }
                                    val noteView = ImageView(this.context)
                                    noteView.id = View.generateViewId()
                                    staticNotesViewInLineList.add(noteView)
                                    noteView.setImageResource(choiceImg)
                                    binding.constraintLayout.addView(noteView)
                                    noteView.layoutParams.height = 52
                                    noteView.layoutParams.width = 52
                                    val set = ConstraintSet()
                                    set.clone(binding.constraintLayout)
                                    set.connect(noteView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
                                    set.connect(noteView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
                                    set.connect(noteView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
                                    set.connect(noteView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
                                    set.setHorizontalBias(noteView.id, Signs.positionInStaveHorizont[i.second]!!)
                                    set.setVerticalBias(noteView.id, Signs.positionInStaveVert[i.first]!! + vertChang)
                                    set.applyTo(binding.constraintLayout)
                                }

                            }
                        })

                        binding.btnAnswer.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.signList.visibility = View.VISIBLE
                        binding.btnClear.visibility = View.VISIBLE
                        binding.groupStave.visibility = View.VISIBLE
                        binding.txtNumPick.text = ""
//                        binding.txtNumPick.text = "Твой ответ будет:"
                        testFragmentViewModel.setCurrentNumPick(0)
                    }
                    "buttons" -> {
                        observeForNumPick(binding, testFragmentViewModel, 0)
                        hideAll(binding)
                        binding.btnAns0.visibility = View.VISIBLE
                        binding.btnAns1.visibility = View.VISIBLE
                        binding.btnAns2.visibility = View.VISIBLE
                    }
                }
            }
            if (type == null) hideAll(binding)
        })


        testFragmentViewModel.navigateToResult.observe(viewLifecycleOwner, Observer { num ->
            num?.let {
                this.findNavController().navigate(
                    TestFragmentDirections
                        .actionTestFragmentToResultFragment()
                )
                testFragmentViewModel.doneNavigate()
            }
        })


        binding.signList.setOnClickListener {
            testFragmentViewModel.onClickRecView()
        }

        binding.btn2.setOnClickListener {
            this.findNavController()
                .navigate(TestFragmentDirections.actionTestFragmentToResultFragment())
        }




        return binding.root
    }

    private fun hideAll(binding: FragmentTestBinding) {
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
        binding.groupStave.visibility = View.GONE
    }



    private fun observeForNumPick(
        binding: FragmentTestBinding,
        testFragmentViewModel: TestFragmentViewModel,
        numOfNumPick: Int
    ) {
        when (numOfNumPick) {
            1 -> {
                binding.numberPicker.setOnValueChangedListener { _, _, newVal ->
                    var text = ""
                    if (TonalityTest.currentQuestionNum.value == 2) {
                        testFragmentViewModel.btnText.value?.get(newVal)?.let {
                            text = "\n (${Tonality.valueOf(it).rusName})"
                        }
                    } else text = ""
                    binding.txtNumPick.text =
                        "${testFragmentViewModel.btnText.value?.get(newVal)} $text"
//                    binding.txtNumPick.text =
//                        "Твой ответ будет: ${testFragmentViewModel.btnText.value?.get(newVal)} $text"
                    testFragmentViewModel.setCurrentNumPick(newVal)
                }
            }
            2 -> {
                var text = ""
                var text1 = ""
                var text2 = ""
                text1 = testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(0).toString()
                text2 = testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(0).toString()
                testFragmentViewModel.addInSignInStave(text1, text2)
                binding.numberPicker.setOnValueChangedListener { _, _, newVal ->
                    text1 = testFragmentViewModel.specificBtnTxt.value?.get(0)?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "$text"
//                    binding.txtNumPick.text =
//                        "Твой ответ будет: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                    testFragmentViewModel.addInSignInStave(text1, text2)
//                    updateSignsInLineList(text1, text2)
                }
                binding.numberPicker2.setOnValueChangedListener { _, _, newVal ->
                    text2 = testFragmentViewModel.specificBtnTxt.value?.get(1)?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "$text"
//                    binding.txtNumPick.text =
//                        "Твой ответ будет: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                    testFragmentViewModel.addInSignInStave(text1, text2)
//                    updateSignsInLineList(text1, text2)
                }
            }
            0 -> {

            }

        }
    }


    private fun createSignView(binding: FragmentTestBinding,
                               choicedImg: Int,
                               noteList: MutableList<ImageView>,
                               signTriple: Triple<Float, Float, String>,
                               vertBias: Float = 0f){
        val signView = ImageView(this.context)
        signView.id = View.generateViewId()
        noteList.add(signView)
        signView.setImageResource(choicedImg)
        binding.constraintLayout.addView(signView)
        signView.layoutParams.height = 52
        signView.layoutParams.width = 52
        var set = ConstraintSet()
        set.clone(binding.constraintLayout)
        set.connect(signView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
        set.connect(signView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
        set.connect(signView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
        set.connect(signView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
        set.setHorizontalBias(signView.id, Signs.positionHorizontalKeySignature[signTriple.second]!!)
        set.setVerticalBias(signView.id, Signs.positionInStaveVert[signTriple.first]!! + vertBias)
        set.applyTo(binding.constraintLayout)
    }

    private fun updateSignsInLineList(text1: String, text2: String) {
        Signs._signsInStave.value = mutableListOf()


        Signs._signsInStave.value?.add(
            Triple(
                Signs.noteInOrderInLines.get(text1),
                2f,
                "целая"
            ) as Triple<Float, Float, String>
        )
        Signs._signsInStave.value?.add(
            Triple(
                Signs.noteInOrderInLines.get(text1),
                1f,
                text2
            ) as Triple<Float, Float, String>
        )
        Signs._signsInStave.value = Signs._signsInStave.value
    }


}