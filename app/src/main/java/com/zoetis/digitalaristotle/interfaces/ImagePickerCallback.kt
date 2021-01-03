package com.zoetis.digitalaristotle.interfaces

import com.zoetis.digitalaristotle.database.AssessmentDB

interface ImagePickerCallback {

    fun pickImage(assessmentDB: AssessmentDB)
}