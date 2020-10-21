package com.example.musictheory.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.example.musictheory.R
import com.example.musictheory.TextItemViewHolder
import android.widget.TextView

class SignsAdapter: RecyclerView.Adapter<SignsAdapter.ViewHolder>() {

    var data = listOf<String>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }


    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.btnName.text = item

        if (item == "Фа") {
            holder.btnName.setTextColor(Color.RED) // red
        } else {
            // reset
            holder.btnName.setTextColor(Color.BLACK) // black
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.text_item_signs, parent, false)
        return ViewHolder(view)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val btnName: Button = itemView.findViewById(R.id.btnSign)
    }
}