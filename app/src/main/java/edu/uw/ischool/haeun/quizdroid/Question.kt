package edu.uw.ischool.haeun.quizdroid

data class Question(
    val text: String,
    val answers: List<String>,
    val answer: Int
)
