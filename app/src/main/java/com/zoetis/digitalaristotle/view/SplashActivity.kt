package com.zoetis.digitalaristotle.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.databinding.ActivitySplashBinding
import com.zoetis.digitalaristotle.viewmodel.AssessmentViewModel
import com.zoetis.digitalaristotle.viewmodel.SplashViewModel
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {

    @Inject
    private lateinit var mViewModel: SplashViewModel
    @Inject
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        initViewModel()
        observeSplashLiveData()
    }

    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

    }

    private fun observeSplashLiveData() {
        mViewModel.initSplashScreen()
        val observer = Observer<Boolean> {
            startActivity(Intent(this, InstructionActivity::class.java))
            finish()
        }
        mViewModel.liveData.observe(this, observer)
    }

}