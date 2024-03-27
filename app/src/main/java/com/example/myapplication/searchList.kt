package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.room.Room
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

class searchList(val vityList: MutableList<String>, val context: Context) : BaseAdapter() {
    val weatherdao = Room.databaseBuilder(
        context,
        WeatherDb::class.java,
        "weather.db"
    ).allowMainThreadQueries().build()

    override fun getCount(): Int {
        return vityList.size
    }

    override fun getItem(p0: Int): Any {
        return 0
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.cut, null)
        view.findViewById<TextView>(R.id.ct).text = vityList[p0]
        view.setOnClickListener {
            val assets = context.assets.open("data.xml").bufferedReader().use { it.readText() }
            val doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                .parse(InputSource(StringReader(assets)))
            val loc2 = doc.getElementsByTagName("location")

            for (j in 0 until loc2.length) {
                val loc3 = doc.getElementsByTagName("locationName").item(j).textContent
                if (loc3 == vityList[p0]) {
                    val min: MutableList<String> = mutableListOf()
                    val max: MutableList<String> = mutableListOf()
                    val hourTemp: MutableList<String> = mutableListOf()
                    val cloud: MutableList<String> = mutableListOf()
                    val weatherTalk: MutableList<String> = mutableListOf()
                    val timeList: MutableList<String> = mutableListOf()
                    val loc = doc.getElementsByTagName("location").item(j) as Element
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
                            (timeTag.getElementsByTagName("startTime")
                                .item(0).textContent.substring(6, 10))
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
                            vityList[p0],
                            max.joinToString(","),
                            min.joinToString(","),
                            weatherTalk.joinToString(","),
                            hourTemp.joinToString(","),
                            cloud.joinToString(","),
                            timeList.joinToString(",")
                        )
                    )
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            }


        }
        return view
    }
}
