package com.example.chien_an_chen_myruns4.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.chien_an_chen_myruns4.MainActivity
import com.example.chien_an_chen_myruns4.ManualEntryActivity
import com.example.chien_an_chen_myruns4.R

class TextDialog(val inputType: String, val keyboardType: String): DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val view = requireActivity().layoutInflater.inflate(R.layout.fragment_text_dialog, null)
        builder.setView(view)

        // setting up proper view for each dialog type
        val inputText = view.findViewById<EditText>(R.id.inputDialog)
        if(keyboardType == "number"){
            inputText.inputType = InputType.TYPE_CLASS_NUMBER
        }else{
            inputText.inputType = InputType.TYPE_CLASS_TEXT
        }

        if(inputType == "Comment(settings)"){
            inputText.setText(MainActivity.comment, TextView.BufferType.EDITABLE)
            builder.setTitle("Comment: ")
        }else if(inputType == "Comment(entry)"){
            builder.setTitle("Comment: ")
        }else{
            builder.setTitle(inputType)
        }

        // For different prompt message
        builder.setNegativeButton("save") { dialog, which ->

            when(inputType){

                "Duration"->{
                    if(inputText.text.toString() != "") {
                        ManualEntryActivity.duration = inputText.text.toString().toLong()
                    }
                }

                "Distance"->{
                    if(inputText.text.toString() != "") {
                        ManualEntryActivity.distance = inputText.text.toString().toLong()
                    }
                }

                "Calories"->{
                    if(inputText.text.toString() != "") {
                        ManualEntryActivity.calories = inputText.text.toString().toLong()
                    }
                }

                "Heart Rate"->{
                    if(inputText.text.toString() != "") {
                        ManualEntryActivity.heartRate = inputText.text.toString().toLong()
                        println("saving heart rate : " + ManualEntryActivity.heartRate)
                    }
                }

                "Comment(entry)"->{
                    ManualEntryActivity.entryComment = inputText.text.toString()
                    println("saving comment : " + ManualEntryActivity.entryComment)
                }

                "Comment(settings)"->{
                    MainActivity.comment = inputText.text.toString()
                }
            }
        }

        builder.setPositiveButton("cancel") { dialog, which ->

        }

        return builder.create()
    }
}