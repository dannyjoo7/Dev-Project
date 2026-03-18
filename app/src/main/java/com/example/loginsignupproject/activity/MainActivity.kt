package com.example.loginsignupproject.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import com.example.loginsignupproject.R


class MainActivity : ComponentActivity() {
    private var inputId: EditText? = null
    private var inputPW: EditText? = null
    private lateinit var myApplication: MyApplication
    private lateinit var signUpLauncher: ActivityResultLauncher<Intent>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        myApplication = application as MyApplication
        val am = myApplication.accountManager

        // user list 호출...
        var list = am!!.list;

        // id로 요소 찾기...
        val loginButton = findViewById<Button>(R.id.loginBtn)
        val signupBtn = findViewById<Button>(R.id.signupBtn)

        inputId = findViewById(R.id.input_id)
        inputPW = findViewById(R.id.input_pw)

        // 로그인 버튼 클릭 시
        loginButton.setOnClickListener {
            val id = inputId!!.text.toString().trim { it <= ' ' }
            val pw = inputPW!!.text.toString().trim { it <= ' ' }

            if (id.isEmpty()) {
                Toast.makeText(this@MainActivity, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else if (pw.isEmpty()) {
                Toast.makeText(this@MainActivity, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show()
            } else {
                val searchedUser = list.find { it.id == id }
                if (searchedUser == null) {
                    Toast.makeText(this@MainActivity, "해당 ID는 없습니다.", Toast.LENGTH_SHORT).show()
                } else if (searchedUser!!.passwd != pw) {
                    Toast.makeText(this@MainActivity, "비밀번호를 다시 입력하세요!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "로그인 성공!", Toast.LENGTH_SHORT).show()

                    inputId!!.setText("");
                    inputPW!!.setText("");

                    // 액티비티 넘어가기...
                    val intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra("USER_ID", searchedUser.id)
                    intent.putExtra("USER_NAME", searchedUser.name)
                    startActivity(intent)
                }
            }
        }

        // 회원 가입 버튼 클릭 시...
        signupBtn.setOnClickListener {
            inputId!!.setText("");
            inputPW!!.setText("");

            // 액티비티 넘어가기...
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }
}