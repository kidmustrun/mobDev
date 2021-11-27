interface Student {
    public fun goToUniversity() {
        println("I go to the University")
    }
}

class TechStudent: Student {
    override fun learn() {
        println("I'm learning programming")
    }
}

class LanguageStudent: Student {
    override fun learn() {
        println("I am learning the languages of the world")
    }
}

class StudentFactory {
    fun makeStudent(type: StudentType): Student? {
        return when(type) {
            StudentType.TECH -> TechStudent()
            StudentType.LANGUAGE -> LanguageStudent()
            else -> null
        }
    }
}


enum class StudentType {
    TECH,
    LANGUAGE
}


fun main() {
    val factory = StudentFactory()
    val student = factory.makeStudent(StudentType.TECH)
    student?.goToUniversity()
    student?.learn()
}