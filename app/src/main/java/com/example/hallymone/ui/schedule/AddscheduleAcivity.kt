package com.example.hallymone.ui.schedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.hallymone.R

class AddScheduleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_schedule)

        // 1. 뷰 바인딩
        val datePicker    = findViewById<DatePicker>(R.id.datePicker)
        val spinnerCourse = findViewById<Spinner>(R.id.spinnerCourse)
        val etContent     = findViewById<EditText>(R.id.etContent)
        val btnSave       = findViewById<Button>(R.id.btnSaveSchedule)

        // 2. 인텐트로 넘어온 과목 리스트(문자열) 꺼내기
        val courseNames = intent
            .getStringArrayListExtra("COURSE_LIST")
            ?: arrayListOf()  // null 대비

        // 3. spinner 세팅
        spinnerCourse.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            courseNames
        )

        // 4. 저장 버튼 클릭
        btnSave.setOnClickListener {
            val y   = datePicker.year
            val m   = datePicker.month      // 0-based
            val d   = datePicker.dayOfMonth
            val course = spinnerCourse.selectedItem as? String
            val content = etContent.text.toString().trim()

            if (course.isNullOrEmpty()) {
                Toast.makeText(this, "과목을 선택하세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (content.isEmpty()) {
                etContent.error = "내용을 입력하세요"
                return@setOnClickListener
            }

            // 5. 결과를 돌려보내기
            Intent().apply {
                putExtra("EXTRA_SCHEDULE_DATE_YEAR", y)
                putExtra("EXTRA_SCHEDULE_DATE_MONTH", m)
                putExtra("EXTRA_SCHEDULE_DATE_DAY", d)
                putExtra("EXTRA_SCHEDULE_COURSE", course)
                putExtra("EXTRA_SCHEDULE_CONTENT", content)
            }.also { data ->
                setResult(Activity.RESULT_OK, data)
                finish()
            }
        }
    }
}
