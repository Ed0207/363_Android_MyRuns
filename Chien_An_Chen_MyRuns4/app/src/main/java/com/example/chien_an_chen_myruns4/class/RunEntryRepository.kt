package com.example.chien_an_chen_myruns4

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class RunEntryRepository(private val databaseDAO: RunEntryDatabaseDAO){

    val runentries: Flow<List<RunEntry>> = databaseDAO.getAllEntries()

    fun insert(newRunEntry: RunEntry){
        CoroutineScope(IO).launch {
            databaseDAO.insertRunEntry(newRunEntry)
            println(newRunEntry.toString())
        }
    }


    fun delete(deleteKey: Long){
        CoroutineScope(IO).launch {
            databaseDAO.deleteEntry(deleteKey)
        }
    }

}