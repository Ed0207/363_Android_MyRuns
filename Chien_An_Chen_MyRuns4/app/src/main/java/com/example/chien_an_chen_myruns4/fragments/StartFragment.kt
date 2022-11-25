package com.example.chien_an_chen_myruns4

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class StartFragment : Fragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    //Spinner value
    private lateinit var inputTypeSpinner: Spinner
    private lateinit var activityTypeSpinner: Spinner
    private lateinit var inputTypeAdapter: ArrayAdapter<CharSequence>
    private lateinit var activityTypeAdapter: ArrayAdapter<CharSequence>

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_start, container, false)

        // Setting up spinner
        inputTypeSpinner = view.findViewById(R.id.inputTypeSpinner)
        activityTypeSpinner = view.findViewById(R.id.activityTypeSpinner)

        inputTypeAdapter = ArrayAdapter.createFromResource(view.context, R.array.input_type_list, android.R.layout.simple_spinner_item)
        inputTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        activityTypeAdapter = ArrayAdapter.createFromResource(view.context, R.array.activity_type_list, android.R.layout.simple_spinner_item)
        activityTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        inputTypeSpinner.adapter = inputTypeAdapter
        activityTypeSpinner.adapter = activityTypeAdapter

        // Set OnClick Listener for button
        val startButton: Button = view.findViewById(R.id.mainStartButton)
        startButton.setOnClickListener(View.OnClickListener {
            println(" Selected input type:  " + inputTypeSpinner.selectedItem)
            println(" Selected activity type: " + activityTypeSpinner.selectedItem)

            val intent: Intent

           // only call this intent if manual entry is selected
            if(inputTypeSpinner.selectedItemId == 0L){
                intent = Intent(view.context, ManualEntryActivity::class.java)
                intent.putExtra("ActivityType", activityTypeSpinner.selectedItemId)
                println(activityTypeSpinner.selectedItemId)
            }else{
                // Map activity (to be implemented)
                intent = Intent(view.context, MapDisplayActivity::class.java)
                intent.putExtra("displayType", "input")
            }

            startActivity(intent)
        })

        // Inflate the layout for this fragment
        return view
    }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment StartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            StartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

}