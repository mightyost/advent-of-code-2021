package day16p1

import readInput

class Bits(val data: String) {

    var pc = 0

    var versionSum = 0

    var indention: String = ""

    fun execute() {
        val result = executeOperation()
        println("Sum of versions: $versionSum")
    }

    fun executeOperation(): Int {

        indention += "  "

        out("Execute - pc: $pc, data: ${data.substring(pc)}")

        val version = take(3)
        val operation = take(3)

        out("Ver: $version, op: $operation")

        versionSum += version

        val result = when (operation) {

            // Sum
            0 -> takeValues().sum()

            // Product
            1 -> takeValues().reduce { acc, i -> acc * i }

            // Min
            2 -> takeValues().minOf { it }

            // Max
            3 -> takeValues().maxOf { it }

            // Literal
            4 -> {
                takeLiteral()
            }

            // Greater than
            5 -> {
                val values = takeValues()
                if (values[0] > values[1]) 1 else 0
            }

            // Lesser than
            6 -> {
                val values = takeValues()
                if (values[0] < values[1]) 1 else 0
            }

            // Equal
            7 -> {
                val values = takeValues()
                if (values[0] == values[1]) 1 else 0
            }

            // Operator
            else -> {
                throw IllegalArgumentException("Unknown operation: $operation, pc: $pc")
            }
        }

        out("Result: $result")

        indention = indention.substring(2)

        return result
    }

    fun takeLiteral(): Int {
        var value = 0
        do {
            val last = take(1) == 0
            value = value shl 4
            value += take(4)
        } while (!last)

        return value
    }

    fun takeValues(): List<Int> {

        val values = mutableListOf<Int>()

        val lengthType = take(1)

        if (lengthType == 0) {
            val length = take(15)
            out("Subpacket length: $length bits")
            val nextOperationPc = pc + length
            while (pc < nextOperationPc) {
                values += executeOperation()
            }
        } else {
            val length = take(11)
            out("Subpackets: $length")
            for (i in 0 until length) {
                values += executeOperation()
            }
        }

        return values
    }

    fun take(length: Int): Int {
        val value = data.substring(pc, pc+length).toInt(2)

        pc += length

        return value
    }

    fun out(string: String) {
        println("$indention$string")
    }
}

fun main() {

//    val data = readInput("day16/data_test")[0]
    val data = readInput("day16/data")[0]

    Bits(data.toBinString()).execute()
}

fun String.toBinString(): String = this.windowed(2, 2)
    .map { it.toInt(16).toString(2) }
    .map { it.padStart(8, '0') }
    .joinToString("")
