package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivitySearchBinding
import org.json.JSONArray

class Search : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: MainActivity.Page
    private var pageCount = 0;
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val assets = assets.open("city.json").bufferedReader().use { it.readText() }
        val json = JSONArray(assets)
        val city: MutableList<String> = mutableListOf()
        for (i in 0 until json.length()) {
            city.add(json.getJSONObject(i).getString("City"))
        }
        binding.sl.adapter = searchList(city, this)
        binding.search.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val newcity: MutableList<String> = mutableListOf()
                for (i in 0 until city.size) {
                    if (city[i].contains(p0.toString())) {
                        newcity.add(city[i])

                    }
                }
                binding.sl.adapter = searchList(newcity, this@Search)
            }


            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}