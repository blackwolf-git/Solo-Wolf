package com.blackwolf.solowolf.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blackwolf.solowolf.R
import com.blackwolf.solowolf.databinding.ActivityBodyScannerBinding
import com.blackwolf.solowolf.models.FaceShape
import com.blackwolf.solowolf.models.HairType

class BodyScannerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBodyScannerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBodyScannerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
        analyzeBody()
    }

    private fun setupUI() {
        binding.btnScan.setOnClickListener {
            // In a real app, this would launch camera or gallery to get an image
            analyzeBody()
        }
    }

    private fun analyzeBody() {
        // Mock analysis - in a real app, this would use ML Kit or similar
        val faceShape = FaceShape.OVAL
        val hairType = HairType.STRAIGHT
        val hairLength = "Medium"
        
        binding.tvFaceShape.text = getString(R.string.face_shape_result, faceShape.name.toLowerCase().capitalize())
        binding.tvHairType.text = getString(R.string.hair_type_result, hairType.name.toLowerCase().capitalize(), hairLength)
        
        // Get recommendations
        val haircutRecommendations = getHaircutRecommendations(faceShape)
        val workoutRecommendations = getWorkoutRecommendations()
        
        binding.tvHaircutRecommendations.text = haircutRecommendations.joinToString("\n• ", "• ")
        binding.tvWorkoutRecommendations.text = workoutRecommendations.joinToString("\n• ", "• ")
    }

    private fun getHaircutRecommendations(faceShape: FaceShape): List<String> {
        return when (faceShape) {
            FaceShape.OVAL -> listOf(
                "Undercut with longer top",
                "Classic side part",
                "Textured crop",
                "Pompadour"
            )
            FaceShape.ROUND -> listOf(
                "High fade with textured top",
                "French crop",
                "Faux hawk",
                "Side-swept undercut"
            )
            FaceShape.SQUARE -> listOf(
                "Buzz cut",
                "Crew cut",
                "Short textured fringe",
                "Quiff"
            )
            FaceShape.HEART -> listOf(
                "Side-parted undercut",
                "Longer layered styles",
                "Textured fringe",
                "Comb over"
            )
            FaceShape.LONG -> listOf(
                "Fringe or bangs",
                "Side part",
                "Textured layers",
                "Medium length cuts"
            )
        }
    }

    private fun getWorkoutRecommendations(): List<String> {
        return listOf(
            "Focus on shoulder and neck exercises for better posture",
            "Incorporate resistance training 3x weekly",
            "Add HIIT sessions 2x weekly for fat burning",
            "Practice daily stretching for flexibility"
        )
    }
}

enum class FaceShape {
    OVAL, ROUND, SQUARE, HEART, LONG
}

enum class HairType {
    STRAIGHT, WAVY, CURLY, COILY
}
