package com.zoetis.digitalaristotle.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.zoetis.digitalaristotle.R
import com.zoetis.digitalaristotle.databinding.ActivityInstructionBinding
import javax.inject.Inject

class InstructionActivity : AppCompatActivity() {

    @Inject
    private lateinit var binding: ActivityInstructionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_instruction)

        binding.btnStartTest.setOnClickListener {
            startActivity(Intent(this@InstructionActivity, AssessmentActivity::class.java))
            finish()
        }
    }
}