import kotlin.math.min

fun main() {

    fun part1(input: String): Any {
        val heightmap = input.split("\n")
            .map { it.map { it } }

        return findShortestPath(heightmap)
    }

    fun part2(input: String): Any {
        return input.split("\n").count()
    }

    val input = readInput("input12.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

fun findShortestPath(heightmap: List<List<Char>>): Int {
    val startLocationY = heightmap.indexOfFirst { it.contains('S') }
    val startLocationX = heightmap[startLocationY].indexOfFirst { it == 'S' }
    val startCoordinates = Pair(startLocationX, startLocationY)
    val visited = mutableSetOf<Pair<Int, Int>>()
    return doTheRecursiveThing(heightmap, visited, startCoordinates, 0)
}

fun doTheRecursiveThing(heightmap: List<List<Char>>,
                        visited: MutableSet<Pair<Int, Int>>,
                        coordinates: Pair<Int, Int>,
                        depth: Int): Int {
    val currentHeight = effectiveHeight(heightmap[coordinates.second][coordinates.first])
    val north = Pair(coordinates.first, coordinates.second - 1)
    val south = Pair(coordinates.first, coordinates.second + 1)
    val west = Pair(coordinates.first - 1, coordinates.second)
    val east = Pair(coordinates.first + 1, coordinates.second)

//    val spaces = " ".repeat(depth)
//    println("$spaces$currentHeight")

    visited.add(coordinates)

    var minDepth = Int.MAX_VALUE
    if (heightmap[coordinates.second][coordinates.first] == 'E') {
        minDepth = depth
    } else {
        if (!visited.contains(north) && north.second in heightmap.indices && effectiveHeight(heightmap[north.second][north.first]) <= currentHeight + 1) {
            minDepth = min(minDepth, doTheRecursiveThing(heightmap, visited, north, depth + 1))
        }
        if (!visited.contains(south) && south.second in heightmap.indices && effectiveHeight(heightmap[south.second][south.first]) <= currentHeight + 1) {
            minDepth = min(minDepth, doTheRecursiveThing(heightmap, visited, south, depth + 1))
        }
        if (!visited.contains(west) && west.first in heightmap[0].indices && effectiveHeight(heightmap[west.second][west.first]) <= currentHeight + 1) {
            minDepth = min(minDepth, doTheRecursiveThing(heightmap, visited, west, depth + 1))
        }
        if (!visited.contains(east) && east.first in heightmap[0].indices && effectiveHeight(heightmap[east.second][east.first]) <= currentHeight + 1) {
            minDepth = min(minDepth, doTheRecursiveThing(heightmap, visited, east, depth + 1))
        }
    }

    visited.remove(coordinates)
    return minDepth
}

fun effectiveHeight(mapHeight: Char): Int {
    return when (mapHeight) {
        in 'a'..'z' -> mapHeight.minus('a')
        'S' -> 0
        'E' -> 25
        else -> throw IllegalArgumentException()
    }
}
