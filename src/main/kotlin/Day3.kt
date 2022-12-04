fun main() {

    fun part1(input: String): Any {
        return input.split("\n")
            .sumOf {
                val compartments = Pair(it.subSequence(0, it.length / 2).toHashSet(), it.subSequence(it.length / 2, it.length).toHashSet())
                val commonItemType = compartments.first.intersect(compartments.second).first()
                itemTypePriority(commonItemType)
            }
    }

    fun part2(input: String): Any {
        return input.split("\n")
            .chunked(3)
            .sumOf {
                val rucksacks = it.map(String::toHashSet)
                val commonItemType = rucksacks[0].intersect(rucksacks[1]).intersect(rucksacks[2]).first()
                itemTypePriority(commonItemType)
            }
    }

    val input = readInput("input3.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

fun itemTypePriority(itemType: Char): Int = when (itemType) {
    in 'a'..'z' -> itemType.code - 96
    in 'A'..'Z' -> itemType.code - 38
    else -> throw IllegalArgumentException()
}
