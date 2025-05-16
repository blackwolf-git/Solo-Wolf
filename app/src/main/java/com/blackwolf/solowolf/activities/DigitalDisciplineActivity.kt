package com.blackwolf.solowolf.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityDigitalDisciplineBinding

class DigitalDisciplineActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDigitalDisciplineBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDigitalDisciplineBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.switchBlockPorn.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showWarning(getString(R.string.porn_block_warning))
            }
        }

        binding.switchSocialMediaLimit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                showWarning(getString(R.string.social_media_warning))
            }
        }

        binding.btnAppUsageSettings.setOnClickListener {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:$packageName")
            startActivity(intent)
        }

        binding.btnSetLimits.setOnClickListener {
            // In a real app, this would set up Focus Mode or similar
            showWarning(getString(R.string.limits_set_message))
        }
    }

    private fun showWarning(message: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle("Warning")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}
