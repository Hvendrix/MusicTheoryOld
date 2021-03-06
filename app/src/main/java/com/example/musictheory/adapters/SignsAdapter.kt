package com.example.musictheory.adapters

import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.R
import com.example.musictheory.data.InterfaceTypes
import com.example.musictheory.data.Notes
import com.example.musictheory.data.Signs
import com.example.musictheory.databinding.ListItemSignsBinding
import com.example.musictheory.models.TestFragmentViewModel

class SignsAdapter: RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

    var data = mutableListOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.i("ttt", "notifydata")
        }

    var data2 = mutableListOf<Int>()
        set(value) {
            field = value
            notifyDataSetChanged()
            Log.i("ttt", "notifydata2")
        }


    lateinit var viewModel : TestFragmentViewModel

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = data[position]
        val item2 = data2[position]
        val viewModel = viewModel
        holder.bind(item, position, item2, viewModel)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemSignsBinding) : RecyclerView.ViewHolder(binding.root){
        private val btnName: Button = itemView.findViewById(R.id.btnSign)


        fun bind(
            item: String,
            position: Int,
            item2: Int,
            viewModel: TestFragmentViewModel
        ) {
            btnName.text = item
            btnName.setBackgroundResource(R.drawable.bg_btn_basic)

            btnName.setOnClickListener {
                if(viewModel.interfaceType.value==InterfaceTypes.Buttons || viewModel.interfaceType.value==InterfaceTypes.ButtonsWithStave){
                    viewModel.onClickAnswer(position)
                    return@setOnClickListener
                }
                if(viewModel.interfaceType.value==InterfaceTypes.NumPickWithoutStave){
                    Signs.listDataEnabled.value?.set(position, 0)
                    when (item2) {
                        1 -> {
                            btnName.isEnabled = true
//                    btnName.setBackgroundColor(Color.GRAY)
//                    btnName.setBackgroundColor()
//                    btnName.setBackgroundColor(binding.root.resources.getColor(R.color.btnsColorPressed));
                        }
                        0 -> {
                            btnName.isEnabled = false
                        }
                        else -> {
                            btnName.text = "?????? ???? ???? ??????"
                            btnName.setBackgroundColor(Color.LTGRAY)
                        }
                    }
//                    viewModel.onClickAnswer(0)
                    return@setOnClickListener
                }
                Signs.addInList(item, position)


//                val signType = Signs.currentSignTypeInSigns[0].toLowerCase()
                val signType = viewModel.currentSignType.value?.get(0).toString().toLowerCase()
//                var signType = "??????????"
                val numInRange = Signs.listInOrder.size.toFloat()

//                Signs._signsInStave.value?.add(Triple(Signs.noteInOrderInLines.get(item), numInRange, signType) as Triple<Float, Float, String>)
//                Signs._signsInStave.value = Signs._signsInStave.value


                // ??????????????(
                var signNamePlusOct = item
                if(item!="????" && item!="????" && item!="????" && item!="????") {
                    when (signType) {
                        "??????????????????????????", "????????????????????????????" -> signNamePlusOct = item + "2"
                        "????????????????????????????" -> {
                            if(viewModel.signInStave.value?.lastOrNull() != null &&
                                (viewModel.signInStave.value?.last()!!.first > Signs.noteInOrderInLines[item] ?: error(""))){
                                signNamePlusOct = item + "2"
                            }
                        }
                        "????????????????????", "??????????????????" -> {
                            if(viewModel.signInStave.value?.lastOrNull() != null &&
                                (viewModel.signInStave.value?.size!! % 2==1) &&
                                (viewModel.signInStave.value?.last()!!.first > Signs.noteInOrderInLines[item] ?: error(""))){
                                signNamePlusOct = item + "2"
                            } else if(item=="????"|| item=="????") {
                                signNamePlusOct = item + "2"
                            }
                        }
                    }
                }

//

                viewModel.signInStave.value?.add(Triple(Signs.noteInOrderInLines.get(signNamePlusOct), numInRange, signType) as Triple<Float, Float, String>)
                viewModel.updateStave()
                Log.i("xxx", "${viewModel.signInStave.value?.get(0)}")
//                viewModel.signInStave.value = viewModel.signInStave.value


//                for((x, i) in Signs._signsInStave.value!!.withIndex()){
//                    Log.i("ttt", "Triple in Adapter ${i.first} $x name is $item")
//                }
//                for((x, i) in viewModel.signInStave.value!!.withIndex()){
//                    Log.i("ttt", "Triple in Adapter ${i.first} $x name is $item")
//                }
//                Log.i("ttt", "${Signs._signsInStave.value?.get(0)?.third}")
                Signs.listEnabled[position] = 0
//                Log.i("xxx", "current sign type = $signType")
                if(signType!=("????????????????????") && signType!=("??????????????????")){
                    Signs.listDataEnabled.value?.set(position, 0)
                    btnName.setBackgroundColor(Color.LTGRAY)
                    btnName.isEnabled = false

                }

            }
            when (item2) {
                1 -> {
                    btnName.isEnabled = true
//                    btnName.setBackgroundColor(Color.GRAY)
//                    btnName.setBackgroundColor()
//                    btnName.setBackgroundColor(binding.root.resources.getColor(R.color.btnsColorPressed));
                }
                0 -> {
                    btnName.isEnabled = false
                }
                else -> {
                    btnName.text = "?????? ???? ???? ??????"
                    btnName.setBackgroundColor(Color.LTGRAY)
                }
            }
        }




        companion object{

            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
//                val view = layoutInflater.inflate(R.layout.text_item_signs, parent, false)
                val binding = ListItemSignsBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }

        }

    }
}




//class SignsListener(val clickListener: (sleepId: Long) -> Unit) {
//    fun onClick(signs: Signs) = clickListener(night.listData.indexOf())
//class SignsDiffCallback : DiffUtil.ItemCallback<Signs.Sign>() {
//    override fun areItemsTheSame(oldItem: Signs.Sign, newItem: Signs.Sign): Boolean {
//        return oldItem.id == newItem.id
//    }
//
//    override fun areContentsTheSame(oldItem: Signs.Sign, newItem: Signs.Sign): Boolean {
//        return oldItem.Name == newItem.Name
//
//    }
//}