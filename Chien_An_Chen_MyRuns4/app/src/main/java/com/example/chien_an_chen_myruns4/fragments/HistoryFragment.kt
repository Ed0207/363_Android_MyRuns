package com.example.chien_an_chen_myruns4

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    // Database variable
    //private lateinit var runEntryDatabase: RunEntryDatabase
    private lateinit var runEntryDatabaseDAO: RunEntryDatabaseDAO
    private lateinit var runEntryRepository: RunEntryRepository
    private lateinit var runEntryViewModel: RunEntryViewModel
    private lateinit var entryListView: ListView
    private lateinit var listAdapter: CustomListViewAdapter


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

        // Inflate the layout for this fragment, and to use for setting up view
        val view = inflater.inflate(R.layout.fragment_history, container, false)


        // Setting up
        // runEntryDatabase = RunEntryDatabase.getDatabase(view.context)
        runEntryDatabaseDAO = RunEntryDatabase.getDatabase(view.context).runEntryDatabaseDao
        runEntryRepository = RunEntryRepository(runEntryDatabaseDAO)
        val runEntryViewModelFactory = RunEntryViewModelFactory(runEntryRepository)
        runEntryViewModel = ViewModelProvider(requireActivity(), runEntryViewModelFactory).
            get(RunEntryViewModel::class.java)

        entryListView = view.findViewById(R.id.entryHistoryList)
        val temp: List<RunEntry> = ArrayList<RunEntry>()
        listAdapter = CustomListViewAdapter(view.context, temp)
        entryListView.adapter = listAdapter

        // Setting up live data update
        runEntryViewModel.runEntryLiveData.observe(requireActivity()){
            listAdapter.updateList(it)
            Thread.currentThread()
            listAdapter.notifyDataSetChanged()
        }


        entryListView.setOnItemClickListener { parent, view, position, id ->

            val detailEntryIntent: Intent = Intent(view.context, DisplayEntryActivity::class.java)
            detailEntryIntent.putExtra("EntryID", position)

            startActivity(detailEntryIntent)

        }



        return view
    }







    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}