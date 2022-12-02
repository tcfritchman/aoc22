fun readInput(path: String): String {
    val resource = {}::class.java.getResource(path)
    return resource?.readText() ?: throw IllegalArgumentException("This input does not exist: $path")
}

fun printAnswer(f: () -> Any) {
    val timerStart = System.currentTimeMillis()
    val answer = f()
    val timerStop = System.currentTimeMillis()
    println("Answer: $answer - calculated in ${timerStop - timerStart}ms")
}