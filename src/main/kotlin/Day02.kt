enum class RPS(val score: Int) {
    ROCK(1), PAPER(2), SCISSORS(3);

    fun beats(other: RPS): Boolean {
        return (this.getBeats() == other)
    }

    fun getBeats(): RPS {
        return when (this) {
            ROCK -> SCISSORS
            SCISSORS -> PAPER
            PAPER -> ROCK
        }
    }

    fun getLosesTo(): RPS {
        return when (this) {
            ROCK -> PAPER
            SCISSORS -> ROCK
            PAPER -> SCISSORS
        }
    }

    companion object {
        fun fromChar(value: Char): RPS {
            return when (value) {
                'A', 'X' -> ROCK
                'B', 'Y' -> PAPER
                'C', 'Z' -> SCISSORS
                else -> throw IllegalArgumentException()
            }
        }
    }
}


fun main() {

    fun part1(input: String): Any {
        return input.split("\n").sumOf {
            val opponentMove = RPS.fromChar(it[0])
            val playerMove = RPS.fromChar(it[2])
            if (playerMove == opponentMove) {
                playerMove.score + 3
            } else if (playerMove.beats(opponentMove)) {
                playerMove.score + 6
            } else {
                playerMove.score
            }
        }
    }

    fun part2(input: String): Any {
        return input.split("\n").sumOf {
            val opponentMove = RPS.fromChar(it[0])
            when (it[2]) {
                'X' -> opponentMove.getBeats().score
                'Y' -> opponentMove.score + 3
                'Z' -> opponentMove.getLosesTo().score + 6
                else -> throw IllegalArgumentException()
            }
        }
    }

    val input = readInput("input02.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

