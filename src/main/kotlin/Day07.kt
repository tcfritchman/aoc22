import Filesystem.NodeType.DIR
import Filesystem.NodeType.FILE

fun main() {

    fun part1(input: String): Any {
        val fs = createFilesystem(input)
        return fs.dirSizes().filter { it <= 100000 }.sum()
    }

    fun part2(input: String): Any {
        val fs = createFilesystem(input)
        val spaceNeeded = 30000000 - fs.unusedSpace()
        return fs.dirSizes().filter { it >= spaceNeeded }.min()
    }

    val input = readInput("input07.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

class Filesystem {

    enum class NodeType { FILE, DIR }

    class Node(val type: NodeType, val name: String, val parent: Node? = null, var size: Int = 0) {
        val children = mutableListOf<Node>()
        fun findChild(name: String): Node = children.first { it.name == name }
    }

    private val storageSpace = 70000000
    private val root = Node(DIR, "")
    private var curr = root

    fun enterDir(name: String) {
        curr = curr.findChild(name)
    }

    fun exitDir() {
        curr = curr.parent!!
    }

    fun createDir(name: String) {
        curr.children.add(Node(DIR, name, curr))
    }

    fun createFile(name: String, size: Int) {
        curr.children.add(Node(FILE, name, curr, size))
        var n: Node? = curr
        while (n != null) {
            n.size += size
            n = n.parent
        }
    }

    fun unusedSpace(): Int = storageSpace - root.size

    fun dirSizes(): List<Int> = traverse(root).map(Node::size)

    private fun traverse(n: Node): List<Node> = if (n.type == DIR) {
        listOf(n) + n.children.flatMap { traverse(it) }
    } else {
        listOf()
    }
}

private fun createFilesystem(input: String): Filesystem {
    val fs = Filesystem()
    for (line in input.split("\n")) {
        if (line.startsWith("$ cd /") || line.startsWith("$ ls")) {
            // Ignore, these don't do anything
        } else if (line.startsWith("$ cd ..")) {
            fs.exitDir()
        } else if (line.startsWith("$ cd")) {
            fs.enterDir(line.split(" ")[2])
        } else if (line.startsWith("dir")) {
            fs.createDir(line.split(" ")[1])
        } else {
            fs.createFile(line.split(" ")[1], line.split(" ")[0].toInt())
        }
    }
    return fs
}