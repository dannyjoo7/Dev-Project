package com.joo.miruni.service.unlock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.joo.miruni.domain.usecase.setting.SettingGetUnlockStateUseCase
import com.joo.miruni.presentation.unlock.UnlockActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UnlockReceiver : BroadcastReceiver() {

    @Inject
    lateinit var settingGetUnlockStateUseCase: SettingGetUnlockStateUseCase

    companion object {
        private const val TAG = "UnlockReceiver"
        private const val CHANNEL_ID = "unlock_channel"
        private const val CHANNEL_NAME = "Unlock Notifications"
        private const val SERVICE_ID = 1
    }

    override fun onReceive(context: Context, intent: Intent) {
        Log.d(TAG, "onReceive called, action: ${intent.action}")

        // 옵션 여부 확인
        val isUnlockServiceAble = settingGetUnlockStateUseCase.invoke()
        Log.d(TAG, "isUnlockServiceAble: $isUnlockServiceAble")

        if (isUnlockServiceAble && intent.action == Intent.ACTION_USER_PRESENT) {
            Log.d(TAG, "Starting UnlockActivity")
            startUnlockActivity(context)
        }
    }

    private fun startUnlockActivity(context: Context) {
        context.startActivity(
            Intent(context, UnlockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
            }
        )
    }
}
