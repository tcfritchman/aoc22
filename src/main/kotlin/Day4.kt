fun main() {

    fun part1(input: String): Any {
        return input.split("\n")
            .map { line -> line.split(",").flatMap { it.split("-") }.map { it.toInt()} }
            .count { (it[0] <= it[2] && it[1] >= it[3]) || (it[2] <= it[0] && it[3] >= it[1]) }
    }

    fun part2(input: String): Any {
        return input.split("\n")
            .map { line -> line.split(",").flatMap { it.split("-") }.map { it.toInt()} }
            .count { it[0] <= it[3] && it[1] >= it[2] }
    }

    val input = readInput("input4.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}
