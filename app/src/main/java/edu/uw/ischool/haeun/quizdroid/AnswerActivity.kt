package edu.uw.ischool.haeun.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class AnswerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.answer)

        // Retrieve data passed from QuestionsActivity
        val userAnswer = intent.getStringExtra("USER_ANSWER") ?: ""
        val correctAnswer = intent.getStringExtra("CORRECT_ANSWER") ?: ""
        val score = intent.getIntExtra("SCORE", 0)
        val questionIndex = intent.getIntExtra("QUESTION_INDEX", 0)
        val totalQuestions = intent.getIntExtra("TOTAL_QUESTIONS", 0)
        val topicIndex = intent.getIntExtra("TOPIC_INDEX", 0)

        // Bind views
        val userAnswerTextView: TextView = findViewById(R.id.userAnswerTextView)
        val correctAnswerTextView: TextView = findViewById(R.id.correctAnswerTextView)
        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val nextOrFinishButton: Button = findViewById(R.id.nextOrFinishButton)

        // Update views
        userAnswerTextView.text = getString(R.string.your_answer, userAnswer)
        correctAnswerTextView.text = getString(R.string.correct_answer, correctAnswer)
        scoreTextView.text = getString(R.string.score, score, totalQuestions)

        // Set text of the button and its click listener based on whether it's the last question
        nextOrFinishButton.text = if (questionIndex + 1 < totalQuestions) getString(R.string.next) else getString(R.string.finish)

        nextOrFinishButton.setOnClickListener {
            if (questionIndex + 1 < totalQuestions) {
                val nextQuestionIntent = Intent(this, QuestionsActivity::class.java).apply {
                    putExtra("TOPIC_INDEX", topicIndex)
                    putExtra("QUESTION_INDEX", questionIndex + 1)
                    putExtra("SCORE", score)
                }
                startActivity(nextQuestionIntent)
            } else {
                val finishIntent = Intent(this, MainActivity::class.java)
                startActivity(finishIntent)
            }
            finish()
        }
    }

}
