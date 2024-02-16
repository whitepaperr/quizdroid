package edu.uw.ischool.haeun.quizdroid

data class Topic(
    val title: String,
    val desc: String,
    val questions: List<Question>,
    val iconResId: Int
)
