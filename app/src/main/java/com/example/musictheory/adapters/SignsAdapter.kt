package com.example.musictheory.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.R

class SignsAdapter: RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val btnName: Button = itemView.findViewById(R.id.btnSign)

        fun bind(item: String) {
            btnName.text = item

            if (item == "Фа") {
                btnName.setTextColor(Color.RED) // red
                btnName.isEnabled = false
            } else {
                // reset
                btnName.setTextColor(Color.BLACK) // black
            }
        }

        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.text_item_signs, parent, false)
                return ViewHolder(view)
            }
        }

    }
}
