package com.example.chien_an_chen_myruns4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [RunEntry::class], version = 1)
abstract class RunEntryDatabase: RoomDatabase(){

    abstract val runEntryDatabaseDao: RunEntryDatabaseDAO

    // ensure only one instance of the Database is created
    companion object{
        private var RUNENTRYDATABASE: RunEntryDatabase? = null

        fun getDatabase(context: Context): RunEntryDatabase{
            var instance = RUNENTRYDATABASE
            // Ensure only one thread access data base at a time to avoid conflict
            synchronized(this){
                if(instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RunEntryDatabase::class.java,
                        "RunEntryDB"
                    ).build()
                    RUNENTRYDATABASE = instance
                }

                // return reference
                return instance as RunEntryDatabase
            }
        }
    }
}
