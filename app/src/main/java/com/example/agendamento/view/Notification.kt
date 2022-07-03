package com.example.agendamento.view

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.agendamento.R

const val notificationID = 1
const val channelID = "channel"
const val titleEX = "title"
const val messageEX = "message"

class Notification : BroadcastReceiver() {
    override fun onReceive(p0: Context, p1: Intent) {
        val notification =
            NotificationCompat.Builder(p0, channelID)
                .setSmallIcon(R.drawable.ic_schedule_new)
                .setContentTitle(p1.getStringExtra(titleEX))
                .setContentText(p1.getStringExtra(messageEX))
                .build()

        val manager = p0.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)
    }
}