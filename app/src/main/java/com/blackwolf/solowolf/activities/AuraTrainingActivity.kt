package com.blackwolf.solowolf.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityAuraTrainingBinding

class AuraTrainingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuraTrainingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuraTrainingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        loadAuraExercises()
    }

    private fun setupUI() {
        binding.btnStartTraining.setOnClickListener {
            // Start the selected training program
        }
    }

    private fun loadAuraExercises() {
        val exercises = listOf(
            AuraExercise(
                title = "Basic Presence",
                description = "Develop your basic aura and personal presence",
                difficulty = "Beginner",
                duration = "10 minutes daily",
                steps = listOf(
                    "Stand straight with shoulders back",
                    "Breathe deeply and evenly",
                    "Visualize a blue light surrounding you",
                    "Maintain strong eye contact when practicing with others"
                ),
                benefits = "Improves first impressions, makes you more noticeable"
            ),
            AuraExercise(
                title = "Commanding Aura",
                description = "Project authority and leadership presence",
                difficulty = "Intermediate",
                duration = "15 minutes daily",
                steps = listOf(
                    "Practice power poses for 2 minutes",
                    "Visualize your aura expanding to fill the room",
                    "Speak slowly and deliberately",
                    "Practice maintaining an unbreakable composure"
                ),
                benefits = "Enhances leadership qualities, commands respect"
            ),
            AuraExercise(
                title = "Magnetic Attraction",
                description = "Develop an aura that naturally draws people in",
                difficulty = "Advanced",
                duration = "20 minutes daily",
                steps = listOf(
                    "Meditate on feelings of warmth and openness",
                    "Visualize your aura pulsing with inviting energy",
                    "Practice smiling with your eyes",
                    "Combine with open body language"
                ),
                benefits = "Makes you more approachable, enhances charisma"
            )
        )

        val adapter = AuraExerciseAdapter(exercises) { exercise ->
            // Show exercise details
            binding.tvExerciseTitle.text = exercise.title
            binding.tvExerciseDescription.text = exercise.description
            binding.tvExerciseDifficulty.text = "Difficulty: ${exercise.difficulty}"
            binding.tvExerciseDuration.text = "Duration: ${exercise.duration}"
            binding.tvExerciseSteps.text = exercise.steps.joinToString("\n\n", "Steps:\n\n")
            binding.tvExerciseBenefits.text = "Benefits: ${exercise.benefits}"
        }

        binding.rvAuraExercises.adapter = adapter
    }
}

data class AuraExercise(
    val title: String,
    val description: String,
    val difficulty: String,
    val duration: String,
    val steps: List<String>,
    val benefits: String
)
