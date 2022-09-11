package com.example.wheretogo.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.wheretogo.databinding.ActivityMapBinding
import com.example.wheretogo.ui.BaseActivity
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object{
        lateinit var naverMap: NaverMap
    }

    private lateinit var mapView: MapView
    private lateinit var binding: ActivityMapBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }


    override fun onMapReady(p0: NaverMap) {
        MapActivity.naverMap = naverMap

        var camPos = CameraPosition(
            LatLng(34.38, 128.55),
            9.0
        )
        naverMap.cameraPosition = camPos
    }
}