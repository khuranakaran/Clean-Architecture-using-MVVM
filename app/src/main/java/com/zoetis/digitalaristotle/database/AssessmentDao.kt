package com.zoetis.digitalaristotle.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAnswer(assessmentDB: AssessmentDB)

    @Query("SELECT * FROM assessment_answers")
    fun getAllAnswers(): List<AssessmentDB>

    @Query("SELECT * FROM assessment_answers where qno= :qNo")
    fun getAnswerByQNo(qNo: Int): List<AssessmentDB>?
}
