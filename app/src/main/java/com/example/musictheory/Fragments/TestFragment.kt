package com.example.musictheory.Fragments

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musictheory.Activities.MainActivity
import com.example.musictheory.R
import com.example.musictheory.data.InterfaceTypes
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



        setHasOptionsMenu(true)


        val manager = GridLayoutManager(activity, 4)
        binding.signList.layoutManager = manager


//        val adapter = SignsAdapter()
        val adapter = testFragmentViewModel.adapter

        binding.signList.adapter = adapter

        adapter.viewModel = testFragmentViewModel



//        binding.signList.addItemDecoration(DividerItemDecoration(context, 48))
//        binding.signList.addItemDecoration(SignItemDecoration(489))
//        binding.signList.addItemDecoration(DividerItemDecoration(context, GridLayoutManager.VERTICAL))


//        Signs.signList.observe(viewLifecycleOwner, Observer {
//            adapter.viewModel = testFragmentViewModel
//            adapter.data = it
//            adapter.notifyDataSetChanged()
//        })
//
//
//        testFragmentViewModel.btnText.observe(viewLifecycleOwner, Observer {
//            adapter.data = it.toMutableList()
//            adapter.notifyDataSetChanged()
//        })
//
//        testFragmentViewModel.currentSignType.observe(viewLifecycleOwner, Observer {
//            Log.i("xxx", "observ done")
//            Signs.currentSignTypeInSigns = it
//            Log.i("xxx", "${Signs.currentSignTypeInSigns[0]}")
//        })


        Signs.listDataEnabled.observe(viewLifecycleOwner, Observer {
            adapter.data2 = it
            adapter.notifyDataSetChanged()
        })


        Signs.TestString.observe(viewLifecycleOwner, Observer {
//            binding.txtNumPick.text = "${it}"
//            binding.txtNumPick.text = "???????? ?????????? ??????????: ${it}"
            binding.txtNumPick.text = ""
        })

        binding.btnClear.setOnClickListener {
            testFragmentViewModel.onClearRecView(adapter)
        }




        testFragmentViewModel.onClearRecView(adapter)



        binding.btnTxtTonFirst.setOnClickListener {
            Signs.getOne() ?: Log.i("xxx", "something wrong xx = ${Signs.x1.value}   ${Signs.x2}")
            Signs.x2++
            Log.i("xxx", "values xx = ${Signs.x1.value}   ${Signs.x2}")
            Toast.makeText(context,  "${testFragmentViewModel.currentTonality.value?.rusName ?: ""}", Toast.LENGTH_SHORT).show()
        }


        hideAll(binding)
//        binding.numberPicker.minValue = 0
//        binding.numberPicker.maxValue = testFragmentViewModel.btnText.value?.size?.minus(1) ?: 0
//        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value

        val notesViewInLineList : MutableList<ImageView> = mutableListOf()
        val staticNotesViewInLineList : MutableList<ImageView> = mutableListOf()


        observeSignForView(testFragmentViewModel, notesViewInLineList, binding, testFragmentViewModel.signInStave)
        observeSignForView(testFragmentViewModel, staticNotesViewInLineList, binding, testFragmentViewModel.staticSignInStave)

        testFragmentViewModel.interfaceType.observe(viewLifecycleOwner, Observer{ type ->
            type?.let {
                adapter.notifyDataSetChanged()
                when (type) {
                    InterfaceTypes.NumPick -> {
                        binding.numberPicker.maxValue = 0
                        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value?.btnTextList?.toTypedArray()
                        binding.numberPicker.maxValue =
                            testFragmentViewModel.btnText.value?.btnTextList?.size?.minus(1) ?: 1
                        binding.txtNumPick.text = "${testFragmentViewModel.btnText.value?.btnTextList?.get(0)}"
//                        binding.txtNumPick.text = "???????? ?????????? ??????????: ${testFragmentViewModel.btnText.value?.get(0)}"
                        observeForNumPick(binding, testFragmentViewModel, 1)
                        testFragmentViewModel.setCurrentNumPick(0)
                        hideAll(binding)
                        binding.numberPicker.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.btnAnswer.visibility = View.VISIBLE
                    }
                    InterfaceTypes.TwoNumPick-> {

                        hideAll(binding)
                        observeForNumPick(binding, testFragmentViewModel, 2)
                        binding.numberPicker.maxValue = 0
                        binding.numberPicker.displayedValues =
                            testFragmentViewModel.btnText.value?.btnTextList?.toTypedArray()
                        binding.numberPicker.maxValue =
                            testFragmentViewModel.btnText.value?.btnTextList?.size?.minus(1) ?: 0
                        binding.numberPicker2.maxValue = 0
                        binding.numberPicker2.displayedValues =
                            testFragmentViewModel.btnText.value?.btnTextList2?.toTypedArray()
                        binding.numberPicker2.maxValue =
                            testFragmentViewModel.btnText.value?.btnTextList2?.size?.minus(1) ?: 0

                        //????????
                        testFragmentViewModel.setCurrentNumPick(0)
                        val text = "${testFragmentViewModel.btnText.value?.btnTextList?.get(0).toString()}-${testFragmentViewModel.btnText.value?.btnTextList2?.get(0).toString()}"
                        binding.txtNumPick.text = "${text}"
//                        binding.txtNumPick.text = "???????? ?????????? ??????????: ${text}"
                        testFragmentViewModel.setCurrentAnswer(text)

                        //?????????? ???????? ????????, ???? ???? ??????????????????
                        binding.numberPicker.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.btnAnswer.visibility = View.VISIBLE
                        binding.numberPicker2.visibility = View.VISIBLE
                        binding.groupStave.visibility = View.VISIBLE


//                        observeSignForView(testFragmentViewModel, notesViewInLineList, binding, testFragmentViewModel.signInStave)


                    }
                    InterfaceTypes.Table-> {

                        adapter.data = testFragmentViewModel.btnText.value?.btnTextList?: mutableListOf()
                        adapter.notifyDataSetChanged()
                        hideAll(binding)

//                        observeSignForView(testFragmentViewModel, notesViewInLineList, binding, testFragmentViewModel.signInStave)
//                        observeSignForView(testFragmentViewModel, staticNotesViewInLineList, binding, testFragmentViewModel.staticSignInStave)


//                        binding.btnAnswer.visibility = View.VISIBLE
//                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.signList.visibility = View.VISIBLE
//                        binding.btnClear.visibility = View.VISIBLE
//                        binding.groupStave.visibility = View.VISIBLE
//                        binding.txtNumPick.text = ""
//                        binding.txtNumPick.text = "???????? ?????????? ??????????:"
                        testFragmentViewModel.setCurrentNumPick(0)
                    }
                    InterfaceTypes.TableWithAnsBtn ->{
                        adapter.data = testFragmentViewModel.btnText.value?.btnTextList?: mutableListOf()
                        adapter.notifyDataSetChanged()
                        hideAll(binding)

                        // ?????????????????????? ?????????? ???????????????????????????? ?????? ?????????????????????? ???????????? ???? ???????????? ????????????????????, ?? ?????? ?????????? ????????(
                        observeSignForView(testFragmentViewModel, notesViewInLineList, binding, testFragmentViewModel.signInStave)
                        observeSignForView(testFragmentViewModel, staticNotesViewInLineList, binding, testFragmentViewModel.staticSignInStave)


                        binding.btnAnswer.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.signList.visibility = View.VISIBLE
                        binding.btnClear.visibility = View.VISIBLE
                        binding.groupStave.visibility = View.VISIBLE
                        binding.txtNumPick.text = ""
                        testFragmentViewModel.setCurrentNumPick(0)
                    }
                    InterfaceTypes.NumPickWithoutStave ->{
                        binding.numberPicker.maxValue = 0
                        binding.numberPicker.displayedValues = testFragmentViewModel.btnText.value?.btnTextList?.toTypedArray()
                        binding.numberPicker.maxValue =
                            testFragmentViewModel.btnText.value?.btnTextList?.size?.minus(1) ?: 1

                        //??????????????
                        var text = "${testFragmentViewModel.btnText.value?.btnTextList?.get(0)}"
                        if (TonalityTest.currentQuestionNum.value == 2) {
                            testFragmentViewModel.btnText.value?.btnTextList?.get(0)?.let {
                                text += " (${Tonality.valueOf(it).rusName})"
                            }
                        }
                        binding.txtNumPick.text = "${text}"
                        observeForNumPick(binding, testFragmentViewModel, 1)
                        testFragmentViewModel.setCurrentNumPick(0)
                        hideAll(binding)
                        binding.numberPicker.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.btnAnswer.visibility = View.VISIBLE

                    }
                    InterfaceTypes.ButtonsWithStave -> {
                        adapter.data = testFragmentViewModel.btnText.value?.btnTextList?: mutableListOf()
                        adapter.notifyDataSetChanged()
                        hideAll(binding)
                        binding.signList.visibility = View.VISIBLE
                        testFragmentViewModel.setCurrentNumPick(0)


                        observeSignForView(testFragmentViewModel, notesViewInLineList, binding, testFragmentViewModel.signInStave)
                        observeSignForView(testFragmentViewModel, staticNotesViewInLineList, binding, testFragmentViewModel.staticSignInStave)


//                        binding.btnAnswer.visibility = View.VISIBLE
                        binding.txtNumPick.visibility = View.VISIBLE
                        binding.signList.visibility = View.VISIBLE
//                        binding.btnClear.visibility = View.VISIBLE
                        binding.groupStave.visibility = View.VISIBLE
                        binding.txtNumPick.text = ""
                        testFragmentViewModel.setCurrentNumPick(0)
                    }
                    InterfaceTypes.Buttons ->{
                        adapter.data = testFragmentViewModel.btnText.value?.btnTextList?: mutableListOf()
                        adapter.notifyDataSetChanged()
                        hideAll(binding)
                        binding.signList.visibility = View.VISIBLE
                        testFragmentViewModel.setCurrentNumPick(0)
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


//        binding.signList.setOnClickListener {
//            testFragmentViewModel.onClickRecView()
//        }

        binding.btn2.setOnClickListener {
            this.findNavController()
                .navigate(TestFragmentDirections.actionTestFragmentToResultFragment())
        }


        binding.btnHelp.setOnClickListener {
            toHelpFragment(testFragmentViewModel)
        }


        // ?????????????? ??????????????
        val activity = activity as MainActivity
        hideSoftKeyboard(activity)


        return binding.root
    }


    //????????????????
    fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus?.windowToken, 0
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.overflow_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(context, "$item ${view!!.findNavController()}", Toast.LENGTH_SHORT).show()

        return super.onOptionsItemSelected(item)
//        return NavigationUI.onNavDestinationSelected(item!!,
//        view!!.findNavController())
//                || super.onOptionsItemSelected(item)



    }

    fun toHelpFragment(viewModel: TestFragmentViewModel){
        this.findNavController()
            .navigate(TestFragmentDirections.actionTestFragmentToTheoryFragment(viewModel.currentTest.value?.javaClass?.simpleName.toString() ?: "nothing"))
    }

//    override fun onStart() {
//        super.onStart()
//        val cUser = userStateTesting.mAuth.currentUser
//
//        if(cUser != null){
//            val USER_KEY = "User"
//            val fBDb: DatabaseReference = FirebaseDatabase.getInstance().getReference(USER_KEY)
//            var userName = "???? ?????????? ?????? : " + cUser.email
//            Toast.makeText(context, "$userName", Toast.LENGTH_SHORT).show()
//            val id = fBDb.key ?: "0"
//            val name = "zzzzz"
//            val pass = "pppppp"
//            val user = TestForFirebase(id, name, pass)
//            fBDb.push().setValue(user)
////            txtUserName.setText(userName)
//        }else{
//            Toast.makeText(context, "User null", Toast.LENGTH_SHORT).show()
//
//        }
//    }





    private fun hideAll(binding: FragmentTestBinding) {
//        binding.btnAns0.visibility = View.GONE
//        binding.btnAns1.visibility = View.GONE
//        binding.btnAns2.visibility = View.GONE
        binding.numberPicker.visibility = View.GONE
        binding.numberPicker2.visibility = View.GONE
//        binding.txtNumPick.visibility = View.GONE
        binding.txtNumPick.visibility = View.INVISIBLE
        binding.btnAnswer.visibility = View.INVISIBLE
        binding.signList.visibility = View.GONE
        binding.btnClear.visibility = View.INVISIBLE

        //next must be invisible
        binding.groupStave.visibility = View.INVISIBLE
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
                        testFragmentViewModel.btnText.value?.btnTextList?.get(newVal)?.let {
                            text = " (${Tonality.valueOf(it).rusName})"
                        }
                    } else text = ""
                    binding.txtNumPick.text =
                        "${testFragmentViewModel.btnText.value?.btnTextList?.get(newVal)} $text"
//                    binding.txtNumPick.text =
//                        "${testFragmentViewModel.btnText.value?.btnTextList?.get(newVal)}"
//                    binding.txtNumPick.text =
//                        "???????? ?????????? ??????????: ${testFragmentViewModel.btnText.value?.get(newVal)} $text"
                    testFragmentViewModel.setCurrentNumPick(newVal)

//                    testFragmentViewModel.setCurrentAnswer(testFragmentViewModel.btnText.value?.btnTextList?.get(newVal)?: "null")
                }
            }
            2 -> {
                var text = ""
                var text1 = ""
                var text2 = ""
                text1 = testFragmentViewModel.btnText.value?.btnTextList?.get(0).toString()
                text2 = testFragmentViewModel.btnText.value?.btnTextList2?.get(0).toString()
                testFragmentViewModel.addInSignInStave(text1, text2)
                binding.numberPicker.setOnValueChangedListener { _, _, newVal ->
                    text1 = testFragmentViewModel.btnText.value?.btnTextList?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "$text"
//                    binding.txtNumPick.text =
//                        "???????? ?????????? ??????????: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                    testFragmentViewModel.addInSignInStave(text1, text2)
//                    updateSignsInLineList(text1, text2)
                }
                binding.numberPicker2.setOnValueChangedListener { _, _, newVal ->
                    text2 = testFragmentViewModel.btnText.value?.btnTextList2?.get(newVal).toString()
                    text = "$text1-$text2"
                    binding.txtNumPick.text =
                        "$text"
//                    binding.txtNumPick.text =
//                        "???????? ?????????? ??????????: $text"
                    testFragmentViewModel.setCurrentAnswer(text)
                    testFragmentViewModel.addInSignInStave(text1, text2)
//                    updateSignsInLineList(text1, text2)
                }

            }
            0 -> {

            }

        }
    }

    private fun observeSignForView(
        testFragmentViewModel: TestFragmentViewModel,
        notesViewInLineList: MutableList<ImageView>,
        binding: FragmentTestBinding,
        signList: LiveData<MutableList<Triple<Float, Float, String>>>
    ) {
        signList.observe(viewLifecycleOwner, Observer { signTripleList ->

            signTripleList?.let {

                if (signTripleList.isEmpty()) {
                    for (i in notesViewInLineList) {
                        //                        binding.constraintLayout.removeViewAt(i)
                        binding.constraintLayout.removeView(i)
                    }
                }
                for (i in notesViewInLineList) {
                    //                        binding.constraintLayout.removeViewAt(i)
                    binding.constraintLayout.removeView(i)
                }
                // ???? ???? ???????? ???? ???? ???? ???? ????????????
                for (i in signTripleList) {
//
                    var choiceImg = 0
                    var vertChang = 0f
                    var horChang = 0f
                    var heightSize = 52
                    var widthSize = 52
                    var horPos = Signs.positionInStaveHorizont[i.second] ?: 0f
                    var vertPos = Signs.positionInStaveVert[i.first] ?: 0f
                    when (i.third) {
                        "??????????", "????????" -> choiceImg = R.drawable.sharp
                        "??????????" -> choiceImg = R.drawable.bekar
                        "????????????", "????????????" -> {
                            choiceImg = R.drawable.bemol
                            vertChang = -0.06f

                        }
                        "??????????" -> {
                            choiceImg = R.drawable.int_note
//                            vertChang = -0.005f
//                            heightSize = 31
//                            widthSize = 31
                        }
                        "????????????????????????????" -> {
                            choiceImg = R.drawable.int_note
//                            vertChang = -0.005f
                            horPos = 0.115f
                            horChang = staveSignBias(testFragmentViewModel)
//                            heightSize = 31
//                            widthSize = 31
                        }
                        "??????????????????" -> {
                            choiceImg = R.drawable.int_note
//                            heightSize = 31
//                            widthSize = 31
                        }
                        "????????????????????" -> {
                            choiceImg = R.drawable.int_note
//                            vertChang = -0.005f
                            horPos = Signs.positionInStaveHorTwoBarTwoPart[i.second] ?: 0f
                            horChang = staveSignBias(testFragmentViewModel)
//                            heightSize = 31
//                            widthSize = 31
                        }
                        "??????????????????" -> {
                            choiceImg = R.drawable.int_note
//                            vertChang = -0.005f
                            horPos = Signs.positionInStaveHorTwoBarTwoPart[i.second] ?: 0f
                            horPos += 0.100f
                            horChang = staveSignBias(testFragmentViewModel)
//                            heightSize = 31
//                            widthSize = 31
                        }
                        "??????????????????????????" -> {
                            choiceImg = R.drawable.sharp
                            horPos = Signs.positionHorizontalKeySignature[i.second] ?: 0f
                        }
                        "????????????????????????????" -> {
                            choiceImg = R.drawable.bemol
                            horPos = Signs.positionHorizontalKeySignature[i.second] ?: 0f
                            vertChang = -0.06f
                        }
                        "??????????????????????????" ->{
                            choiceImg = R.drawable.bar
                            horChang = staveSignBias(testFragmentViewModel)
                            horPos = 0.500f
                            vertPos = 0.5f
                            heightSize = 100
                            widthSize = 83
                        }
                    }
                    createSignView(binding, choiceImg, notesViewInLineList, i, heightSize,  widthSize, horPos, vertPos, vertChang, horChang)

//                    if(i.first<0){
//                        var choiceImg2 = R.drawable.additional_line
//                        createSignView(binding, choiceImg2, notesViewInLineList, i, 1,  10, horPos, vertPos, vertChang, horChang)
//                    }

                }

            }
        })
    }

    private fun createSignView(
        binding: FragmentTestBinding,
        choicedImg: Int,
        noteList: MutableList<ImageView>,
        signTriple: Triple<Float, Float, String>,
        heightSize: Int,
        widthSize: Int,
        horPos: Float,
        vertPos: Float,
        vertBias: Float,
        horChang: Float
    ){
        //???????????? ???? ?????????????????????? ???? ?????????????????? numPick

        val signView = ImageView(this.context)
        signView.id = View.generateViewId()
        noteList.add(signView)
        signView.setImageResource(choicedImg)
        binding.constraintLayout.addView(signView)
        signView.layoutParams.height = heightSize
        signView.layoutParams.width = widthSize
        var set = ConstraintSet()
        set.clone(binding.constraintLayout)
        set.connect(signView.id, ConstraintSet.LEFT, binding.imgStave.id, ConstraintSet.LEFT)
        set.connect(signView.id, ConstraintSet.RIGHT, binding.imgStave.id, ConstraintSet.RIGHT)
        set.connect(signView.id, ConstraintSet.TOP, binding.imgStave.id, ConstraintSet.TOP)
        set.connect(signView.id, ConstraintSet.BOTTOM, binding.imgStave.id, ConstraintSet.BOTTOM)
        set.setHorizontalBias(signView.id, horPos + horChang)
        set.setVerticalBias(signView.id, vertPos + vertBias)
        set.applyTo(binding.constraintLayout)
        Log.i("xxx", "?????????????????? ???????? ${horPos}, $vertPos, $choicedImg, ")
    }


    //???????????? ???? ????????????????????????
    private fun updateSignsInLineList(text1: String, text2: String) {
        Signs._signsInStave.value = mutableListOf()


        Signs._signsInStave.value?.add(
            Triple(
                Signs.noteInOrderInLines.get(text1),
                2f,
                "??????????"
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


    //?????????????? ?? ?????????????????????? ???? ???????????????????? ???????????? ?????? ??????????
    private fun staveSignBias(viewModel: TestFragmentViewModel):Float{
        var x = viewModel.currentTonality.value?.signCount ?: 0
        return x*0.035f
    }

}