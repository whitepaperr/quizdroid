package edu.uw.ischool.haeun.quizdroid

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class QuestionsActivity : AppCompatActivity() {
    private var currentQuestionIndex = 0
    private var score = 0
    private lateinit var questions: Array<String>
    private lateinit var choices: Array<Array<String>>
    private lateinit var correctAnswers: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.questions)

        val topicIndex = intent.getIntExtra("TOPIC_INDEX", 0)
        currentQuestionIndex = intent.getIntExtra("QUESTION_INDEX", 0) // Get current question index
        score = intent.getIntExtra("SCORE", 0)

        initializeQuestionsAndAnswers(topicIndex)
        displayCurrentQuestion()

        val submitAnswerButton: Button = findViewById(R.id.submitAnswerButton)
        submitAnswerButton.setOnClickListener {
            checkAndProceedWithAnswer()
        }
    }

    private fun initializeQuestionsAndAnswers(topicIndex: Int) {
        when (topicIndex) {
            0 -> { // Math
                questions = resources.getStringArray(R.array.math_questions)
                correctAnswers = arrayOf(
                    getString(R.string.math_correct_answer_0),
                    getString(R.string.math_correct_answer_1),
                    getString(R.string.math_correct_answer_2),
                    getString(R.string.math_correct_answer_3),
                    getString(R.string.math_correct_answer_4)
                )
                choices = arrayOf(
                    resources.getStringArray(R.array.math_choices_0),
                    resources.getStringArray(R.array.math_choices_1),
                    resources.getStringArray(R.array.math_choices_2),
                    resources.getStringArray(R.array.math_choices_3),
                    resources.getStringArray(R.array.math_choices_4)
                )
            }
            1 -> { // Physics
                questions = resources.getStringArray(R.array.physics_questions)
                correctAnswers = arrayOf(
                    getString(R.string.physics_correct_answer_0),
                    getString(R.string.physics_correct_answer_1),
                    getString(R.string.physics_correct_answer_2),
                    getString(R.string.physics_correct_answer_3)
                )
                choices = arrayOf(
                    resources.getStringArray(R.array.physics_choices_0),
                    resources.getStringArray(R.array.physics_choices_1),
                    resources.getStringArray(R.array.physics_choices_2),
                    resources.getStringArray(R.array.physics_choices_3)
                )
            }
            2 -> { // Marvel
                questions = resources.getStringArray(R.array.marvel_questions)
                correctAnswers = arrayOf(
                    getString(R.string.marvel_correct_answer_0),
                    getString(R.string.marvel_correct_answer_1),
                    getString(R.string.marvel_correct_answer_2),
                    getString(R.string.marvel_correct_answer_3)
                )
                choices = arrayOf(
                    resources.getStringArray(R.array.marvel_choices_0),
                    resources.getStringArray(R.array.marvel_choices_1),
                    resources.getStringArray(R.array.marvel_choices_2),
                    resources.getStringArray(R.array.marvel_choices_3)
                )
            }
        }
    }

    private fun displayCurrentQuestion() {
        val questionText: TextView = findViewById(R.id.questionText)
        val answersGroup: RadioGroup = findViewById(R.id.answersGroup)
        questionText.text = questions[currentQuestionIndex]

        answersGroup.removeAllViews()
        choices[currentQuestionIndex].forEachIndexed { index, choice ->
            val radioButton = RadioButton(this).apply {
                text = choice
                id = index
            }
            answersGroup.addView(radioButton)
        }

        findViewById<Button>(R.id.submitAnswerButton).isEnabled = false
        answersGroup.setOnCheckedChangeListener { _, checkedId ->
            findViewById<Button>(R.id.submitAnswerButton).isEnabled = checkedId != -1
        }
    }

    private fun checkAndProceedWithAnswer() {
        val answersGroup = findViewById<RadioGroup>(R.id.answersGroup)
        val selectedId = answersGroup.checkedRadioButtonId

        if (selectedId != -1) {
            val selectedAnswer = findViewById<RadioButton>(selectedId).text.toString()
            val isCorrect = selectedAnswer == correctAnswers[currentQuestionIndex]

            if (isCorrect) {
                score++
            }

            val intent = Intent(this, AnswerActivity::class.java).apply {
                putExtra("USER_ANSWER", selectedAnswer)
                putExtra("CORRECT_ANSWER", correctAnswers[currentQuestionIndex])
                putExtra("SCORE", score)
                putExtra("QUESTION_INDEX", currentQuestionIndex) // Pass the current question index
                putExtra("TOTAL_QUESTIONS", questions.size) // Total number of questions
                putExtra("TOPIC_INDEX", intent.getIntExtra("TOPIC_INDEX", 0)) // Pass the topic index for continuity
                putExtra("IS_LAST_QUESTION", currentQuestionIndex == questions.size - 1) // Check if it's the last question
            }
            startActivity(intent)
            finish()
        }
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
