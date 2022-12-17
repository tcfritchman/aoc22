import kotlin.math.abs

fun main() {

    fun runSimulation(input: String, size: Int) = input.split("\n")
        .flatMap {
            val symbol = it.split(" ")[0]
            val times = it.split(" ")[1].toInt()
            List(times) { MoveDirection.from(symbol) }
        }
        .fold(Simulation(size)) { simulation, direction -> simulation.take(direction) }
        .countUniqueTailPositions()

    fun part1(input: String): Any {
        return runSimulation(input, size = 2)
    }

    fun part2(input: String): Any {
        return runSimulation(input, size = 10)
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

class Simulation(val size: Int) {
    private val knots = List(size) { Knot(0, 0) }
    private val head = knots[0]
    private val tail = knots[size - 1]
    private val visitedTailPositions = mutableSetOf(Knot(0, 0))

    fun take(direction: MoveDirection): Simulation {
        // Move head
        head.y += direction.dy
        head.x += direction.dx

        // Move tail segments as needed
        for (i in 1 until size) {
            val current = knots[i]
            val previous = knots[i - 1]
            if (abs(previous.y - current.y) > 1 || abs(previous.x - current.x) > 1) {
                current.y += sign(previous.y - current.y)
                current.x += sign(previous.x - current.x)
            }
        }

        // Update the set of visited tail positions
        visitedTailPositions.add(tail.copy())
        return this
    }

    fun countUniqueTailPositions(): Int = visitedTailPositions.size

    private data class Knot(var x: Int, var y: Int)
}

fun sign(x: Int): Int = if (x > 0) {
    1
} else if (x < 0) {
    -1
} else {
    0
}
