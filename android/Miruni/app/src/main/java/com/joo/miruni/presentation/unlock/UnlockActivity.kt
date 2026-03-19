package com.joo.miruni.presentation.unlock

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UnlockActivity : ComponentActivity() {
    private val unlockViewModel: UnlockViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(Color.WHITE, Color.WHITE),
            navigationBarStyle = SystemBarStyle.light(Color.WHITE, Color.WHITE)
        )

        setContent {
            com.joo.miruni.theme.MiruniTheme {
                UnlockScreen(unlockViewModel)
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로가기 버튼 비활성화
            }
        })
    }

    override fun onUserLeaveHint() {
        super.onUserLeaveHint()
        // 사용자가 홈으로 나갈 때 종료하여 다음 잠금해제 시 새로 생성
        finish()
    }
}
