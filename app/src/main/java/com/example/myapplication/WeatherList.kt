package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class WeatherList(
    val hourTampList: MutableList<String>,
    val cloudList: MutableList<String>,
    val day: MutableList<String>
) :
    RecyclerView.Adapter<WeatherList.Page>() {
    inner class Page(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val temp: TextView = itemView.findViewById(R.id.temp2)
        val img12: ImageView = itemView.findViewById(R.id.ccloudImag)
        val time2: TextView = itemView.findViewById(R.id.time2)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Page {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weatherlist, null)
        return Page(view)
    }


    override fun getItemCount(): Int {
        return hourTampList.size
    }

    override fun onBindViewHolder(holder: Page, position: Int) {
        holder.temp.text = hourTampList[position]
        if (cloudList[position].contains("雨")) {
            holder.img12.setImageResource(R.drawable.raining)
        } else if (cloudList[position].contains("雲")) {
            holder.img12.setImageResource(R.drawable.cloudy)
        } else {
            holder.img12.setImageResource(R.drawable.sun)
        }
        holder.time2.text = day[position]

    }
}