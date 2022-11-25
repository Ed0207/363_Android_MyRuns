package com.example.chien_an_chen_myruns4

import android.annotation.SuppressLint
import android.app.*
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.*
import java.time.LocalTime

class MapRecordService: Service(), LocationListener{

    private val CHANNEL_ID = "my runs channel id"
    private val REQUEST_CODE = 12
    private val NOTIFICATION_ID = 1
    private lateinit var notificationManger: NotificationManager

    @SuppressLint("MissingPermission")
    override fun onCreate() {
        super.onCreate()
        println("starting service")
        MapDisplayActivity.locationManger.requestLocationUpdates(MapDisplayActivity.bestProvider.toString(), 1L, 1f, this)
        notificationManger = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        showNotification()
    }


    // flag not important here
    // start ID specify when or which service to wait for kill
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }


    fun showNotification(){

        val intent = Intent(this, MapDisplayActivity::class.java)

        val resumeIntent = PendingIntent.getActivity(
            this, REQUEST_CODE, intent, PendingIntent.FLAG_IMMUTABLE)

        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this, CHANNEL_ID)
        builder.setContentTitle("My Runs tracking service")
        builder.setContentText("Currently recording your location data...")
        builder.setSmallIcon(R.drawable.icon)
        builder.setAutoCancel(true)
        builder.setContentIntent(resumeIntent)

        val notification = builder.build()

        // android version check place here
        val channel = NotificationChannel(CHANNEL_ID, "my runs channel", NotificationManager.IMPORTANCE_HIGH)
        notificationManger.createNotificationChannel(channel)
        notificationManger.notify(NOTIFICATION_ID, notification)
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("Not yet implemented")
    }

    override fun onLocationChanged(location: Location) {
        println("service updating location...")
        val current = LatLng(location.latitude, location.longitude)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(current, 16f)
        val marker = MarkerOptions().position(current).title("location ${MapDisplayActivity.index}")
        MapDisplayActivity.index++

        MapDisplayActivity.time = 0
        MapDisplayActivity.speed = 0f
        MapDisplayActivity.now = LocalTime.now()
        MapDisplayActivity.distance = 0f

        if(MapDisplayActivity.lastLocal != null){
            MapDisplayActivity.distance = location.distanceTo(MapDisplayActivity.lastLocal!!)
        }

        if(MapDisplayActivity.now.minute != MapDisplayActivity.preTime.minute){
            MapDisplayActivity.time = MapDisplayActivity.now.minute - MapDisplayActivity.preTime.minute
            MapDisplayActivity.time *= 60
            MapDisplayActivity.speed = MapDisplayActivity.distance/MapDisplayActivity.time
        }else if(MapDisplayActivity.now.second != MapDisplayActivity.preTime.minute){
            MapDisplayActivity.time = MapDisplayActivity.now.second - MapDisplayActivity.preTime.second
            MapDisplayActivity.speed = MapDisplayActivity.distance/MapDisplayActivity.time
        }

        if(MapDisplayActivity.previousMark != null){
            MapDisplayActivity.previousMark?.remove()
        }
        MapDisplayActivity.preTime = MapDisplayActivity.now
        MapDisplayActivity.lastLocal = location

        MapDisplayActivity.polylineOption.add(current)
        MapDisplayActivity.previousMark = MapDisplayActivity.mMap.addMarker(marker)
        MapDisplayActivity.lines.add(MapDisplayActivity.mMap.addPolyline(MapDisplayActivity.polylineOption))
        MapDisplayActivity.mMap.moveCamera(cameraUpdate)
    }

    override fun onDestroy() {
        super.onDestroy()
        println("stopping service")
    }

}