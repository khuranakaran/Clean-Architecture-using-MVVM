package com.zoetis.digitalaristotle.model

data class Assessment(
    val assessmentId: String,
    val assessmentName: String,
    val duration: Int,
    val questions: List<Question>,
    val subject: String,
    val totalMarks: Int
)