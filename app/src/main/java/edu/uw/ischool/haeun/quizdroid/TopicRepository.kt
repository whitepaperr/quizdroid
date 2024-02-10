package edu.uw.ischool.haeun.quizdroid

interface TopicRepository {
    fun getAllTopics(): List<Topic>
    fun getTopicByName(name: String): Topic?
}
