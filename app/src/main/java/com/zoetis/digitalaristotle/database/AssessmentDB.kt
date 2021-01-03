package com.zoetis.digitalaristotle.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "assessment_answers")
class AssessmentDB(

    /*@PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,*/

    @PrimaryKey
    @ColumnInfo(name = "qno") var qno: String = "",
    @ColumnInfo(name = "type") var type: String? = "",
    @ColumnInfo(name = "mcAnswer") var mcAnswer: Int = -1,
    @ColumnInfo(name = "saAnswer") var saAnswer: String = "",
    @ColumnInfo(name = "saImage") var saImage: String = "",
)
