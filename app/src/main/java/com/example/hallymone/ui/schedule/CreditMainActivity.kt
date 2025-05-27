package com.example.hallymone.ui.schedule

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.TextView
import com.example.hallymone.R

class CreditMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_credit_main)

        val tvResult = findViewById<TextView>(R.id.tvResult)

        val messages = intent.getStringArrayListExtra("resultMessages")
        if (messages != null && messages.isNotEmpty()) {
            tvResult.text = messages.joinToString("\n")
        } else {
            tvResult.text = "졸업 학점을 모두 채웠습니다. 🎉"
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}