package com.zoetis.digitalaristotle.viewmodel

import android.app.Application
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.zoetis.digitalaristotle.database.AssessmentDB
import com.zoetis.digitalaristotle.interfaces.NetworkResponseCallback
import com.zoetis.digitalaristotle.database.AssessmentRepository
import com.zoetis.digitalaristotle.model.Assessment
import com.zoetis.digitalaristotle.utils.NetworkHelper

class AssessmentViewModel(private val app: Application) : AndroidViewModel(app) {
    private lateinit var mList: MutableLiveData<Assessment>

    val mShowProgressBar = MutableLiveData(true)
    val mShowNetworkError: MutableLiveData<Boolean> = MutableLiveData()
    val mShowApiError = MutableLiveData<String>()
    private var mRepository = AssessmentRepository.getInstance()

    fun fetchAssessmentFromServer(forceFetch: Boolean): MutableLiveData<Assessment> {
        if (NetworkHelper.isOnline(app.baseContext)) {
            mShowProgressBar.value = true

            mList = MutableLiveData()
            mList = mRepository.getAssessment(object : NetworkResponseCallback {
                override fun onNetworkFailure(th: Throwable) {
                    mShowApiError.value = th.message
                }

                override fun onNetworkSuccess() {
                    mShowProgressBar.value = false
                }
            }, forceFetch)
        } else {
            mShowNetworkError.value = true
        }
        return mList
    }

    fun onRefreshClicked(view: View) {
        fetchAssessmentFromServer(true)
    }

    fun getAnswerByQNo(qNo: Int): MutableLiveData<List<AssessmentDB>> {
        return mRepository.getAnswerByQNo(qNo)
    }

    fun insertAnswer(assessmentDB: AssessmentDB) {
        mRepository.insertAnswer(assessmentDB)
    }
}