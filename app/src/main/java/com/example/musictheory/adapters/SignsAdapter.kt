package com.example.musictheory.adapters

import android.graphics.Color
import android.text.TextUtils.indexOf
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.Activities.MainActivity
import com.example.musictheory.R
import com.example.musictheory.data.Signs
import com.example.musictheory.data.Tonality
import com.example.musictheory.databinding.ListItemSignsBinding

class SignsAdapter: RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

//    var data = mapOf<Int, String>()
    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item, position)
        ViewHolder
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder private constructor(val binding: ListItemSignsBinding) : RecyclerView.ViewHolder(binding.root){
        val btnName: Button = itemView.findViewById(R.id.btnSign)

        init{

        }

        fun bind(item: String, position: Int) {

            btnName.text = item
            btnName.setOnClickListener {
//                btnName.text = "qe"
                btnName.isEnabled = false
                Signs.addInList(item, position)
            }

            if (item == "Фа") {
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
