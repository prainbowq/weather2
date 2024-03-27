package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.room.Room
import androidx.viewpager.widget.ViewPager
import com.example.myapplication.databinding.ActivityMainBinding
import org.json.JSONArray
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager
    private lateinit var adapter: Page
    private var pageCount = 0;
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        window?.decorView?.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_IMMERSIVE or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val weatherdao = Room.databaseBuilder(
            this,
            WeatherDb::class.java,
            "weather.db"
        ).allowMainThreadQueries().build()
        val assets = assets.open("data.xml").bufferedReader().use { it.readText() }
        val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
            .parse(InputSource(StringReader(assets)))
        if (weatherdao.dao.getAll().isEmpty()) {
            val min: MutableList<String> = mutableListOf()
            val max: MutableList<String> = mutableListOf()
            val hourTemp: MutableList<String> = mutableListOf()
            val cloud: MutableList<String> = mutableListOf()
            val weatherTalk: MutableList<String> = mutableListOf()
            val timeList: MutableList<String> = mutableListOf()
            val loc = doc.getElementsByTagName("location").item(16) as Element
            val wh = loc.getElementsByTagName("weatherElement").item(0) as Element
            val wh2 = loc.getElementsByTagName("weatherElement").item(3) as Element
            val wh3 = loc.getElementsByTagName("weatherElement").item(4) as Element
            val wh4 = loc.getElementsByTagName("weatherElement").item(14) as Element
            val wh5 = loc.getElementsByTagName("weatherElement").item(12) as Element
            val time = wh.getElementsByTagName("time")
            val time2 = wh2.getElementsByTagName("time")
            val time3 = wh3.getElementsByTagName("time")
            val time4 = wh4.getElementsByTagName("time")
            val time5 = wh5.getElementsByTagName("time")
            for (i in 0 until time.length) {
                val timeTag = wh.getElementsByTagName("time").item(i) as Element
                hourTemp.add(timeTag.getElementsByTagName("value").item(0).textContent)
                timeList.add(
                    (timeTag.getElementsByTagName("startTime").item(0).textContent.substring(6, 10))
                )
            }
            for (i in 0 until time2.length) {
                val timeTag = wh2.getElementsByTagName("time").item(i) as Element
                max.add(timeTag.getElementsByTagName("value").item(0).textContent)
            }
            for (i in 0 until time3.length) {
                val timeTag = wh3.getElementsByTagName("time").item(i) as Element
                min.add(timeTag.getElementsByTagName("value").item(0).textContent)
            }
            for (i in 0 until time4.length) {
                val timeTag = wh4.getElementsByTagName("time").item(i) as Element
                weatherTalk.add(timeTag.getElementsByTagName("value").item(0).textContent)
            }
            for (i in 0 until time5.length) {
                val timeTag = wh5.getElementsByTagName("time").item(i) as Element
                cloud.add(timeTag.getElementsByTagName("value").item(0).textContent)
            }
            weatherdao.dao.insertData(
                Weather(
                    "台北市",
                    max.joinToString(","),
                    min.joinToString(","),
                    weatherTalk.joinToString(","),
                    hourTemp.joinToString(","),
                    cloud.joinToString(","),
                    timeList.joinToString(",")
                )
            )
            pageCount = 1;
        } else {
            for (i in 0 until weatherdao.dao.getAll().size) {
                pageCount++;
            }
        }
        println(weatherdao.dao.getAll())
        viewPager = binding.vp
        adapter = Page(supportFragmentManager)
        viewPager.adapter = adapter
        binding.add.setOnClickListener {
            startActivity(Intent(this, Search::class.java))
        }
        val assets2 = this.assets.open("city.json").bufferedReader().use { it.readText() }
        val json = JSONArray(assets2)
        val city: MutableList<String> = mutableListOf()
        for (i in 0 until weatherdao.dao.getAll().size) {
            city.add(weatherdao.dao.getAll()[i].city)
        }
        binding.hc.adapter = city(city, this)
        binding.map.setOnClickListener {
            startActivity(
                Intent(this, Map::class.java)
            )
        }
    }

    inner class Page(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
        override fun getCount(): Int {
            return pageCount
        }

        override fun getItem(position: Int): Fragment {
            return WeatherPage(position)
        }

    }
}