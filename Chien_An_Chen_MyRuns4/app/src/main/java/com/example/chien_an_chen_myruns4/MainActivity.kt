package com.example.chien_an_chen_myruns4

import android.Manifest
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : AppCompatActivity() {

    //Fragments
    /*
    private lateinit var startFragment: Fragment
    private lateinit var historyFragment: Fragment
    private lateinit var settingsFragment: Fragment
     */
    private lateinit var fragments: ArrayList<Fragment>

    //Tabs
    private val TABTITLES = arrayOf("Start", "History", "Settings")
    private lateinit var tabConfigurationStrategy: TabLayoutMediator.TabConfigurationStrategy
    private lateinit var tabLayoutMediator: TabLayoutMediator

    //views
    private lateinit var viewPager: ViewPager2
    private lateinit var mainTabs: TabLayout

    private val MAINSHAREPREF = "MainActivitySharePreferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initializing fragment display
        fragments = ArrayList()
        fragments.add(StartFragment())
        fragments.add(HistoryFragment())
        fragments.add(SettingsFragment())

        viewPager = findViewById(R.id.mainViewPager)
        viewPager.adapter = FragmentStateAdapter(this, fragments)


        // Configure dynamic tabs
        mainTabs = findViewById(R.id.mainTabs)
        tabConfigurationStrategy = TabLayoutMediator.TabConfigurationStrategy(){
                tab: TabLayout.Tab, position: Int->
            tab.text = TABTITLES[position]
        }
        tabLayoutMediator = TabLayoutMediator(mainTabs, viewPager, tabConfigurationStrategy)
        tabLayoutMediator.attach()

        // Ask user permission for camera and storage
        // external read request
        Util.checkCameraPermissions(this)
        checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    }


    fun onSave(){
        val sharePref: SharedPreferences = getSharedPreferences(MAINSHAREPREF, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharePref.edit()
        editor.clear()
    }

    fun onLoad(){

    }

    companion object{

        var unit: String = ""
        var privacy: Boolean = true
        var comment: String = ""
    }


    // asking user for permission
    fun checkPermission(permission: String){
        val check: Int = ContextCompat.checkSelfPermission(this, permission)
        if(!(check == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, arrayOf(permission), 1)
        }
        println("Permission " + permission + " granted")
    }

    override fun onDestroy() {
        super.onDestroy()
        if(MapDisplayActivity.mapRecordIntent != null){
            stopService(MapDisplayActivity.mapRecordIntent)
        }
    }
}