package com.zoetis.digitalaristotle.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.zoetis.digitalaristotle.app.DigitalAristotle
import com.zoetis.digitalaristotle.interfaces.NetworkResponseCallback
import com.zoetis.digitalaristotle.model.Assessment
import com.zoetis.digitalaristotle.retrofit.RestClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AssessmentRepository() {
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

    fun getAllAnswers(): MutableLiveData<List<AssessmentDB>> {
        val allAnswers = MutableLiveData<List<AssessmentDB>>()
        val data = DigitalAristotle.database.assessmentDao().getAllAnswers()
        allAnswers.postValue(data)

        return allAnswers
    }

    private lateinit var mAssessmentCall: Call<Assessment>

    fun getAssessment(
        callback: NetworkResponseCallback,
        forceFetch: Boolean
    ): MutableLiveData<Assessment> {
        mCallback = callback
        mAssessmentList = MutableLiveData()
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

    fun insertAnswer(assessmentDB: AssessmentDB): LiveData<AssessmentDB> {
        val insertAnswer = MutableLiveData<AssessmentDB>()
        DigitalAristotle.database.assessmentDao().insertAnswer(assessmentDB)
        insertAnswer.value = assessmentDB
        return insertAnswer
    }

    fun getAnswerByQNo(qNo: Int): MutableLiveData<List<AssessmentDB>> {
        val answer = MutableLiveData<List<AssessmentDB>>()
        val data = DigitalAristotle.database.assessmentDao().getAnswerByQNo(qNo)
        answer.postValue(data)

        return answer
    }
}