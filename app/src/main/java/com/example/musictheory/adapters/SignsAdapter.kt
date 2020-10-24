package com.example.musictheory.adapters

import android.graphics.Color
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.Activities.MainActivity
import com.example.musictheory.R
import com.example.musictheory.data.Signs
import com.example.musictheory.data.Tonality
import com.example.musictheory.databinding.ListItemSignsBinding

class SignsAdapter: ListAdapter<Signs.Sign, SignsAdapter.ViewHolder>(SignsDiffCallback()) {

//    var data = mapOf<Int, String>()
//    var data = mutableListOf<Signs.Sign>()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//
//    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        val item = data[position]
        val item = getItem(position)
        holder.bind(item, position)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemSignsBinding) : RecyclerView.ViewHolder(binding.root){
        val btnName: Button = itemView.findViewById(R.id.btnSign)


        fun bind(item: Signs.Sign, position: Int) {

            btnName.text = item.Name.toString()
            btnName.setOnClickListener {
//                btnName.text = "qe"
                btnName.isEnabled = false
                Signs.addInList(item.Name.toString(), position)
            }

            if(!Signs.testBool){
                btnName.isEnabled = true
            }
            if (item.Name == "Фа") {
                btnName.setTextColor(Color.RED) // red

            } else {
                // reset
                btnName.setTextColor(Color.BLACK) // black
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
class SignsDiffCallback : DiffUtil.ItemCallback<Signs.Sign>() {
    override fun areItemsTheSame(oldItem: Signs.Sign, newItem: Signs.Sign): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Signs.Sign, newItem: Signs.Sign): Boolean {
        return oldItem.Name == newItem.Name

    }
}