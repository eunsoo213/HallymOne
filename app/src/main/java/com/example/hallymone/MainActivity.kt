package com.example.hallymone

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import com.example.hallymone.R
import com.example.hallymone.MainScreenActivity


class MainActivity : AppCompatActivity() {

    private val id = "20251234"
    private val password = "1234"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val tv_id = findViewById<EditText>(R.id.tv_id)
        val tv_pw = findViewById<EditText>(R.id.tv_pw)
        val btnM = findViewById<Button>(R.id.btn_m)

        btnM.setOnClickListener(){
            var textId = tv_id.text.toString()
            var textPw = tv_pw.text.toString()
            var inputLogin = tv_id.text.trim().toString()
            var inputPassword = tv_pw.text.trim().toString()

//            if (inputLogin == id && inputPassword == password){
//                var intent = Intent(this, MainScreenActivity::class.java)
//                startActivity(intent)
//            }
            if (inputLogin == id && inputPassword == password) {
                startActivity(Intent(this, MainScreenActivity::class.java))
                finish()
            }
            else {
                if (inputLogin.isNullOrEmpty() && inputPassword.isNullOrEmpty()){
                    Toast.makeText(this, "아이디와 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show()
                }
                else if (inputLogin != id){
                    Toast.makeText(this, "존재하지 않는 아이디입니다", Toast.LENGTH_SHORT).show()
                }
                else if (inputPassword != password){
                    Toast.makeText(this, "비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show()
                }
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}