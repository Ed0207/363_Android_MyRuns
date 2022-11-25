package com.example.chien_an_chen_myruns4

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class CustomListViewAdapter(private val context: Context, private var entryList: List<RunEntry> ): BaseAdapter() {

    override fun getCount(): Int {
        return entryList.size
    }

    override fun getItem(p0: Int): Any {
        return entryList.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }


    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {

        val view: View

        val entry = entryList[p0]

        if(p1 == null){
            view = View.inflate(context, R.layout.customlistview, null)
            val entryFirstLine: TextView = view.findViewById(R.id.entryFirstLine)
            val entrySecondLine: TextView = view.findViewById(R.id.entrySecondLine)

            entryFirstLine.text = entry.entryType + ":" + entry.activityType
            entrySecondLine.text = entry.distance.toString() + " ," + entry.duration.toString()

        }else{
            view = p1
        }

        return view
    }

    fun updateList(newList: List<RunEntry>){
        entryList = newList
    }
}