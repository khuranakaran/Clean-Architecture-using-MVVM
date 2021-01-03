package com.zoetis.digitalaristotle.view

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.database.AssessmentDB
import com.zoetis.digitalaristotle.databinding.ActivityAssessmentBinding
import com.zoetis.digitalaristotle.interfaces.ImagePickerCallback
import com.zoetis.digitalaristotle.utils.Constants
import com.zoetis.digitalaristotle.utils.SnapHelperOneByOne
import com.zoetis.digitalaristotle.utils.Utility
import com.zoetis.digitalaristotle.viewmodel.AssessmentViewModel
import io.github.erehmi.countdown.CountDownTask
import io.github.erehmi.countdown.CountDownTimers
import java.io.File
import java.io.InputStream


class AssessmentActivity : AppCompatActivity(), CountDownTimers.OnCountDownListener {

    private lateinit var mAdapter: AssessmentAdapter
    private lateinit var mViewModel: AssessmentViewModel
    private lateinit var mActivityBinding: ActivityAssessmentBinding
    private var mCountDownTask: CountDownTask? = null
    private var mDeadlineMillis: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivityBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_assessment)
        mViewModel = ViewModelProvider(this).get(AssessmentViewModel::class.java)

        mActivityBinding.viewModel = mViewModel
        mActivityBinding.lifecycleOwner = this
        mDeadlineMillis = CountDownTask.elapsedRealtime() + Constants.MINUTES_30

        mActivityBinding.btnSubmitTest.setOnClickListener {
            showTestSubmissionDialog()
        }

        initializeRecyclerView()
        initializeObservers()
    }
    var assessment = AssessmentDB()

    private val imagePickerCallback = object : ImagePickerCallback {
        override fun pickImage(assessmentDB: AssessmentDB) {
            startImagePicker()

            this@AssessmentActivity.assessment = assessmentDB
        }
    }

    private fun initializeRecyclerView() {
        mAdapter = AssessmentAdapter(this, mViewModel, imagePickerCallback)
        val linearSnapHelper: LinearSnapHelper = SnapHelperOneByOne()
        linearSnapHelper.attachToRecyclerView(mActivityBinding.recyclerView)
        mActivityBinding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = mAdapter

        }
    }

    private fun startImagePicker() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(
                1080,
                1080
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            val fileUri = data?.data

            val imageStream: InputStream? = fileUri?.let { contentResolver.openInputStream(it) }
            val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)

            assessment.saImage = Utility.getInstance(this)?.encodeImage(selectedImage).toString()

            mViewModel.insertAnswer(assessment)

            val file: File = ImagePicker.getFile(data)!!

            //You can also get File Path from intent
            val filePath: String = ImagePicker.getFilePath(data)!!
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show()
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

            mCountDownTask = CountDownTask.create()
                .until(
                    mActivityBinding.tvCountdownTimer,
                    mDeadlineMillis,
                    1000,
                    this@AssessmentActivity
                );
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

    override fun onTick(view: View?, millisUntilFinished: Long) {
        mActivityBinding.tvCountdownTimer.text =
            Utility.getInstance(this)?.convertSecondsToHMmSs((millisUntilFinished / 1000)) ?: "0"

        if (millisUntilFinished < Constants.MINUTES_15) {
            mActivityBinding.tvCountdownTimer.setTextColor(Color.RED)
        }

        if (millisUntilFinished < Constants.SECOND_5) {
            Toast.makeText(this, R.string.warning_finishing_test, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onFinish(view: View?) {
        startActivity(Intent(this, EvaluationActivity::class.java))
        finish()
    }

    private fun showTestSubmissionDialog() {
        val builder = AlertDialog.Builder(this)
        //set title for alert dialog
        builder.setTitle(R.string.dialogTitle)
        //set message for alert dialog
        builder.setMessage(R.string.dialogMessage)
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        //performing positive action
        builder.setPositiveButton("Yes") { dialogInterface, which ->
            startActivity(Intent(this, EvaluationActivity::class.java))
            finish()
        }


        //performing negative action
        builder.setNegativeButton("No") { dialogInterface, which -> }

        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()
    }
}