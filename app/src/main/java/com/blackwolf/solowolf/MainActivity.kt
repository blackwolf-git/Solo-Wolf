package com.blackwolf.solowolf

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NeonAnalyzerUI()
        }
    }
}

@Composable
fun NeonAnalyzerUI() {
    var username by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF001F3F))
            .padding(20.dp)
    ) {
        Text("Instagram Analyzer 2050", color = Color.Cyan, fontSize = 28.sp)
        Spacer(modifier = Modifier.height(20.dp))

        BasicTextField(
            value = username,
            onValueChange = { username = it },
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF003366), shape = MaterialTheme.shapes.medium)
                .padding(16.dp)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            coroutineScope.launch(Dispatchers.IO) {
                result = analyzeInstagram(username)
            }
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Cyan)) {
            Text("حلل الحساب", color = Color.Black)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(result, color = Color.White, fontSize = 16.sp)
    }
}

suspend fun analyzeInstagram(username: String): String {
    return try {
        val url = "https://www.instagram.com/$username"
        val doc = Jsoup.connect(url).get()
        val scripts = doc.select("script[type=text/javascript]")
        val dataScript = scripts.firstOrNull { it.data().contains("profile_pic_url_hd") }?.data() ?: return "لا يمكن تحليل الحساب."

        // استخراج بعض البيانات بشكل مبدئي
        val followersRegex = "\\\\"edge_followed_by\\\\":\\\\{"count\\\\":(\\\\d+)".toRegex()
        val postsRegex = "\\\\"edge_owner_to_timeline_media\\\\":\\\\{"count\\\\":(\\\\d+)".toRegex()

        val followers = followersRegex.find(dataScript)?.groupValues?.get(1) ?: "غير معروف"
        val posts = postsRegex.find(dataScript)?.groupValues?.get(1) ?: "غير معروف"

        val bestTime = listOf("12:00 PM", "6:00 PM", "9:00 PM").random()
        val videoIdea = listOf(
            "رييل عن روتينك اليومي بنمط سريع",
            "نصيحة قصيرة بفكرة مشوقة",
            "تحدي بصري أو تفاعل مع المتابعين"
        ).random()

        "عدد المتابعين: $followers\nعدد المنشورات: $posts\n\nأفضل وقت للنشر: $bestTime\nفكرة فيديو: $videoIdea"
    } catch (e: Exception) {
        "حدث خطأ أثناء تحليل الحساب. تأكد من أن الاسم صحيح وأن الحساب عام."
    }
}
