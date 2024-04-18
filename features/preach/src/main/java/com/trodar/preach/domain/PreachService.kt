package com.trodar.preach.domain

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.app.NotificationCompat
import com.trodar.common.Core
import com.trodar.common.constants.PreachConstant.SIXTY_MIN
import com.trodar.common.constants.PreachConstant.TIME_OUT
import com.trodar.preach.R
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject
import kotlin.math.roundToInt


@AndroidEntryPoint
class PreachService @Inject constructor() : Service() {
    private val binder = LocalBinder()
    private val timer = Timer()

    override fun onCreate() {

        if (Core.preachRepository.getStartTime() == 0L) {
            Core.preachRepository
                .setStartTime(System.currentTimeMillis())
                .setMinutes(0)
        }

        startForeground(Core.preachConst.APP_ID, myNotify())

        @SuppressLint("HandlerLeak") val handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
                manager.notify(Core.preachConst.APP_ID, myNotify())
            }
        }

        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if (Core.preachRepository.getStartTime() <= 0) return
                val msg = handler.obtainMessage()
                handler.sendMessage(msg)
            }
        }, 0, TIME_OUT)

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_REDELIVER_INTENT
    }

    private fun createChanelId(): String {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channelId = getString(com.trodar.theme.R.string.app_name)
        val notificationChannel =
            NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_DEFAULT)
        notificationChannel.description = channelId
        notificationChannel.setSound(null, null)
        notificationManager.createNotificationChannel(notificationChannel)
        return channelId
    }

    private fun myNotify(): Notification {
        val intent = Intent(this, Core.mainActivityClass)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S)
            PendingIntent.getActivity(
            this,
            (System.currentTimeMillis() / 1000).toInt(),
            intent,
            PendingIntent.FLAG_MUTABLE
        ) else
            PendingIntent.getActivity(
            this,
            (System.currentTimeMillis() / 1000).toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT)


        val minutes: Int = getMinutes()
        val alarmTime = Core.preachRepository.getAlarm()
        if (minutes % SIXTY_MIN == 0 && alarmTime != minutes && minutes != 0) {
            val v = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager =
                    getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
                vibratorManager.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                getSystemService(VIBRATOR_SERVICE) as Vibrator
            }
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE))
        }
        if (alarmTime != minutes) {
            Core.preachRepository.setAlarm(minutes)
        }
        val chanelId = createChanelId()
        @SuppressLint("DefaultLocale") val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, chanelId)
                .setSmallIcon(R.drawable.ic_service)
                .setContentTitle(
                    getString(com.trodar.theme.R.string.serviceHeader) + String.format(
                        " %d:%02d",
                        minutes / SIXTY_MIN,
                        minutes % SIXTY_MIN
                    )
                )
                .setContentText(getString(com.trodar.theme.R.string.serviceDescription))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setColor(118369)
                .setContentIntent(pendingIntent)
        return builder.build()
    }

    fun stop() {
        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.cancel(Core.preachConst.APP_ID)
        timer.cancel()
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.cancel()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return super.onUnbind(intent)
    }

    override fun onBind(intent: Intent?): IBinder = binder

    fun setPaused() {
        if (!getPaused()) {
            Core.preachRepository
                .setMinutes(getMinutes())
                .setStartTime(0)
        } else {
            Core.preachRepository.setStartTime(System.currentTimeMillis())
        }
    }

    fun getPaused(): Boolean {
        return Core.preachRepository.getStartTime() == 0L
    }

    inner class LocalBinder : Binder() {
        fun getService(): PreachService = this@PreachService
    }

    companion object {
        fun getMinutes(): Int {
            var minutes = Core.preachRepository.getMinutes()
            val startTime = Core.preachRepository.getStartTime()
            if (startTime > 0) {
                minutes =
                    (minutes + (System.currentTimeMillis() - startTime) / 60000).toFloat()
                        .roundToInt()
            }
            return minutes
        }
    }
}