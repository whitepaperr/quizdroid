package edu.uw.ischool.haeun.quizdroid

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class TopicRepositoryImplTest {

    private lateinit var repository: TopicRepository

    @Before
    fun setUp() {
        repository = TopicRepositoryImpl()
    }

    @Test
    fun getAllTopics_returnsCorrectTopicsCount() {
        assertEquals("Expected number of topics does not match", 3, repository.getAllTopics().size)
    }

    @Test
    fun getTopicByName_returnsCorrectTopic() {
        val topic = repository.getTopicByName("Math")
        assertEquals("Math", topic?.title)
        assertEquals("Expected number of questions in Math does not match", 5, topic?.questions?.size)
    }
}
