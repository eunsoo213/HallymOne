// app/src/main/java/com/example/hallymone/model/Schedule.kt
package com.example.hallymone.model

data class Schedule(
    val year: Int,
    val month: Int,   // 0 = January, Calendar.MONTH 기준
    val day: Int,
    val courseName: String,
    val content: String
)
