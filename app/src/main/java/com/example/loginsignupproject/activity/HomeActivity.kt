package com.example.loginsignupproject.activity

import android.graphics.Outline
import android.os.Bundle
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.loginsignupproject.R
import kotlin.random.Random

class HomeActivity : ComponentActivity() {
    private var idTxt: TextView? = null
    private var pwTxt: TextView? = null
    private var finishBtn: Button? = null
    private var img: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home)

        // id로 요소 찾기...
        idTxt = findViewById(R.id.idTxt)
        pwTxt = findViewById(R.id.pwTxt)
        finishBtn = findViewById(R.id.finishBtn)
        img = findViewById(R.id.img)

        val randomInRange: Int = Random.nextInt(0, 4)
        when(randomInRange){
            0 -> img?.setImageResource(R.drawable.hun1)
            1 -> img?.setImageResource(R.drawable.hun2)
            2 -> img?.setImageResource(R.drawable.hun3)
            3 -> img?.setImageResource(R.drawable.hun4)
            4 -> img?.setImageResource(R.drawable.hun5)
        }

        img!!.outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, view!!.width, view.height, 100f) // 원하는 반지름 값 설정
            }
        }
        img!!.clipToOutline = true

        val intent = intent
        if (intent != null) {
            val id = intent.getStringExtra("USER_ID")
            val name = intent.getStringExtra("USER_NAME")

            idTxt?.text = "ID : $id"
            pwTxt?.text = "이름 : $name"
        }

        // 종료 버튼 클릭 시...
        finishBtn!!.setOnClickListener {
            finish()
        }
    }
}
