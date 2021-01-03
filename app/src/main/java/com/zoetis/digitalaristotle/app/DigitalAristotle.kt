package com.zoetis.digitalaristotle.app

import android.app.Application
import androidx.room.Room
import com.zoetis.digitalaristotle.database.AssessmentDatabase
import com.zoetis.digitalaristotle.database.AssessmentRepository
import com.zoetis.digitalaristotle.utils.AppSharedPref

class DigitalAristotle: Application() {
    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(this, AssessmentDatabase::class.java, "assessment_database.db").allowMainThreadQueries().build()
        database = Room.databaseBuilder(this, AssessmentDatabase::class.java, "assessment_database.db").allowMainThreadQueries().fallbackToDestructiveMigration().build()

        AssessmentDatabase.getDatabase(this).assessmentDao()

        AppSharedPref.getInstance(this)
    }
    companion object {
        lateinit var database: AssessmentDatabase
    }
}