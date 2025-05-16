package com.blackwolf.solowolf.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityMainBinding
import com.blackwolf.solowolf.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        // Setup bottom navigation
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Show home fragment
                    true
                }
                R.id.nav_workouts -> {
                    // Show workouts fragment
                    true
                }
                R.id.nav_aura -> {
                    // Show aura training fragment
                    true
                }
                R.id.nav_relationships -> {
                    // Show relationships fragment
                    true
                }
                R.id.nav_settings -> {
                    // Show settings fragment
                    true
                }
                else -> false
            }
        }
    }

    private fun observeViewModel() {
        viewModel.user.observe(this) { user ->
            // Update UI with user data
            binding.tvUserName.text = "${user.firstName} \"${user.nickname}\" ${user.lastName}"
            binding.tvLevel.text = "Level ${user.level}"
            binding.progressXp.max = user.level * 1000
            binding.progressXp.progress = user.xp
            binding.tvXp.text = "${user.xp}/${user.level * 1000} XP"
            
            // Update stats
            binding.progressPhysicalEnergy.progress = user.physicalEnergy
            binding.progressMentalEnergy.progress = user.mentalEnergy
            binding.progressAura.progress = user.auraPoints
        }
    }
}
