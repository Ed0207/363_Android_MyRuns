package com.example.chien_an_chen_myruns4

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import com.example.chien_an_chen_myruns4.dialog.TextDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import java.util.*

class ManualEntryActivity : AppCompatActivity() {

    private lateinit var entryListAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_entry)

        // set up list view for entries
        val manualEntryList = arrayOf("Date", "Time", "Duration", "Distance", "Calories", "Heart Rate", "Comment")
        entryListAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, manualEntryList)
        listView = findViewById(R.id.manualEntryList)
        listView.adapter = entryListAdapter


        // set up buttons
        val cancelButton: Button = findViewById(R.id.manualEntryCancel)
        cancelButton.setOnClickListener{
            finish()
        }

        // submit user input to data base
        val saveButton: Button = findViewById(R.id.manualEntrySave)
        saveButton.setOnClickListener{

            val runEntryDatabaseDAO = RunEntryDatabase.getDatabase(this).runEntryDatabaseDao
            val runEntryRepository = RunEntryRepository(runEntryDatabaseDAO)

            val activityList: Array<String> = resources.getStringArray(R.array.activity_type_list)
            val bundle = intent.extras!!
            val newEntry = RunEntry()

            newEntry.distance = distance
            newEntry.duration = duration
            newEntry.calories = calories
            newEntry.heartRate = heartRate
            newEntry.comment = entryComment
            newEntry.entryType = "Manual Entry"
            newEntry.activityType = activityList[bundle.getLong("ActivityType").toInt()]

            runEntryRepository.insert(newEntry)

            finish()
        }


        // call corresponding dialog window when correct value is selected
        listView.setOnItemClickListener { adapterView, view, i, l ->
            when(i){
                0 -> {
                    // Calendar
                    val calendar = Calendar.getInstance()
                    val initDay = calendar.get(Calendar.DAY_OF_MONTH)
                    val initMonth = calendar.get(Calendar.MONTH)
                    val initYear = calendar.get(Calendar.YEAR)

                    DatePickerDialog(this, {view, setYear, setMonth, setDay ->
                        year = setYear.toLong()
                        month = setMonth.toLong()
                        day = setDay.toLong()
                        println("Selected date: $year $month $day")
                    } , initYear, initMonth, initDay).show()
                }
                1 -> {
                    // Clock
                    val calendar = Calendar.getInstance()
                    val initMin = calendar.get(Calendar.MINUTE)
                    val initHour = calendar.get(Calendar.HOUR)

                    TimePickerDialog(this, {view, setHour, setMin->
                        hour = setHour.toLong()
                        min = setMin.toLong()
                        println("Selected time: $hour $min")
                    }, initHour, initMin, true).show()
                }
                2 -> {
                    // Duration (number)
                    val dialog = TextDialog("Duration", "number")
                    dialog.show(supportFragmentManager, "Duration")
                }
                3 -> {
                    // Distance (number)
                    val dialog = TextDialog("Distance", "number")
                    dialog.show(supportFragmentManager, "Distance")
                }
                4 -> {
                    // Calories (number)
                    val dialog = TextDialog("Calories", "number")
                    dialog.show(supportFragmentManager, "Calories")
                }
                5 -> {
                    // Heart Rate (number)
                    val dialog = TextDialog("Heart Rate", "number")
                    dialog.show(supportFragmentManager, "Heart Rate")
                }
                6 -> {
                    // Comment (text)
                    val dialog = TextDialog("Comment(entry)", "text")
                    dialog.show(supportFragmentManager, "comment (entry)")
                }
        } }
    }

    companion object{
        var hour = 0L
        var min = 0L
        var year = 0L
        var month = 0L
        var day = 0L

        var duration = 0L
        var distance = 0L
        var calories = 0L
        var heartRate = 0L
        var entryComment: String = ""
    }
}