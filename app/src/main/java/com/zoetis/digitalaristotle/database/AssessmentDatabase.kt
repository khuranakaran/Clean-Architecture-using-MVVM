package com.zoetis.digitalaristotle.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Annotates class to be a Room Database with a table (entity) of the Word class
@Database(entities = [AssessmentDB::class]
    , version = 1, exportSchema = false)

abstract class AssessmentDatabase : RoomDatabase() {

    abstract fun assessmentDao(): AssessmentDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AssessmentDatabase? = null

        fun getDatabase(context: Context): AssessmentDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AssessmentDatabase::class.java,
                    "assessment_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
