package com.zoetis.digitalaristotle.database

import androidx.lifecycle.MutableLiveData
import com.noob.apps.mvvmcountries.interfaces.NetworkResponseCallback
import com.zoetis.digitalaristotle.model.Assessment
import com.zoetis.digitalaristotle.retrofit.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentRepository private constructor() {
    private lateinit var mCallback: NetworkResponseCallback
    private lateinit var mAssessmentList: MutableLiveData<Assessment>

    companion object {
        private var mInstance: AssessmentRepository? = null
        fun getInstance(): AssessmentRepository {
            if (mInstance == null) {
                synchronized(this) {
                    mInstance = AssessmentRepository()
                }
            }
            return mInstance!!
        }
    }

    private lateinit var mAssessmentCall: Call<Assessment>

    fun getAssessment(callback: NetworkResponseCallback, forceFetch : Boolean): MutableLiveData<Assessment> {
        mCallback = callback
       /* if (mAssessmentList.value!!. && !forceFetch) {
            mCallback.onNetworkSuccess()
            return mAssessmentList
        }*/
        mAssessmentCall = RestClient.getInstance().getApiService().getAssessment()
        mAssessmentCall.enqueue(object : Callback<Assessment> {
            override fun onResponse(call: Call<Assessment>, response: Response<Assessment>) {
                mAssessmentList.value = response.body()
                mCallback.onNetworkSuccess()
            }

            override fun onFailure(call: Call<Assessment>, t: Throwable) {
                mAssessmentList.value = Assessment()
                mCallback.onNetworkFailure(t)
            }

        })
        return mAssessmentList
    }
}