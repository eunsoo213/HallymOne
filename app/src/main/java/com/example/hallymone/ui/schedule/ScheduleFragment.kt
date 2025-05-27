package com.example.hallymone.ui.schedule

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.hallymone.R
import com.example.hallymone.model.Course
import com.example.hallymone.model.Schedule
import java.util.Calendar

class ScheduleFragment : Fragment() {

    private val courses = mutableListOf<Course>()
    private val schedules = mutableListOf<Schedule>()

    // 오늘 날짜로 초기화 (Calendar 사용)
    private var selectedYear: Int
    private var selectedMonth: Int
    private var selectedDay: Int

    init {
        val cal = Calendar.getInstance()
        selectedYear  = cal.get(Calendar.YEAR)
        selectedMonth = cal.get(Calendar.MONTH)
        selectedDay   = cal.get(Calendar.DAY_OF_MONTH)
    }

    // 과목 추가 결과
    private val addCourseLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val d = result.data!!
            val course = Course(
                name      = d.getStringExtra("EXTRA_COURSE_NAME")!!,
                professor = d.getStringExtra("EXTRA_COURSE_PROFESSOR")!!,
                room      = d.getStringExtra("EXTRA_COURSE_ROOM")!!
            )
            courses.add(course)
            refreshCourseButtons()
        }
    }

    // 일정 추가 결과
    private val addScheduleLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK && result.data != null) {
            val d = result.data!!
            val year  = d.getIntExtra("EXTRA_SCHEDULE_DATE_YEAR", selectedYear)
            val month = d.getIntExtra("EXTRA_SCHEDULE_DATE_MONTH", selectedMonth)
            val day   = d.getIntExtra("EXTRA_SCHEDULE_DATE_DAY", selectedDay)
            val courseName = d.getStringExtra("EXTRA_SCHEDULE_COURSE")!!
            val content    = d.getStringExtra("EXTRA_SCHEDULE_CONTENT")!!
            schedules.add(Schedule(year, month, day, courseName, content))
            // 현재 보고 있는 날짜와 같으면 리스트 갱신
            if (year==selectedYear && month==selectedMonth && day==selectedDay) {
                refreshScheduleList()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false)

        // 과목 추가
        view.findViewById<Button>(R.id.addCourseButton).setOnClickListener {
            val intent = Intent(requireContext(), AddCourseActivity::class.java)
            addCourseLauncher.launch(intent)
        }

        // 일정 추가 (과목 이름 리스트만 넘김)
        view.findViewById<Button>(R.id.addScheduleButton).setOnClickListener {
            Intent(requireContext(), AddScheduleActivity::class.java).apply {
                putStringArrayListExtra(
                    "COURSE_LIST",
                    ArrayList(courses.map { it.name })
                )
            }.also { addScheduleLauncher.launch(it) }
        }

        // 달력 날짜 선택
        view.findViewById<CalendarView>(R.id.calendarView)
            .setOnDateChangeListener { _, year, month, dayOfMonth ->
                selectedYear  = year
                selectedMonth = month
                selectedDay   = dayOfMonth
                refreshScheduleList()
            }

        // 처음 렌더링
        refreshCourseButtons()
        refreshScheduleList()

        return view
    }

    private fun refreshCourseButtons() {
        val container = view?.findViewById<LinearLayout>(R.id.subjectContainer) ?: return
        container.removeAllViews()
        for ((idx, course) in courses.withIndex()) {
            val btn = Button(requireContext()).apply {
                text = course.name
                setOnClickListener {
                    AlertDialog.Builder(requireContext())
                        .setTitle(course.name)
                        .setMessage("교수: ${course.professor}\n강의실: ${course.room}")
                        .setPositiveButton("확인", null)
                        .show()
                }
                setOnLongClickListener {
                    courses.removeAt(idx)
                    refreshCourseButtons()
                    true
                }
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = resources.getDimensionPixelSize(R.dimen.spacing_small)
                }
            }
            container.addView(btn)
        }
    }

    private fun refreshScheduleList() {
        val container = view?.findViewById<LinearLayout>(R.id.scheduleContainer) ?: return
        container.removeAllViews()
        // 선택된 연월일과 일치하는 일정만 표시
        for (sch in schedules.filter {
            it.year==selectedYear &&
                    it.month==selectedMonth &&
                    it.day==selectedDay
        }) {
            val tv = TextView(requireContext()).apply {
                text = "${sch.courseName}: ${sch.content}"
                setPadding(8,8,8,8)
                setOnClickListener {
                    AlertDialog.Builder(requireContext())
                        .setTitle("일정 상세")
                        .setMessage(
                            "과목: ${sch.courseName}\n" +
                                    "날짜: ${sch.year}/${sch.month+1}/${sch.day}\n" +
                                    "내용: ${sch.content}"
                        )
                        .setPositiveButton("확인", null)
                        .show()
                }
            }
            container.addView(tv)
        }
    }
}
