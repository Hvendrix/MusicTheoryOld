package com.example.musictheory.Fragments

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.musictheory.R
import com.example.musictheory.data.ConstsForTesting
import com.example.musictheory.data.tests.TonalityTest
import com.example.musictheory.data.tests.TrebleClefTest
import com.example.musictheory.data.tests.TritonTest
import com.example.musictheory.database.AnswerDatabase
import com.example.musictheory.database.TestForFirebase
import com.example.musictheory.database.userStateTesting
import com.example.musictheory.databinding.FragmentTitleBinding
import com.example.musictheory.models.TitleFragmentViewModel
import com.example.musictheory.models.TitleFragmentViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_title.*
import com.google.firebase.database.ValueEventListener as ValueEventListener1


class TitleFragment : Fragment() {

//    public lateinit var mAuth: FirebaseAuth
//    private lateinit var txt1: EditText
//    private lateinit var txt2: EditText
//    private lateinit var txtUser: View
//    private lateinit var btn2: Button
//    private lateinit var btn3: Button
//    private lateinit var btnExit: Button
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



        //FireBase module
        val USER_KEY = "User"
        val fBDb: DatabaseReference = FirebaseDatabase.getInstance().getReference(USER_KEY)


        binding.btnDB1.setOnClickListener {
            val id = fBDb.key ?: "0"
            val name = binding.editTextTextPersonName.text.toString()
            val pass = binding.editTextTextPersonName2.text.toString()
            val user = TestForFirebase(id, name, pass)
            fBDb.push().setValue(user)
        }

        binding.btnDB2.setOnClickListener {
            if(!binding.editTextTextPersonName.text.toString().isEmpty()
                && !binding.editTextTextPersonName2.text.toString().isEmpty()) {
                userStateTesting.mAuth.createUserWithEmailAndPassword(binding.editTextTextPersonName.text.toString(),
                binding.editTextTextPersonName2.text.toString()).addOnCompleteListener(
                    OnCompleteListener{
                        if(it.isSuccessful()){
                            Toast.makeText(context, "Регистрация успешна", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(context, "ВНИМАНИЕ!!! Регистрация не прошла успешно!", Toast.LENGTH_SHORT).show()
                        }
                    })

            }
        }

//        binding.btnDB2.setOnClickListener {
//            mAuth.sign
//        }


        binding.btnDB3.setOnClickListener {
            if(!binding.editTextTextPersonName.text.toString().isEmpty()
                && !binding.editTextTextPersonName2.text.toString().isEmpty()) {
                userStateTesting.mAuth.signInWithEmailAndPassword(binding.editTextTextPersonName.text.toString(),
                    binding.editTextTextPersonName2.text.toString()).addOnCompleteListener(
                    OnCompleteListener {
                        if(it.isSuccessful()){
                            editTextTextPersonName.visibility = View.GONE
                            editTextTextPersonName2.visibility = View.GONE
                            btnDB2.visibility = View.GONE
                            btnDB3.visibility = View.GONE
                            btnDBExit.visibility = View.VISIBLE
                            txtUserName.visibility = View.VISIBLE
                            val cUser = userStateTesting.mAuth.currentUser
                            var userName = "Вы вошли как : " + cUser?.email
                            txtUserName.setText(userName)
                            Toast.makeText(context, "${userName}", Toast.LENGTH_SHORT).show()
                        } else{
                            Toast.makeText(context, "Ошибка при входе", Toast.LENGTH_SHORT).show()
                        }
                    }
                )

            }
        }

    binding.btnDBExit.setOnClickListener {
        FirebaseAuth.getInstance().signOut()
        editTextTextPersonName.visibility = View.VISIBLE
        editTextTextPersonName2.visibility = View.VISIBLE
        btnDB2.visibility = View.VISIBLE
        btnDB3.visibility = View.VISIBLE
        btnDBExit.visibility = View.GONE
        txtUserName.visibility = View.GONE
    }



        val listFromDb: List<String> = arrayListOf()
        val arrayAdapter : ArrayAdapter<String> = ArrayAdapter(context!!, android.R.layout.simple_list_item_1, listFromDb)
        binding.listView.adapter = arrayAdapter


        userStateTesting.mAuth = FirebaseAuth.getInstance()



        return binding.root
    }

    override fun onStart() {
        super.onStart()
        val cUser = userStateTesting.mAuth.currentUser

        if(cUser != null){
            editTextTextPersonName.visibility = View.GONE
            editTextTextPersonName2.visibility = View.GONE
            btnDB2.visibility = View.GONE
            btnDB3.visibility = View.GONE
            var userName = "Вы вошли как : " + cUser.email
            txtUserName.setText(userName)

            Toast.makeText(context, "$userName", Toast.LENGTH_SHORT).show()
        }else{
            btnDBExit.visibility = View.GONE
            txtUserName.visibility = View.GONE
//            Toast.makeText(context, "User null", Toast.LENGTH_SHORT).show()

        }
    }

//    private fun getDataFromDB(fbDb: DatabaseReference){
//        var  vListener =  fbDb.addValueEventListener()
//        }
//    }




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