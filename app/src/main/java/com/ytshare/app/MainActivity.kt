package com.ytshare.app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.tv_status)
        val btnTest = findViewById<Button>(R.id.btn_test)

        statusText.text = "التطبيق جاهز للعمل\n\nافتح يوتيوب واضغط على سهم المشاركة"

        btnTest.setOnClickListener {
            // Open a test share simulation
            val intent = Intent(this, ShareReceiverActivity::class.java)
            intent.putExtra(Intent.EXTRA_TEXT, "https://youtube.com/watch?v=test")
            startActivity(intent)
        }
    }
}
