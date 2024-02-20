package edu.uw.ischool.haeun.quizdroid

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.NotificationCompat
import java.io.File
import java.net.HttpURLConnection
import java.net.URL

class DownloadService : Service() {

    private val channelId = "DownloadServiceChannel"

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        if (NetworkUtils.isAirplaneModeOn(this)) {
            NetworkUtils.promptForAirplaneMode(this)
        } else if (NetworkUtils.isNetworkAvailable(this)) {
            showToast("Attempting to download questions...")
            downloadQuestions()
        } else {
            showToast("No internet connection available")
        }

        return START_NOT_STICKY
    }

    private fun downloadQuestions() {
        Thread {
            try {
                val prefs = getSharedPreferences("edu.uw.ischool.haeun.quizdroid_preferences", Context.MODE_PRIVATE)
                val urlPref = prefs.getString("url_preference", "http://tednewardsandbox.site44.com/questions.json") ?: "http://tednewardsandbox.site44.com/questions.json"

                val url = URL(urlPref)
                val connection = url.openConnection() as HttpURLConnection
                val inputStream = connection.inputStream

                val file = File(filesDir, "questions.json")
                file.outputStream().use { fileOut ->
                    inputStream.copyTo(fileOut)
                }

                sendNotification("Download Successful", "Questions downloaded successfully.")
            } catch (e: Exception) {
                e.printStackTrace()
                sendNotification("Download Failed", "Failed to download questions. Please try again.")
            }
        }.start()
    }

    private fun showToast(message: String) {
        Handler(Looper.getMainLooper()).post {
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    private fun sendNotification(title: String, text: String) {
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .build()

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(1, notification)
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Download Service Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
