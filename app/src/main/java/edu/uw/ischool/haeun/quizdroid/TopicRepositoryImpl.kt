package edu.uw.ischool.haeun.quizdroid

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class TopicRepositoryImpl(private val context: Context) : TopicRepository {
    private var topics: List<Topic> = loadTopicsFromAssets()

    private fun loadTopicsFromAssets(): List<Topic> {
        val jsonFileString = getJsonDataFromAsset(context, "questions.json")
        jsonFileString?.let {
            val gson = Gson()
            val listTopicType = object : TypeToken<List<Topic>>() {}.type
            return gson.fromJson(it, listTopicType)
        }
        return emptyList()
    }

    override fun getAllTopics(): List<Topic> = topics

    override fun getTopicByName(name: String): Topic? = topics.find { it.title == name }

    private fun getJsonDataFromAsset(context: Context, fileName: String): String? {
        return try {
            context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            null
        }
    }
}
