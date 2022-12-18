import kotlin.math.max
import kotlin.math.min

fun main() {
    class Simulation(input: String, hasFloor: Boolean = false) {

        private val startPoint = Pair(500, 0)
        private val lowerBound = 1000
        private val floor: Int?
        private val blocked: MutableSet<Pair<Int, Int>> = mutableSetOf()
        private var currentLocation = startPoint
        private var sandCount = 0

        init {
            blocked += input.split("\n")
                .map {
                    it.split(" -> ")
                        .map { pair -> Pair(pair.split(",")[0].toInt(), pair.split(",")[1].toInt()) }
                        .windowed(size = 2, step = 1)
                }
                .flatten()
                .flatMap {
                    if (it[0].first == it[1].first) {
                        // vertical line
                        val x = it[0].first
                        val start = min(it[0].second, it[1].second)
                        val end = max(it[0].second, it[1].second)
                        (start..end).map { y -> Pair(x, y) }
                    } else {
                        // horizontal line
                        val y = it[0].second
                        val start = min(it[0].first, it[1].first)
                        val end = max(it[0].first, it[1].first)
                        (start..end).map { x -> Pair(x, y) }
                    }
                }

            floor = if (hasFloor) {
                blocked.map { it.second }.max() + 2
            } else null
        }

        fun run() {

            while (currentLocation.second < lowerBound && !blocked.contains(startPoint)) {
                val down = Pair(currentLocation.first, currentLocation.second + 1)
                val left = Pair(currentLocation.first - 1, currentLocation.second + 1)
                val right = Pair(currentLocation.first + 1, currentLocation.second + 1)

                if (canMoveTo(down)) {
                    currentLocation = down
                } else if (canMoveTo(left)) {
                    currentLocation = left
                } else if (canMoveTo(right)) {
                    currentLocation = right
                } else {
                    blocked.add(currentLocation)
                    sandCount++
                    currentLocation = startPoint
                }
            }
        }

        fun getSandCount() = sandCount

        private fun canMoveTo(location: Pair<Int, Int>): Boolean =
            !blocked.contains(location) && (floor == null || floor > location.second)

    }

    fun part1(input: String): Any {
        val simulation = Simulation(input)
        simulation.run()
        return simulation.getSandCount()
    }

    fun part2(input: String): Any {
        val simulation = Simulation(input, hasFloor = true)
        simulation.run()
        return simulation.getSandCount()
    }

    val input = readInput("input14.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}


