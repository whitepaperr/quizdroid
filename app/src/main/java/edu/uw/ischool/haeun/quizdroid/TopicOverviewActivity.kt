package edu.uw.ischool.haeun.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class TopicOverviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.topic_overview)

        val topicIndex = intent.getIntExtra("TOPIC_INDEX", 0)
        val topics = resources.getStringArray(R.array.quiz_topics)
        val descriptions = arrayOf(
            getString(R.string.math_description),
            getString(R.string.physics_description),
            getString(R.string.marvel_description)
        )

        val topicTextView = findViewById<TextView>(R.id.topicTextView)
        topicTextView.text = topics[topicIndex]

        val descriptionTextView = findViewById<TextView>(R.id.topicDescription)
        descriptionTextView.text = descriptions[topicIndex]

        val topicQuestionCountTextView = findViewById<TextView>(R.id.topicQuestionCount)
        val questionCounts = arrayOf(5, 4, 4) // Example array, adjust based on actual count
        topicQuestionCountTextView.text = "Number of Questions: ${questionCounts[topicIndex]}"

        val beginButton = findViewById<Button>(R.id.beginButton)
        beginButton.setOnClickListener {
            // Intent to start question activity
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra("TOPIC_INDEX", topicIndex)
            }
            startActivity(intent)
        }
    }
}