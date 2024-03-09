package com.wrxhard.ftravel.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.wrxhard.ftravel.R
import com.wrxhard.ftravel.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityMapBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val map = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        binding= ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        map.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
    }
}