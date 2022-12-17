import java.math.BigInteger

fun main() {

    fun part1(input: String): Any {
        val simulation = MonkeyInTheMiddle(input, divideWorryLevel = true)
        simulation.run(rounds=20)
        return simulation.monkeys.map { it.inspectionCount }.sorted().takeLast(2).reduce { a, b -> a * b }
    }

    fun part2(input: String): Any {
        val simulation = MonkeyInTheMiddle(input, divideWorryLevel = false)
        simulation.run(rounds=10000)
        return simulation.monkeys.map { it.inspectionCount }.sorted().takeLast(2).reduce { a, b -> a * b }
    }

    val input = readInput("inputX.txt")
    printAnswer { part1(input) }
    printAnswer { part2(input) }
}

class MonkeyInTheMiddle(input: String, divideWorryLevel: Boolean) {
    val monkeys: List<Monkey>;

    init {
        monkeys = input.split("\n\n")
            .map { it.split("\n") }
            .map {monkeyInfo ->
                val monkeyIndex = "Monkey (\\d+):\$".toRegex().find(monkeyInfo[0])!!.groupValues[1].toInt()
                val items = "Starting items: (.*)$".toRegex().find(monkeyInfo[1])!!.groupValues[1].split(",").map { it.trim().toBigInteger() }.toMutableList()
                val operation = Operation(monkeyInfo[2])
                val test = Test(monkeyInfo[3], monkeyInfo[4], monkeyInfo[5])
                Monkey(monkeyIndex, items, operation, test, divideWorryLevel)
            }
    }

    fun run(rounds: Int) {
        repeat(rounds) {
            for (monkey in monkeys) {
                val throwList = monkey.takeTurn()
                throwList.forEach { monkeys[it.destinationMonkeyIndex].addItem(it.itemWorryLevel) }
            }
        }
    }
}

class Monkey(val index: Int, private val itemWorryLevels: MutableList<BigInteger>, private val operation: Operation, private val test: Test, private val divideWorryLevel: Boolean) {
    var inspectionCount = 0

    fun takeTurn(): List<Throw> {
        val throwList = mutableListOf<Throw>()
        for (oldWorryLevel in itemWorryLevels) {
            inspectionCount++
            val newWorryLevel = operation.doOperation(oldWorryLevel)
            val currentWorryLevel = if (divideWorryLevel) newWorryLevel.divide(BigInteger.valueOf(3)) else newWorryLevel
            val destinationMonkeyIndex = test.doTest(currentWorryLevel)
            throwList.add(Throw(currentWorryLevel, destinationMonkeyIndex))
        }
        itemWorryLevels.clear()
        return throwList
    }

    fun addItem(item: BigInteger) {
        itemWorryLevels.add(item)
    }
}

class Operation(expression: String) {
    private val lhs: (BigInteger) -> BigInteger
    private val rhs: (BigInteger) -> BigInteger
    private val op: (BigInteger, BigInteger) -> BigInteger

    init {
        val params = "Operation: new = (.*) (.*) (.*)\$".toRegex().find(expression)!!.groupValues
        val leftHandValue = params[1]
        val operator = params[2]
        val rightHandValue = params[3]

        println("$leftHandValue, $rightHandValue, $operator")

        lhs = if (leftHandValue == "old") { oldVal: BigInteger -> oldVal } else { _: BigInteger -> leftHandValue.toBigInteger() }
        rhs = if (rightHandValue == "old") { oldVal: BigInteger -> oldVal } else { _: BigInteger -> rightHandValue.toBigInteger() }
        op = if (operator == "+") { l: BigInteger, r: BigInteger -> l.plus(r) } else { l: BigInteger, r: BigInteger -> l.multiply(r) }
    }

    fun doOperation(oldVal: BigInteger): BigInteger = op(lhs(oldVal), rhs(oldVal))
}

class Test(condition: String, trueResult: String, falseResult: String) {
    private val divisibleByConditionValue: BigInteger
    private val trueResultMonkeyIndex: Int
    private val falseResultMonkeyIndex: Int

    init {
        divisibleByConditionValue = "Test: divisible by (.*)\$".toRegex().find(condition)!!.groupValues[1].toBigInteger()
        trueResultMonkeyIndex = "If true: throw to monkey (.*)\$".toRegex().find(trueResult)!!.groupValues[1].toInt()
        falseResultMonkeyIndex = "If false: throw to monkey (.*)\$".toRegex().find(falseResult)!!.groupValues[1].toInt()

        println("$divisibleByConditionValue, $trueResultMonkeyIndex, $falseResultMonkeyIndex")
    }

    fun doTest(worryLevel: BigInteger) = if (worryLevel.mod(divisibleByConditionValue) == BigInteger.ZERO) trueResultMonkeyIndex else falseResultMonkeyIndex
}

data class Throw(val itemWorryLevel: BigInteger, val destinationMonkeyIndex: Int)