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
        val topicName = intent.getStringExtra("TOPIC_NAME") ?: ""

        // Bind views
        val userAnswerTextView: TextView = findViewById(R.id.userAnswerTextView)
        val correctAnswerTextView: TextView = findViewById(R.id.correctAnswerTextView)
        val scoreTextView: TextView = findViewById(R.id.scoreTextView)
        val nextOrFinishButton: Button = findViewById(R.id.nextOrFinishButton)

        // Update views
        userAnswerTextView.text = "Your answer: $userAnswer"
        correctAnswerTextView.text = "Correct answer: $correctAnswer"
        scoreTextView.text = "Score: $score out of $totalQuestions"

        val isLastQuestion = questionIndex + 1 >= totalQuestions

        nextOrFinishButton.text = if (isLastQuestion) getString(R.string.finish) else getString(R.string.next)
        nextOrFinishButton.setOnClickListener {
            if (isLastQuestion) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                val nextQuestionIntent = Intent(this, QuestionsActivity::class.java).apply {
                    putExtra("TOPIC_NAME", topicName)
                    putExtra("CURRENT_QUESTION_INDEX", questionIndex + 1)
                    putExtra("SCORE", score)
                }
                startActivity(nextQuestionIntent)
            }
            finish()
        }
    }

}