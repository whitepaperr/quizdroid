package edu.uw.ischool.haeun.quizdroid

import android.app.Application

class QuizApp : Application() {
    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        topicRepository = TopicRepositoryImpl(this)
    }
}
