package com.blackwolf.solowolf.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityUserRegistrationBinding
import com.blackwolf.solowolf.models.PersonalityType
import com.blackwolf.solowolf.models.Sport
import com.blackwolf.solowolf.models.User
import com.blackwolf.solowolf.data.DataRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class UserRegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserRegistrationBinding
    private lateinit var repository: DataRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as SoloWolfApplication).repository

        setupUI()
    }

    private fun setupUI() {
        binding.btnCompleteRegistration.setOnClickListener {
            if (validateInput()) {
                registerUser()
            }
        }

        // Setup personality type spinner
        val personalityTypes = PersonalityType.values().map { it.name }
        binding.spinnerPersonalityType.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            personalityTypes
        )
    }

    private fun validateInput(): Boolean {
        // Implement validation logic here
        return true
    }

    private fun registerUser() {
        val firstName = binding.etFirstName.text.toString()
        val lastName = binding.etLastName.text.toString()
        val nickname = binding.etNickname.text.toString()
        val age = binding.etAge.text.toString().toInt()
        val height = binding.etHeight.text.toString().toInt()
        val weight = binding.etWeight.text.toString().toInt()
        val personalityType = PersonalityType.valueOf(binding.spinnerPersonalityType.selectedItem.toString())

        // All users automatically practice these sports
        val sports = listOf(Sport.BOXING, Sport.MMA, Sport.FULL_CONTACT, Sport.CALISTHENICS)

        val newUser = User(
            firstName = firstName,
            lastName = lastName,
            nickname = nickname,
            age = age,
            height = height,
            weight = weight,
            personalityType = personalityType,
            sports = sports
        )

        CoroutineScope(Dispatchers.IO).launch {
            repository.insertUser(newUser)
            runOnUiThread {
                // Navigate to main activity
                finish()
            }
        }
    }
}
