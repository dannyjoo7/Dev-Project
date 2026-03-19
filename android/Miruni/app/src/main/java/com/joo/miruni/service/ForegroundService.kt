package com.joo.miruni.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.joo.miruni.R
import com.joo.miruni.presentation.main.MainActivity
import com.joo.miruni.service.notification.ReminderManagerUtil
import com.joo.miruni.service.notification.ReminderType
import com.joo.miruni.service.unlock.UnlockReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForegroundService : Service() {

    companion object {
        private val TAG = "ForegroundService"
        private const val CHANNEL_ID = "combined_channel"
        private const val CHANNEL_NAME = "Foreground Channel"
        private const val SERVICE_ID = 1
    }

    @Inject
    lateinit var reminderManagerUtil: ReminderManagerUtil

    @Inject
    lateinit var unlockReceiver: UnlockReceiver

    private var isReceiverRegistered = false

    override fun onCreate() {
        super.onCreate()
        startForegroundService()
        registerUnlockReceiver()
    }

    // 서비스 시작
    private fun startForegroundService() {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.service_notification_title))
            .setSmallIcon(getIcon())
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setColor(ContextCompat.getColor(this, R.color.ios_gray_calander_font))
            .setAutoCancel(true)
            .setOngoing(false)
            .build()

        startForeground(SERVICE_ID, notification)
    }

    // 메인 로직
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /*
        * 리마인더 서비스
        * */
        intent?.let {
            val id = it.getLongExtra("TODO_ID", -1)
            val title = it.getStringExtra("TODO_TITLE") ?: getString(R.string.reminder_default)
            val reminderType = it.getSerializableExtra("REMINDER_TYPE") as? ReminderType
            val reminderTime = it.getLongExtra("REMINDER_TIME", -1)
            val deadLineTime = it.getLongExtra("DEADLINE_TIME", -1)

            if (id != -1L && reminderType != null) {
                sendReminder(id, title, reminderType)
                scheduleNextAlarm(id, title, deadLineTime, reminderType, reminderTime)
            }
        }


        return START_STICKY
    }

    // Unlock 리시버 등록 (onCreate에서 1회만 호출)
    private fun registerUnlockReceiver() {
        if (!isReceiverRegistered) {
            registerReceiver(unlockReceiver, IntentFilter().apply {
                addAction(Intent.ACTION_USER_PRESENT)
            })
            isReceiverRegistered = true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isReceiverRegistered) {
            unregisterReceiver(unlockReceiver)
            isReceiverRegistered = false
        }
    }

    // 채널 생성
    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = getString(R.string.service_notification_channel_desc)
        }
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
    }



    private fun sendReminder(id: Long, title: String, reminderType: ReminderType?) {
        val message = when (reminderType) {
            ReminderType.ONE_HOUR_BEFORE -> getString(R.string.reminder_1hour)
            ReminderType.TEN_MINUTES_BEFORE -> getString(R.string.reminder_10min)
            ReminderType.FIVE_MINUTES_BEFORE -> getString(R.string.reminder_5min)
            ReminderType.NOW -> getString(R.string.reminder_now)
            null -> getString(R.string.reminder_notification)
        }

        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(getIcon())
            .setColor(ContextCompat.getColor(this, R.color.ios_gray_calander_font))
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setColorized(true)
            .setOngoing(false)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(id.toInt(), notification)
    }

    private fun scheduleNextAlarm(
        id: Long,
        title: String,
        deadLineTime: Long,
        reminderType: ReminderType?,
        reminderTime: Long,
    ) {
        // 다음 알람 결정
        val nextReminderType = when (reminderType) {
            ReminderType.ONE_HOUR_BEFORE -> ReminderType.TEN_MINUTES_BEFORE
            ReminderType.TEN_MINUTES_BEFORE -> ReminderType.FIVE_MINUTES_BEFORE
            ReminderType.FIVE_MINUTES_BEFORE -> ReminderType.NOW
            ReminderType.NOW -> null
            else -> null
        }

        val nextAlarmTime = calculateNextAlarmTime(nextReminderType, deadLineTime)

        if (nextReminderType != null && nextAlarmTime != null) {
            reminderManagerUtil.setNextAlarm(
                id,
                title,
                deadLineTime,
                nextAlarmTime,
                nextReminderType
            )
        } else {
            reminderManagerUtil.cancelAlarmsForTodoItem(id)
        }
    }

    private fun calculateNextAlarmTime(reminderType: ReminderType?, deadLineTime: Long): Long? {
        return when (reminderType) {
            ReminderType.ONE_HOUR_BEFORE -> deadLineTime - 3600000
            ReminderType.TEN_MINUTES_BEFORE -> deadLineTime - 600000
            ReminderType.FIVE_MINUTES_BEFORE -> deadLineTime - 300000
            ReminderType.NOW -> deadLineTime
            else -> null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun getIcon(): Int {
        return R.mipmap.ic_launcher
    }
}
