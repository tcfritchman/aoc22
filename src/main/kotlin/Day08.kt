fun main() {

    fun part1(input: String): Any {
        val map = input.split("\n")
            .map { line -> line.map(Char::digitToInt) }

        val visibleFromWest = buildList {
            for (i in map.indices) {
                var max = -1
                for (j in map[0].indices) {
                    if (map[i][j] > max) {
                        this.add(Pair(i, j))
                        max = map[i][j]
                    }
                }
            }
        }

        val visibleFromNorth = buildList {
            for (j in map[0].indices) {
                var max = -1
                for (i in map.indices) {
                    if (map[i][j] > max) {
                        this.add(Pair(i, j))
                        max = map[i][j]
                    }
                }
            }
        }

        val visibleFromEast = buildList {
            for (i in map.indices) {
                var max = -1
                for (j in map[0].indices.reversed()) {
                    if (map[i][j] > max) {
                        this.add(Pair(i, j))
                        max = map[i][j]
                    }
                }
            }
        }

        val visibleFromSouth = buildList {
            for (j in map[0].indices) {
                var max = -1
                for (i in map.indices.reversed()) {
                    if (map[i][j] > max) {
                        this.add(Pair(i, j))
                        max = map[i][j]
                    }
                }
            }
        }

        return (visibleFromNorth + visibleFromEast + visibleFromSouth + visibleFromWest)
            .distinct()
            .map { map[it.first][it.second] }
            .count()
    }

    fun part2(input: String): Any {
        val map = input.split("\n")
            .map { line -> line.map(Char::digitToInt) }

        val scores = buildList {
            for (i in map.indices) {
                for (j in map[0].indices) {
                    val currentHeight = map[i][j]

                    var north = 0
                    while (i - north > 0 && (north == 0 || map[i - north][j] < currentHeight)) {
                        north++
                    }

                    var south = 0
                    while (i + south < map.size - 1 && (south == 0 || map[i + south][j] < currentHeight)) {
                        south++
                    }

                    var west = 0
                    while (j - west > 0 && (west == 0 || map[i][j - west] < currentHeight)) {
                        west++
                    }

                    var east = 0
                    while (j + east < map[0].size - 1 && (east == 0 || map[i][j + east] < currentHeight)) {
                        east++
                    }

                    this.add(north * south * east * west)
                }
            }
        }

        return scores.max()
    }

    val input = readInput("input08.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}
