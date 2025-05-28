package com.example.hallymone.qrcode

import android.graphics.Bitmap
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.hallymone.R
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.common.BitMatrix

class QrcodeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qrcode)

        val studentId = intent.getStringExtra("studentId") ?: "20251234"

        val qrImage = findViewById<ImageView>(R.id.qrImage)
        val idText = findViewById<TextView>(R.id.idText)

        // 학번 보여주기
        idText.text = studentId

        // QR 생성
        val qrBitmap = generateQRCode(studentId)
        qrBitmap?.let {
            qrImage.setImageBitmap(it)
        }
    }

    private fun generateQRCode(data: String): Bitmap? {
        val size = 512
        return try {
            val bitMatrix: BitMatrix = MultiFormatWriter().encode(
                data,
                BarcodeFormat.QR_CODE,
                size,
                size
            )
            val bmp = Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565)

            for (x in 0 until size) {
                for (y in 0 until size) {
                    bmp.setPixel(
                        x,
                        y,
                        if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE
                    )
                }
            }
            bmp
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}