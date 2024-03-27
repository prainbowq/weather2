package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import androidx.room.Room
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.FragmentBlankBinding

class WeatherPage(val index: Int) : Fragment() {
    private val binding by lazy { FragmentBlankBinding.inflate(layoutInflater) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val weatherdao = Room.databaseBuilder(
            requireContext(),
            WeatherDb::class.java,
            "weather.db"
        ).allowMainThreadQueries().build()
        var min: MutableList<String> = mutableListOf()
        var max: MutableList<String> = mutableListOf()
        var hourTemp: MutableList<String> = mutableListOf()
        var weatherTalk: MutableList<String> = mutableListOf()
        var cloud: MutableList<String> = mutableListOf()
        var day: MutableList<String> = mutableListOf()

        binding.city.text = weatherdao.dao.getAll()[index].city
        min = weatherdao.dao.getAll()[index].minTemp.split(",") as MutableList<String>
        max = weatherdao.dao.getAll()[index].maxTemp.split(",") as MutableList<String>
        hourTemp = weatherdao.dao.getAll()[index].hourTemp.split(",") as MutableList<String>
        weatherTalk = weatherdao.dao.getAll()[index].weatherTalk.split(",") as MutableList<String>
        cloud = weatherdao.dao.getAll()[index].cloud.split(",") as MutableList<String>
        day = weatherdao.dao.getAll()[index].day.split(",") as MutableList<String>
        binding.cloud.text = cloud[0]
        binding.max.text = max[0]
        binding.min.text = min[0]
        binding.temp.text = hourTemp[0]
        binding.rectangles.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rectangles.adapter = WeatherList(hourTemp, cloud, day)
        binding.weatherList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.weatherList.adapter = WeatherList2(max, min, cloud, cloud)

        return binding.root
    }
}