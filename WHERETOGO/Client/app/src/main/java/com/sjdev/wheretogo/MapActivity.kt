package com.sjdev.wheretogo

import com.sjdev.wheretogo.databinding.ActivityMapBinding
import com.sjdev.wheretogo.ui.BaseActivity
import net.daum.mf.map.api.MapView

class MapActivity: BaseActivity<ActivityMapBinding>(ActivityMapBinding::inflate) {
    override fun initAfterBinding() {
        val mapView = MapView(this)
        binding.detailMapView.addView(mapView)
    }
}