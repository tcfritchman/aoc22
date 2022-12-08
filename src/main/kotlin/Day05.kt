fun main() {

    fun part1(input: String): Any {
        val (moveLines, stackLines) = input.split("\n")
            .filter { it.contains('[') || it.startsWith("move") }
            .partition { it.startsWith("move") }

        val stacks = createStacks(stackLines)

        moveLines.forEach {
            val tokens = it.split(' ')
            val quantity = tokens[1].toInt()
            val source = tokens[3].toInt() - 1
            val destination = tokens[5].toInt() - 1
            moveStacks9000(quantity, stacks, source, destination)
        }

        return stacks.map(ArrayDeque<Char>::first).joinToString("")
    }

    fun part2(input: String): Any {
        val (moveLines, stackLines) = input.split("\n")
            .filter { it.contains('[') || it.startsWith("move") }
            .partition { it.startsWith("move") }

        val stacks = createStacks(stackLines)

        moveLines.forEach {
            val tokens = it.split(' ')
            val quantity = tokens[1].toInt()
            val source = tokens[3].toInt() - 1
            val destination = tokens[5].toInt() - 1
            moveStacks9001(quantity, stacks, source, destination)
        }

        return stacks.map(ArrayDeque<Char>::first).joinToString("")
    }

    val input = readInput("input05.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

fun createStacks(stackLines: List<String>): List<ArrayDeque<Char>> {
    val stackCount = (stackLines.maxOfOrNull(String::length)!!.plus(1)).floorDiv(4)

    val stacks = (0 until stackCount).map{ ArrayDeque<Char>() }

    for (line in stackLines) {
        (1 until line.length step 4)
            .onEachIndexed { stack: Int, index: Int ->
                if (line[index] != ' ') {
                    stacks[stack].addLast(line[index])
                }
            }
    }

    return stacks
}

fun moveStacks9000(
    quantity: Int,
    stacks: List<ArrayDeque<Char>>,
    source: Int,
    destination: Int
) {
    repeat(quantity) {
        val move = stacks[source].removeFirst()
        stacks[destination].addFirst(move)
    }
}

fun moveStacks9001(
    quantity: Int,
    stacks: List<ArrayDeque<Char>>,
    source: Int,
    destination: Int
) {
    val temp = mutableListOf<Char>()
    repeat(quantity) {
        temp.add(stacks[source].removeFirst())
    }
    stacks[destination].addAll(0, temp)
}
