package edu.uw.ischool.haeun.quizdroid

import android.app.Application
import android.content.Intent

class QuizApp : Application() {
    lateinit var topicRepository: TopicRepository

    override fun onCreate() {
        super.onCreate()
        topicRepository = TopicRepositoryImpl(this)

        Intent(this, DownloadService::class.java).also { intent ->
            startService(intent)
        }
    }
}
