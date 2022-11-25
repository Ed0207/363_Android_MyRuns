package com.example.chien_an_chen_myruns4

import androidx.lifecycle.*

class RunEntryViewModel(private val runEntryRepository: RunEntryRepository): ViewModel() {

    // Initialize live data so it can be observed and udpated the viewmodel automatically
    val runEntryLiveData = runEntryRepository.runentries.asLiveData()

    fun insert(runEntry: RunEntry){
        runEntryRepository.insert(runEntry)
    }

    fun delete(runEntry: RunEntry){
        runEntryRepository.delete(runEntry.id)
    }


}


class RunEntryViewModelFactory(private val newRepository: RunEntryRepository): ViewModelProvider.Factory{
    override fun <T: ViewModel> create (modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(RunEntryViewModel::class.java)){
            return RunEntryViewModel(newRepository) as T
        }
        throw java.lang.IllegalArgumentException("incorrect type")
    }
}