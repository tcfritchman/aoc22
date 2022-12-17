fun main() {

    fun part1(input: String): Any {
        return input.split("\n")
            .map(::parseInstruction)
            .flatMap(Instruction::cycles)
            .runningFold(CPUState(1)) { acc, f -> f(acc) }
            .mapIndexed { index, cpuState -> (index + 1) * cpuState.x }
            .filterIndexed { index, _ -> (index - 19) % 40 == 0 }
            .sum()
    }

    fun part2(input: String): Any {
        return input.split("\n").count()
    }

    val input = readInput("input10.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

fun parseInstruction(it: String): Instruction {
    val args = it.split(" ")
    return when (args[0]) {
        "noop" -> NoOpInstruction()
        "addx" -> AddXInstruction(args[1].toInt())
        else -> throw IllegalArgumentException("Not a valid instruction")
    }
}

data class CPUState (val x: Int)

interface Instruction {
    fun cycles(): List<(CPUState) -> CPUState>
}

class NoOpInstruction : Instruction {
    override fun cycles(): List<(CPUState) -> CPUState> {
        return listOf({it}) // Identity function
    }
}

class AddXInstruction(val v: Int) : Instruction {
    override fun cycles(): List<(CPUState) -> CPUState> {
        return listOf({it}, {state: CPUState -> CPUState(state.x + v)})
    }
}

class CRT {

    private val currentPixel: Int
    private val pixels: List<Char>

    constructor() {
        currentPixel = 0
        pixels = List(240) {'.'}
    }

    private constructor(currentPixel: Int, pixels: List<Char>) {
        this.currentPixel = currentPixel
        this.pixels = pixels
    }

    fun atNextCycle(state: CPUState):CRT {
        // update pixels
        val nextPixel = currentPixel + 1
        val currentColumn = currentPixel % 6
        val spritePositions = state.x - 1 .. state.x + 1
        val updatedPixels = pixels.let {

        }
        return CRT(nextPixel, pixels)
    }
}
