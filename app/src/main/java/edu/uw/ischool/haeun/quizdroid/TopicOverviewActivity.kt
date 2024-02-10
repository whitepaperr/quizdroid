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

        val topicName = intent.getStringExtra("TOPIC_NAME")!!
        val app = application as QuizApp
        val topic = app.topicRepository.getTopicByName(topicName)!!

        findViewById<TextView>(R.id.topicTextView).text = topic.title
//        findViewById<TextView>(R.id.topicShortDesc).text = topic.shortDescription
        findViewById<TextView>(R.id.topicDescription).text = topic.longDescription
        findViewById<TextView>(R.id.topicQuestionCount).text = "Questions: ${topic.questions.size}"

        findViewById<Button>(R.id.beginButton).setOnClickListener {
            val intent = Intent(this, QuestionsActivity::class.java).apply {
                putExtra("TOPIC_NAME", topic.title)
            }
            startActivity(intent)
        }
    }
}

