fun main() {

    fun part1(input: String): Any {
        return input.split("\n\n")
            .map {
                val lines = it.split("\n")
                Pair(DataTree.from(lines[0]), DataTree.from(lines[1]))
            }
            .mapIndexed { index, pair ->
                if (pair.first.compare(pair.second) >= 0) index + 1 else 0
            }
            .sum()
    }

    fun part2(input: String): Any {
        val dividerPackets = listOf(
            DataTree.from("[[2]]"),
            DataTree.from("[[6]]")
        )
        val dataPackets = input.split("\n")
            .filter { it.isNotBlank() }
            .map { DataTree.from(it) }
        val sortedPackets = (dividerPackets + dataPackets).sortedWith(DataTree::compare).reversed()
        val decoderKey1Index = sortedPackets.indexOf(dividerPackets[0]) + 1
        val decoderKey2Index = sortedPackets.indexOf(dividerPackets[1]) + 1
        return decoderKey1Index * decoderKey2Index
    }

    val input = readInput("input13.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

class DataTree private constructor(private val root: ListNode) {

    fun compare(other: DataTree): Int = compare(root, other.root)

    private fun compare(left: Node, right: Node): Int {
        if (left is ValueNode && right is ValueNode) {
            return if (left.value < right.value) 1 else if (left.value > right.value) -1 else 0
        } else if (left is ListNode && right is ListNode) {
            for (i in left.children.indices) {
                if (i >= right.children.size) return -1
                val childResult = compare(left.children[i], right.children[i])
                if (childResult != 0) return childResult
            }
            return if (left.children.size < right.children.size) 1 else 0
        } else {
            return compare(wrapValueNode(left), wrapValueNode(right))
        }
    }

    private fun wrapValueNode(node: Node): ListNode {
        return when (node) {
            is ValueNode -> ListNode(node.parent, mutableListOf(node))
            is ListNode -> node
            else -> throw AssertionError("Not possible")
        }
    }

    companion object {
        fun from(input: String): DataTree {
            val root = ListNode(null)
            var current: ListNode = root
            tokenize(input).forEach {
                when (it) {
                    "[" -> {
                        val newListNode = ListNode(parent = current)
                        current.children.add(newListNode)
                        current = newListNode
                    }

                    "]" -> {
                        current = current.parent!!
                    }

                    else -> {
                        current.children.add(ValueNode(parent = current, it.toInt()))
                    }
                }
            }
            return DataTree(root)
        }

        private fun tokenize(input: String) =
            "(\\d+|\\[|])".toRegex().findAll(input.substring(1 until input.length - 1))
                .map { it.groupValues[1] }
    }

    private open class Node(val parent: ListNode?)
    private class ListNode(parent: ListNode?, var children: MutableList<Node> = mutableListOf()) : Node(parent)
    private class ValueNode(parent: ListNode?, val value: Int) : Node(parent)
}

