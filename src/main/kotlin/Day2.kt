enum class RPS(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    fun beats(other: RPS): Boolean {
        return ((this == ROCK && other == SCISSORS)
                || (this == SCISSORS && other == PAPER)
                || (this == PAPER && other == ROCK))
    }

    companion object {
        fun fromChoice(value: Char): RPS {
            return when (value) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> {
                    throw IllegalArgumentException()
                }
            }
        }
    }
}


fun main() {

    fun part1(input: String): Any {
        return input.split("\n").sumOf {
            val opponentMove = RPS.fromChoice(it[0])
            val playerMove = RPS.fromChoice(it[2])
            if (playerMove == opponentMove) {
                // Draw
                3 + playerMove.score
            } else if (playerMove.beats(opponentMove)) {
                // Win
                6 + playerMove.score
            } else {
                // Loss
                playerMove.score
            }
        }
    }

    fun part2(input: String): Any {
        return ""
    }

    val input = readInput("input2.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

