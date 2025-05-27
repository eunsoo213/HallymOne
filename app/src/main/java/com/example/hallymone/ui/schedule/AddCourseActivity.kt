package com.example.hallymone.ui.schedule

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.hallymone.R

class AddCourseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        val etName = findViewById<EditText>(R.id.etCourseName)
        val etProf = findViewById<EditText>(R.id.etProfessor)
        val etRoom = findViewById<EditText>(R.id.etRoom)
        val btnConfirm = findViewById<Button>(R.id.btnConfirm)

        btnConfirm.setOnClickListener {
            val name = etName.text.toString().trim()
            val prof = etProf.text.toString().trim()
            val room = etRoom.text.toString().trim()
            if (name.isEmpty() || prof.isEmpty() || room.isEmpty()) {
                if (name.isEmpty()) etName.error = "필수 입력"
                if (prof.isEmpty()) etProf.error = "필수 입력"
                if (room.isEmpty()) etRoom.error = "필수 입력"
                return@setOnClickListener
            }

            // 결과로 세 가지 필드 되돌리기
            val data = Intent().apply {
                putExtra("EXTRA_COURSE_NAME", name)
                putExtra("EXTRA_COURSE_PROFESSOR", prof)
                putExtra("EXTRA_COURSE_ROOM", room)
            }
            setResult(Activity.RESULT_OK, data)
            finish()
        }
    }
}
