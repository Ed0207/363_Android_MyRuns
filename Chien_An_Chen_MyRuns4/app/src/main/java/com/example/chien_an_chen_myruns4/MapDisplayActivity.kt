package com.example.chien_an_chen_myruns4

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.example.chien_an_chen_myruns4.databinding.ActivityMapDisplayBinding
import com.google.android.gms.maps.model.*
import java.time.LocalTime


class MapDisplayActivity : AppCompatActivity(), OnMapReadyCallback,
        LocationListener, GoogleMap.OnMapLongClickListener {


    private lateinit var binding: ActivityMapDisplayBinding

    companion object{

        lateinit var mapRecordIntent: Intent
        lateinit var mMap: GoogleMap
        var index = 0;

        // map component
        lateinit var locationManger: LocationManager
        var bestProvider: String? = null
        lateinit var criteria: Criteria
        var previousMark: Marker? = null
        lateinit var preTime: LocalTime
        var lastLocal: Location? = null
        lateinit var polylineOption: PolylineOptions
        lateinit var lines :MutableList<Polyline>

        //calculation
        var time = 0
        var speed = 0f
        var now = LocalTime.now()
        var distance = 0f
    }

    // view
    private lateinit var mapInfo: TextView
    private lateinit var cancelButton: Button
    private lateinit var saveButton: Button

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // location manager initialization
        locationManger = getSystemService(LOCATION_SERVICE) as LocationManager
        criteria = Criteria()
        criteria.accuracy = Criteria.ACCURACY_FINE
        bestProvider = locationManger.getBestProvider(criteria, true)

        //view components
        mapInfo = findViewById(R.id.mapInfoText)
        cancelButton = findViewById(R.id.mapCancel)
        saveButton = findViewById(R.id.mapSave)

        saveButton.setOnClickListener{
            stopService(mapRecordIntent)
            finish()
        }

        cancelButton.setOnClickListener {
            stopService(mapRecordIntent)
            finish()
        }

        //

        mapRecordIntent = Intent(this, MapRecordService::class.java)

        preTime = LocalTime.now()
        polylineOption = PolylineOptions()
        polylineOption.color(Color.BLACK)
        lines = arrayListOf()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        //Alternative
        if(bestProvider == null){
            criteria.accuracy = Criteria.ACCURACY_COARSE
            bestProvider = locationManger.getBestProvider(criteria, true)
        }
        println("best provider: ${bestProvider.toString()}")

        // display 'current' location on map
        if(bestProvider != null ){
            val lastKnown = locationManger.getLastKnownLocation(bestProvider.toString())
            println("lastKnown: $lastKnown")
            if(lastKnown != null){
                onLocationChanged(lastKnown)
            }
            //locationManger.requestLocationUpdates(bestProvider.toString(), 1L, 1f, this)
        }
        startService(mapRecordIntent)
    }


    // core
    override fun onLocationChanged(location: Location) {

        /*
        val current = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 16f)
        val marker = MarkerOptions().position(current).title("location $index")
        index++

        var time = 0
        var speed = 0f
        val now = LocalTime.now()
        var distance = 0f

        if(lastLocal != null){
            distance = location.distanceTo(lastLocal!!)
        }

        if(now.minute != preTime.minute){
            time = now.minute - preTime.minute
            time *= 60
            speed = distance/time
        }else if(now.second != preTime.minute){
            time = now.second - preTime.second
            speed = distance/time
        }

        if(previousMark != null){
            previousMark?.remove()
        }
        preTime = now
        lastLocal = location

        polylineOption.add(current)
        previousMark = mMap.addMarker(marker)
        lines.add(mMap.addPolyline(polylineOption))
        mMap.moveCamera(cameraUpdate)
         */

        mapInfo.text = "Current speed: $speed m/s"
    }


    override fun onMapLongClick(targetPos: LatLng) {
        TODO("Not yet implemented")

    }

    override fun onDestroy() {
        super.onDestroy()
        if(locationManger != null){
            locationManger.removeUpdates(this)
        }
    }

}