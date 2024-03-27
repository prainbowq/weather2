package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class WeatherList2(
    val max: MutableList<String>,
    val min: MutableList<String>,
    val cloudList: MutableList<String>,
    val talk: MutableList<String>
) :
    RecyclerView.Adapter<WeatherList2.Page>() {
    inner class Page(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val max: TextView = itemView.findViewById(R.id.max3)
        val min: TextView = itemView.findViewById(R.id.min3)
        val img12: ImageView = itemView.findViewById(R.id.img3)
        val talk: TextView = itemView.findViewById(R.id.talk)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Page {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weatherlist2, null)
        return Page(view)
    }

    override fun onBindViewHolder(holder: Page, position: Int) {
        holder.min.text = min[position]
        holder.max.text = max[position]
        holder.talk.text = talk[position]

        if (cloudList[position].contains("雨")) {
            holder.img12.setImageResource(R.drawable.raining)
        } else if (cloudList[position].contains("雲")) {
            holder.img12.setImageResource(R.drawable.cloudy)
        } else {
            holder.img12.setImageResource(R.drawable.sun)
        }

    }

    override fun getItemCount(): Int {
        return max.size
    }

}