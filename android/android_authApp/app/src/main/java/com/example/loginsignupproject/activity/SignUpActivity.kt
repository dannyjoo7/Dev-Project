package com.example.loginsignupproject.activity

import Account
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.loginsignupproject.R

class SignUpActivity : ComponentActivity() {
    private var inputId: EditText? = null
    private var inputPW: EditText? = null
    private var inputName: EditText? = null
    private var input_PwCheck: EditText? = null
    private var pwCheckTxt: TextView? = null
    private var dupCheckBtn: Button? = null
    private lateinit var myApplication: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)

        // user 관리 클래스 선언...
        myApplication = application as MyApplication
        val am = myApplication.accountManager

        // id로 요소 찾기...
        val signupBtn = findViewById<Button>(R.id.signupBtn)

        inputId = findViewById(R.id.input_id)
        inputPW = findViewById(R.id.input_pw)
        inputName = findViewById(R.id.input_name)
        dupCheckBtn = findViewById(R.id.dupCheckBtn)
        pwCheckTxt = findViewById(R.id.pwCheckTxt)              // id 중복 결과 알림 텍스트...
        input_PwCheck = findViewById(R.id.input_PwCheck)        // 비번 다시 입력 입력 텍스트...

        // 회원 가입 버튼 클릭 시...
        signupBtn.setOnClickListener {
            val id = inputId!!.text.toString().trim { it <= ' ' }
            val pw = inputPW!!.text.toString().trim { it <= ' ' }
            val name = inputName!!.text.toString().trim { it <= ' ' }
            val pwCheck = input_PwCheck!!.text.toString().trim { it <= ' ' }

            if (id.isEmpty() || pw.isEmpty() || name.isEmpty() || pwCheck.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "빈 칸이 존재합니다.", Toast.LENGTH_SHORT).show()
            } else if (inputId!!.isEnabled) {
                Toast.makeText(this@SignUpActivity, "ID 중복 여부 확인", Toast.LENGTH_SHORT).show()
            } else if (!pwCheckTxt!!.text.equals("같음!")) {
                Toast.makeText(this@SignUpActivity, "비밀 번호 재확인", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@SignUpActivity, "회원 가입 성공!", Toast.LENGTH_SHORT).show()
                am.list.add(Account(id, pw, name))

                // 결과값 리턴 부분...
                val intent = Intent(this, SignInActivity::class.java).apply {
                    putExtra("ID", id);
                    putExtra("PASSWORD", pw);
                }
                setResult(RESULT_OK, intent)
                if (!isFinishing) finish()
            }
        }

        // 중복 확인 버튼 클릭 시...
        dupCheckBtn!!.setOnClickListener {
            val id = inputId!!.text.toString().trim { it <= ' ' }

            if (id == "") {
                Toast.makeText(this@SignUpActivity, "ID를 입력하세요!", Toast.LENGTH_SHORT).show()
            } else if (am.list.find { it.id == id } == null) {
                Toast.makeText(this@SignUpActivity, "ID 사용 가능!", Toast.LENGTH_SHORT).show()
                inputId!!.isEnabled = false
            } else {
                Toast.makeText(this@SignUpActivity, "ID 중복!", Toast.LENGTH_SHORT).show()
            }
        }

        // 비밀번호 재입력 체크...
        input_PwCheck!!.addTextChangedListener(object : TextWatcher {

            var passwd = "" // 초기에는 빈 문자열로 초기화

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                pwCheckTxt!!.text = "다름!"
                pwCheckTxt!!.setTextColor(Color.RED)
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputText = s?.toString() ?: ""
                if (inputText != "") {
                    pwCheckTxt!!.visibility = View.VISIBLE
                }
                passwd = inputPW!!.text.toString()
                if (inputText == passwd) {
                    pwCheckTxt!!.text = "같음!"
                    pwCheckTxt!!.setTextColor(Color.BLUE)
                } else {
                    pwCheckTxt!!.setTextColor(Color.RED)
                    pwCheckTxt!!.text = "다름!"
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // 텍스트 변경 후에 호출되는 메소드
            }
        })

    }
}