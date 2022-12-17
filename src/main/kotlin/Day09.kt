import kotlin.math.abs

fun main() {

    fun part1(input: String): Any {
        return input.split("\n")
            .flatMap {
                val symbol = it.split(" ")[0]
                val times = it.split(" ")[1].toInt()
                List(times) {MoveDirection.from(symbol)}
            }
            .fold(Simulation())  { simulation, direction -> simulation.take(direction) }
            .countUniqueTailPositions()
    }

    fun part2(input: String): Any {
        return input.split("\n").count()
    }

    val input = readInput("input09.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

enum class MoveDirection(val dy: Int, val dx: Int) {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    companion object {
        fun from(symbol: String) = when (symbol) {
            "U" -> UP
            "D" -> DOWN
            "L" -> LEFT
            "R" -> RIGHT
            else -> throw IllegalArgumentException("$symbol is an illegal direction")
        }
    }
}

class Simulation {
    private var hy = 0
    private var hx = 0
    private var ty = 0
    private var tx = 0
    private val visitedTailPositions = mutableSetOf(Pair(0, 0))

    fun take(direction: MoveDirection): Simulation {
        // Move head
        hy += direction.dy
        hx += direction.dx

        // Move tail if needed
        if (abs(hy - ty) > 1 || abs(hx - tx) > 1) {
            ty += sign(hy - ty)
            tx += sign(hx - tx)
        }

        // Update the set of visited tail positions
        visitedTailPositions.add(Pair(tx, ty))

        return this
    }

    fun countUniqueTailPositions():Int = visitedTailPositions.size
}

fun sign(x: Int): Int = if (x > 0) {
    1
} else if (x < 0) {
    -1
} else {
    0
}
