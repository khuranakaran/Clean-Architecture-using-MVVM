package com.zoetis.digitalaristotle.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.databinding.ActivityAssessmentBinding
import com.zoetis.digitalaristotle.databinding.ActivityEvaluationBinding
import com.zoetis.digitalaristotle.utils.SnapHelperOneByOne
import com.zoetis.digitalaristotle.viewmodel.AssessmentViewModel

class EvaluationActivity : AppCompatActivity() {

    private lateinit var mAdapter: EvaluationAdapter
    private lateinit var mViewModel: AssessmentViewModel
    private lateinit var mActivityBinding: ActivityEvaluationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_evaluation)
        mViewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this

        initializeRecyclerView()
        initializeObservers()
    }

    private fun initializeRecyclerView() {
        mAdapter = EvaluationAdapter(this, mViewModel)
        val linearSnapHelper: LinearSnapHelper = SnapHelperOneByOne()
        linearSnapHelper.attachToRecyclerView(mActivityBinding.recyclerView)
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter
        }
    }

    private fun initializeObservers() {
        mViewModel.fetchAssessmentFromServer(false).observe(this, Observer { kt ->
            ("Subject : " + kt.subject).also { mActivityBinding.tvSubject.text = it }
            ("(" + kt.assessmentName + ")").also { mActivityBinding.tvAssessmentName.text = it }
            ("Max Marks : " + kt.totalMarks).also { mActivityBinding.tvMaxMarks.text = it }
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setDisplayShowHomeEnabled(true)
            mAdapter.setData(kt)
        })
        mViewModel.mShowApiError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(it).show()
        })
        mViewModel.mShowProgressBar.observe(this, Observer { bt ->
            if (bt) {
                mActivityBinding.progressBar.visibility = View.VISIBLE
            } else {
                mActivityBinding.progressBar.visibility = View.GONE
            }
        })
        mViewModel.mShowNetworkError.observe(this, Observer {
            AlertDialog.Builder(this).setMessage(R.string.app_no_internet_msg).show()
        })
    }
}