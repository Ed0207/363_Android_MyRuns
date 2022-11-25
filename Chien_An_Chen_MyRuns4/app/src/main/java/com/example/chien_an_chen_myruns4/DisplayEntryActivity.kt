package com.example.chien_an_chen_myruns4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import org.w3c.dom.Text

class DisplayEntryActivity : AppCompatActivity() {


    private lateinit var bundle: Bundle
    private lateinit var runEntryDatabaseDAO: RunEntryDatabaseDAO
    private lateinit var runEntryRepository: RunEntryRepository
    private lateinit var entries: List<RunEntry>
    private var entry: RunEntry? = null

    //Views
    private lateinit var inputTypeView: TextView
    private lateinit var activityTypeView: TextView
    private lateinit var durationView: TextView
    private lateinit var distanceView: TextView
    private lateinit var caloriesView: TextView
    private lateinit var heartRateView: TextView
    private lateinit var deleteEntryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_entry)

        bundle = getIntent().extras!!
        val entryID = bundle.getInt("EntryID")

        // Setting up database access
        runEntryDatabaseDAO = RunEntryDatabase.getDatabase(this).runEntryDatabaseDao
        runEntryRepository = RunEntryRepository(runEntryDatabaseDAO)

        // setting up views
        inputTypeView = findViewById(R.id.inputTypeInput)
        activityTypeView = findViewById(R.id.activityTypeInput)
        durationView = findViewById(R.id.durationInput)
        distanceView = findViewById(R.id.distanceInput)
        caloriesView = findViewById(R.id.caloriesInput)
        heartRateView = findViewById(R.id.heartRateInput)

        deleteEntryButton = findViewById(R.id.deleteEntryButton)

        CoroutineScope(Main).launch {
            entries = runEntryRepository.runentries.first()
            entry = entries[entryID]

            inputTypeView.text = entry!!.entryType
            activityTypeView.text = entry!!.activityType
            durationView.text = entry!!.duration.toString()
            distanceView.text = entry!!.distance.toString()
            caloriesView.text = entry!!.calories.toString()
            heartRateView.text = entry!!.heartRate.toString() + " bpm"
        }

        deleteEntryButton.setOnClickListener{
            if(entry != null){
                runEntryRepository.delete(entry!!.id)
            }else{
                Toast.makeText(this, "delete fail...", Toast.LENGTH_SHORT).show()
            }
            finish()
        }

    }
}