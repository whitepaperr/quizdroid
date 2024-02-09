package edu.uw.ischool.haeun.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val topicsListView = findViewById<ListView>(R.id.topicsListView)
        val topics = resources.getStringArray(R.array.quiz_topics)

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, topics)
        topicsListView.adapter = adapter

        topicsListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, TopicOverviewActivity::class.java).apply {
                putExtra("TOPIC_INDEX", position)
            }
            startActivity(intent)
        }
    }
}
