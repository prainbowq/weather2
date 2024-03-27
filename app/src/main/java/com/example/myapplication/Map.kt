package com.example.myapplication

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.core.graphics.scale
import androidx.room.Room
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.databinding.ActivityMapBinding
import org.json.JSONArray
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.infowindow.InfoWindow
import org.w3c.dom.Element
import kotlin.math.roundToInt

class Map : AppCompatActivity() {
    private val binding by lazy { ActivityMapBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        org.osmdroid.config.Configuration.getInstance().load(this, getPreferences(0))
        setContentView(binding.root)
        binding.mapView.controller.apply {
            setCenter(GeoPoint(25.0375, 121.5625))
            setZoom(10.0)
        }
        binding.mapView.setMultiTouchControls(true)
    }
}