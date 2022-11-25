package com.example.chien_an_chen_myruns4.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.chien_an_chen_myruns4.MainActivity
import com.example.chien_an_chen_myruns4.R
import com.example.chien_an_chen_myruns4.SettingsFragment.Companion.selectUnitValue

class RadioButtonDialog: DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val tempArray: Array<String> = arrayOf("Metric (Kilometers)", "Imperial (miles)")

        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_radio_dialog, null)
        builder.setView(view)
        builder.setTitle("Unit Preferences: ")

        builder.setSingleChoiceItems(R.array.unit_list, selectUnitValue){ dialog, which->
            selectUnitValue = which
        }

        builder.setNegativeButton("Ok") { dialog, which ->
            MainActivity.unit = tempArray[selectUnitValue]
            println("Unit choose: ${MainActivity.unit}")
        }

        builder.setPositiveButton("cancel"){dialog, which->

        }

        return builder.create()
    }
}