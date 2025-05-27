package com.example.hallymone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.hallymone.ui.schedule.ScheduleFragment
import com.example.hallymone.R

class ScheduleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule)

        // ScheduleFragment를 화면에 표시
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, ScheduleFragment())
            .commit()
    }
}