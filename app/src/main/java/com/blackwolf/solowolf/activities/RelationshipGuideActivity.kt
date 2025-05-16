package com.blackwolf.solowolf.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityRelationshipGuideBinding
import com.blackwolf.solowolf.viewmodels.RelationshipGuideViewModel

class RelationshipGuideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRelationshipGuideBinding
    private lateinit var viewModel: RelationshipGuideViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRelationshipGuideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(RelationshipGuideViewModel::class.java)

        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.spinnerWomanType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedType = parent?.getItemAtPosition(position).toString()
                viewModel.loadWomanProfile(selectedType)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun observeViewModel() {
        viewModel.womanProfile.observe(this) { profile ->
            profile?.let {
                binding.tvProfileTitle.text = it.title
                binding.tvProfileDescription.text = it.description
                
                binding.tvStrengths.text = it.strengths.joinToString("\n• ", "• ")
                binding.tvWeaknesses.text = it.weaknesses.joinToString("\n• ", "• ")
                
                binding.tvApproachStrategy.text = it.approachStrategy
                binding.tvBuildingTrust.text = it.buildingTrust
                binding.tvMakingHerFall.text = it.makingHerFall
                binding.tvPsychologicalTips.text = it.psychologicalTips
                
                binding.tvWarning.text = it.warning
            }
        }
    }
}
