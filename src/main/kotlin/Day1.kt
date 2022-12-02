fun main() {

    fun part1(input: String): Any {
        return input.split("\n\n")
            .maxOf { it -> it.split("\n").sumOf { it.toInt() } }
    }

    fun part2(input: String): Any {
        return input.split("\n\n")
            .map { it -> it.split("\n").sumOf { it.toInt() } }
            .sorted()
            .takeLast(3)
            .sum()
    }

    val input = readInput("input1.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

