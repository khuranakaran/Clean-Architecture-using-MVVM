package com.zoetis.digitalaristotle.model

data class Question(
    val id: String,
    val marks: Int,
    val mcOptions: List<String>,
    val qno: Int,
    val text: String,
    val type: String
)