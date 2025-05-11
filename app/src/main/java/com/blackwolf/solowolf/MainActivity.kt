package com.blackwolf.solowolf


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Load icons from URLs
        loadIcon(findViewById(R.id.daily_tasks_icon), "https://cdn-icons-png.flaticon.com/512/3652/3652197.png")
        loadIcon(findViewById(R.id.exercises_icon), "https://cdn-icons-png.flaticon.com/512/3009/3009886.png")
        loadIcon(findViewById(R.id.aura_icon), "https://cdn-icons-png.flaticon.com/512/1828/1828884.png")
        loadIcon(findViewById(R.id.girls_section_icon), "https://cdn-icons-png.flaticon.com/512/3135/3135715.png")
        loadIcon(findViewById(R.id.diet_icon), "https://cdn-icons-png.flaticon.com/512/3053/3053176.png")
        loadIcon(findViewById(R.id.shape_icon), "https://cdn-icons-png.flaticon.com/512/2942/2942416.png")

        // Save button click listener
        findViewById<Button>(R.id.save_user_data).setOnClickListener {
            val name = findViewById<EditText>(R.id.name_input).text.toString()
            val age = findViewById<EditText>(R.id.age_input).text.toString()
            val height = findViewById<EditText>(R.id.height_input).text.toString()
            val weight = findViewById<EditText>(R.id.weight_input).text.toString()
            val skillLevel = findViewById<EditText>(R.id.skill_level).text.toString()
            val training = findViewById<EditText>(R.id.preferred_training).text.toString()

            // Simple validation
            if (name.isEmpty() || age.isEmpty() || height.isEmpty() || weight.isEmpty() ||
                skillLevel.isEmpty() || training.isEmpty()) {
                Toast.makeText(this, "يرجى ملء جميع الحقول", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "تم حفظ البيانات!", Toast.LENGTH_SHORT).show()
                // Add code here to save data (e.g., SharedPreferences or database)
            }
        }

        // Section buttons click listeners
        findViewById<androidx.cardview.widget.CardView>(R.id.daily_tasks_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم المهام اليومية", Toast.LENGTH_SHORT).show()
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.exercises_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم التمارين", Toast.LENGTH_SHORT).show()
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.aura_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم الهالة", Toast.LENGTH_SHORT).show()
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.girls_section_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم الفتيات", Toast.LENGTH_SHORT).show()
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.diet_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم النظام الغذائي", Toast.LENGTH_SHORT).show()
        }
        findViewById<androidx.cardview.widget.CardView>(R.id.shape_button).setOnClickListener {
            Toast.makeText(this, "فتح قسم الشكل", Toast.LENGTH_SHORT).show()
        }
    }

    // Function to load icons from URLs
    private fun loadIcon(imageView: ImageView, url: String) {
        object : AsyncTask<String, Void, Bitmap?>() {
            override fun doInBackground(vararg urls: String): Bitmap? {
                return try {
                    val urlConnection = URL(urls[0])
                    val connection = urlConnection.openConnection() as HttpURLConnection
                    connection.doInput = true
                    connection.connect()
                    val input = connection.inputStream
                    BitmapFactory.decodeStream(input)
                } catch (e: Exception) {
                    e.printStackTrace()
                    null
                }
            }

            override fun onPostExecute(result: Bitmap?) {
                if (result != null) {
                    imageView.setImageBitmap(result)
                }
            }
        }.execute(url)
    }
}
