package edu.uw.ischool.haeun.quizdroid

class TopicRepositoryImpl : TopicRepository {
    private val topics = mutableListOf<Topic>()

    init {
        // Math Topic
        topics.add(
            Topic(
                "Math",
                "Math is the study of numbers, shapes, and patterns.",
                listOf(
                    Question("What is 2 + 2?", listOf("4", "22", "2", "0"), 0),
                    Question("What is the square root of 16?", listOf("3", "4", "5", "6"), 1),
                    Question("What is 5 * 3?", listOf("8", "10", "15", "2"), 2),
                    Question("What is 10 - 4?", listOf("1", "4", "6", "10"), 2),
                    Question("What is 15 / 3?", listOf("5", "4", "7", "3"), 0)
                ),
                R.drawable.ic_launcher_foreground
            )
        )

        // Physics Topic
        topics.add(
            Topic(
                "Physics",
                "Physics is the natural science that studies matter, its motion and behavior through space and time.",
                listOf(
                    Question("What is the force of gravity on Earth?", listOf("9.6 m/s^2", "9.8 m/s^2", "10 m/s^2", "9.2 m/s^2"), 1),
                    Question("What is the speed of light?", listOf("2992458 m/s", "299792410 m/s", "299792458 m/s", "269432451"), 2),
                    Question("The SI unit of displacement is", listOf("Kilometer", "Centimetres", "Meter", "Millimetre"), 2),
                    Question("Which one of the following is a vector quantity?", listOf("Speed", "Pressure", "Energy", "Velocity"), 3)
                ),
                R.drawable.ic_launcher_foreground
            )
        )

        // Marvel Super Heroes Topic
        topics.add(
            Topic(
                "Marvel Super Heroes",
                "Marvel Super Heroes are iconic comic book characters from the Marvel Universe.",
                listOf(
                    Question("Who is Tony Stark?", listOf("Iron Man", "Spider Man", "Thor", "Captain America"), 0),
                    Question("How many Infinity Stones are there?", listOf("3", "4", "5", "6"), 3),
                    Question("Who is the leader of the Guardians of the Galaxy?", listOf("Ronan", "Thanos", "Groot", "Star-Lord"), 3),
                    Question("Wanda and her brother Pietro are from which country?", listOf("Korea", "Sokovia", "Japan", "Panama"), 1)
                ),
                R.drawable.ic_launcher_foreground
            )
        )
    }

    override fun getAllTopics(): List<Topic> = topics

    override fun getTopicByName(name: String): Topic? = topics.find { it.title == name }
}
