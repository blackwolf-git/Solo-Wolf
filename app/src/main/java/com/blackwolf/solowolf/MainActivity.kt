package com.blackwolf.solowolf

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.instagramanalyzer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var analyzer: InstagramAnalyzer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // تهيئة محلل إنستقرام
        analyzer = InstagramAnalyzer(this)
        
        setupUI()
        setupListeners()
    }
    
    private fun setupUI() {
        // تصميم نيون أزرق
        binding.apply {
            root.background = NeoBlueBackgroundFactory.create(this@MainActivity)
            usernameInput.setNeonStyle()
            analyzeBtn.setNeonButtonStyle()
            resultsContainer.setNeonBorder()
        }
    }
    
    private fun setupListeners() {
        binding.analyzeBtn.setOnClickListener {
            val username = binding.usernameInput.text.toString()
            if (username.isNotEmpty()) {
                analyzeUser(username)
            } else {
                showError("الرجاء إدخال اسم مستخدم")
            }
        }
    }
    
    private fun analyzeUser(username: String) {
        binding.progressBar.show()
        
        analyzer.analyze(username) { result ->
            binding.progressBar.hide()
            
            when (result) {
                is AnalysisResult.Success -> displayResults(result.data)
                is AnalysisResult.Error -> showError(result.message)
            }
        }
    }
    
    private fun displayResults(data: AnalysisData) {
        binding.apply {
            // عرض النتائج الأساسية
            usernameDisplay.text = data.username
            followersCount.text = data.followers.formatLargeNumber()
            engagementRate.text = "${data.engagementRate}%"
            
            // عرض التحليل المفصل
            detailedAnalysis.text = data.detailedReport
            
            // عرض الاقتراحات
            contentSuggestions.text = data.contentSuggestions
            bestPostingTimes.text = data.optimalPostingTimes.joinToString("\n")
            
            // عرض التوصيات
            recommendations.text = data.recommendations
            
            resultsContainer.visible()
        }
    }
    
    private fun showError(message: String) {
        binding.errorText.text = message
        binding.errorText.visible()
    }
}
