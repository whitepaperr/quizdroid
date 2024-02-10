package edu.uw.ischool.haeun.quizdroid

data class Topic(
    val title: String,
    val longDescription: String,
    val questions: List<Question>,
    val iconResId: Int
)
