package edu.uw.ischool.haeun.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class QuestionsActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var currentTopic: Topic

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions)

        val topicName = intent.getStringExtra("TOPIC_NAME")!!
        val app = application as QuizApp
        currentTopic = app.topicRepository.getTopicByName(topicName)!!

        currentQuestionIndex = intent.getIntExtra("CURRENT_QUESTION_INDEX", 0)
        score = intent.getIntExtra("SCORE", 0)

        displayCurrentQuestion()

        findViewById<Button>(R.id.submitAnswerButton).setOnClickListener {
            checkAndProceedWithAnswer()
        }
    }

    private fun displayCurrentQuestion() {
        if (currentQuestionIndex < currentTopic.questions.size) {
            val question = currentTopic.questions[currentQuestionIndex]
            findViewById<TextView>(R.id.questionText).text = question.text

            val answersGroup = findViewById<RadioGroup>(R.id.answersGroup)
            answersGroup.removeAllViews()
            question.answers.forEachIndexed { index, answer ->
                val radioButton = RadioButton(this).apply {
                    text = answer
                    id = View.generateViewId()
                }
                answersGroup.addView(radioButton)
            }
            findViewById<Button>(R.id.submitAnswerButton).isEnabled = false
            answersGroup.setOnCheckedChangeListener { _, _ ->
                findViewById<Button>(R.id.submitAnswerButton).isEnabled = true
            }
        }
    }

    private fun checkAndProceedWithAnswer() {
        val answersGroup = findViewById<RadioGroup>(R.id.answersGroup)
        val selectedId = answersGroup.checkedRadioButtonId
        val selectedAnswerIndex = answersGroup.indexOfChild(findViewById(selectedId))

        val question = currentTopic.questions[currentQuestionIndex]
        val isCorrect = selectedAnswerIndex == question.answer
        if (isCorrect) {
            score++
        }

        // Intent to go to AnswerActivity with extra data
        val intent = Intent(this, AnswerActivity::class.java).apply {
            putExtra("USER_ANSWER", question.answers[selectedAnswerIndex])
            putExtra("CORRECT_ANSWER", question.answers[question.answer])
            putExtra("SCORE", score)
            putExtra("QUESTION_INDEX", currentQuestionIndex)
            putExtra("TOTAL_QUESTIONS", currentTopic.questions.size)
            putExtra("TOPIC_NAME", currentTopic.title)
        }
        startActivity(intent)
        finish()
    }


    override fun onBackPressed() {
        if (currentQuestionIndex == 0) {
            // If it's the first question, go back to the topic list
            super.onBackPressed()
        } else {
            // If not the first question, go back to the previous question
            currentQuestionIndex--
            displayCurrentQuestion()
        }
    }
}
