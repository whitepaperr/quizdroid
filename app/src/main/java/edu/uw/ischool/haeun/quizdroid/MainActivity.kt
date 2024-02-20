package edu.uw.ischool.haeun.quizdroid

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var downloadStatusReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupDownloadStatusReceiver()

        val app = application as QuizApp
        val topics = app.topicRepository.getAllTopics().map { it.title }

        val topicsListView: ListView = findViewById(R.id.topicsListView)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        topicsListView.adapter = adapter

        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedTopic = topics[position]
            val intent = Intent(this, TopicOverviewActivity::class.java).apply {
                putExtra("TOPIC_NAME", selectedTopic)
            }
            startActivity(intent)
        }
    }

    private fun setupDownloadStatusReceiver() {
        downloadStatusReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val isSuccessful = intent.getBooleanExtra("status", false)
                if (!isSuccessful) {
                    showDownloadFailedDialog()
                }
            }
        }
        registerReceiver(downloadStatusReceiver, IntentFilter("edu.uw.ischool.haeun.quizdroid.DOWNLOAD_STATUS"))
    }

    private fun showDownloadFailedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Download Failed")
            .setMessage("Would you like to retry?")
            .setPositiveButton("Retry") { _, _ ->
                Intent(this, DownloadService::class.java).also { intent ->
                    startService(intent)
                }
            }
            .setNegativeButton("Quit") { _, _ -> finish() }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(downloadStatusReceiver)
    }
}
