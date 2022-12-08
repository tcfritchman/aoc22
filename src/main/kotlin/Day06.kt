fun main() {

    fun part1(input: String): Any {
        return input.windowed(4).indexOfFirst(String::hasUniqueCharSet) + 4
    }

    fun part2(input: String): Any {
        return input.windowed(14).indexOfFirst(String::hasUniqueCharSet) + 14
    }

    val input = readInput("input06.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

fun String.hasUniqueCharSet(): Boolean = this.toHashSet().size == this.length
