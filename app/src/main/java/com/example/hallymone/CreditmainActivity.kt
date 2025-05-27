package com.example.hallymone

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import android.widget.TextView
import com.example.hallymone.ui.schedule.CreditMainActivity

class CreditmainActivity : AppCompatActivity() {
    private var isVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_creditmain)

        val btnToggle = findViewById<Button>(R.id.btnToggle)
        val btnToggle2 = findViewById<Button>(R.id.btnToggle2)
        val optionLayout = findViewById<LinearLayout>(R.id.optionLayout)
        val optionLayout2 = findViewById<LinearLayout>(R.id.optionLayout2)

        btnToggle.setOnClickListener {
            isVisible = !isVisible
            optionLayout.visibility = if (isVisible) View.VISIBLE else View.GONE
        }
        btnToggle2.setOnClickListener {
            isVisible = !isVisible
            optionLayout2.visibility = if (isVisible) View.VISIBLE else View.GONE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var majorSum = 0
        var doubleMajorSum = 0
        var liberalArtsSum = 0

        var selectedCategory = ""
        var selectedCredit = 0

        val historyLayout = findViewById<LinearLayout>(R.id.historyLayout)
        val historyList = mutableListOf<String>()

        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val btn6 = findViewById<Button>(R.id.btn6)
        val btnAdd = findViewById<Button>(R.id.btnAdd)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)
        val btnReset = findViewById<Button>(R.id.btnReset)


        btn1.setOnClickListener {
            selectedCategory = "주전공"
            Toast.makeText(this, "주전공 선택됨", Toast.LENGTH_SHORT).show()
        }
        btn2.setOnClickListener {
            selectedCategory = "복수전공"
            Toast.makeText(this, "복수전공 선택됨", Toast.LENGTH_SHORT).show()
        }
        btn3.setOnClickListener {
            selectedCategory = "교양"
            Toast.makeText(this, "교양 선택됨", Toast.LENGTH_SHORT).show()
        }

        btn4.setOnClickListener {
            selectedCredit = 1
            Toast.makeText(this, "1학점 선택됨", Toast.LENGTH_SHORT).show()
        }
        btn5.setOnClickListener {
            selectedCredit = 2
            Toast.makeText(this, "2학점 선택됨", Toast.LENGTH_SHORT).show()
        }
        btn6.setOnClickListener {
            selectedCredit = 3
            Toast.makeText(this, "3학점 선택됨", Toast.LENGTH_SHORT).show()
        }

        btnAdd.setOnClickListener {
            if (selectedCategory.isEmpty() || selectedCredit == 0) {
                Toast.makeText(this, "항목과 학점을 모두 선택해주세요!", Toast.LENGTH_SHORT).show()
            } else {
                // 1. 텍스트 만들기
                val addedText = "$selectedCategory - ${selectedCredit}학점"

                // 2. 리스트에 추가
                historyList.add(addedText)

                // 3. 6개 초과되면 가장 오래된 항목 제거
                if (historyList.size > 6) {
                    historyList.removeAt(0)
                }

                // 4. UI 갱신
                historyLayout.removeAllViews()
                for (text in historyList.asReversed()) {
                    val textView = TextView(this)
                    textView.text = text
                    textView.textSize = 16f
                    textView.setPadding(8, 8, 8, 8)
                    historyLayout.addView(textView)
                }
                when (selectedCategory) {
                    "주전공" -> majorSum += selectedCredit
                    "복수전공" -> doubleMajorSum += selectedCredit
                    "교양" -> liberalArtsSum += selectedCredit
                }

                // 추가 후 초기화
                selectedCategory = ""
                selectedCredit = 0
                Toast.makeText(this, "추가되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }
        btnConfirm.setOnClickListener {
            val totalScore = majorSum + doubleMajorSum + liberalArtsSum
            val messages = ArrayList<String>()

            if (totalScore < 130)
                messages.add("총 학점이 ${130 - totalScore}점 부족합니다.")

            if (majorSum < 33)
                messages.add("주전공 학점이 ${33 - majorSum}점 부족합니다.")

            if (doubleMajorSum < 39)
                messages.add("복수전공 학점이 ${33 - doubleMajorSum}점 부족합니다.")

            if (liberalArtsSum < 35)
                messages.add("교양 학점이 ${26 - liberalArtsSum}점 부족합니다.")

            // Intent로 다음 액티비티에 데이터 전달
            val intent = Intent(this, CreditMainActivity::class.java).apply {
                putStringArrayListExtra("resultMessages", messages)
            }
            startActivity(intent)
        }
        btnReset.setOnClickListener {

            majorSum = 0
            doubleMajorSum = 0
            liberalArtsSum = 0

            historyList.clear()                      // 리스트 초기화
            historyLayout.removeAllViews()          // 화면에서도 제거
            Toast.makeText(this, "초기화되었습니다.", Toast.LENGTH_SHORT).show()
        }
    }
}