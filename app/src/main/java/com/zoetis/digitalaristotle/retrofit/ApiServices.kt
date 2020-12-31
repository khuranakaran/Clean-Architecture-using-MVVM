package com.zoetis.digitalaristotle.retrofit

import com.zoetis.digitalaristotle.model.Assessment
import retrofit2.Call
import retrofit2.http.GET

interface ApiServices {

    @GET("exam.json")
    fun getAssessment() : Call<Assessment>
}