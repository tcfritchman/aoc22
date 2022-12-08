fun main() {

    fun part1(input: String): Any {
        return input.split("\n").count()
    }

    fun part2(input: String): Any {
        return input.split("\n").count()
    }

    val input = readInput("inputX.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}
