package day16p2

import readInput

class Bits(val data: String) {

    var pc = 0

    var indention: String = ""

    fun execute() {
        val result = executeOperation()
        println("Final result: $result")
    }

    fun executeOperation(): Long {

        indention += "  "

//        out("Execute - pc: $pc, data: ${data.substring(pc)}")

        val version = take(3)
        val operation = take(3)

//        out("Ver: $version, op: $operation")

        val result = when (operation) {

            // Sum
            0L -> {
                out("Sum:")
                val values = takeValues()
                val result = values.sum()
                out("Result: $result = ${values.joinToString(" + ")}")
                result
            }

            // Product
            1L -> {
                out("Multiply:")
                val values = takeValues()
                val result = values.reduce { acc, i -> acc * i }
                out("Result: $result = ${values.joinToString(" * ")}")
                result
            }

            // Min
            2L -> {
                out("Min:")
                val values = takeValues()
                val result = values.minOf { it }
                out("Result: $result = min(${values.joinToString(", ")})")
                result
            }

            // Max
            3L -> {
                out("Max:")
                val values = takeValues()
                val result = values.maxOf { it }
                out("Result: $result = max(${values.joinToString(", ")})")
                result
            }

            // Literal
            4L -> {
                val result = takeLiteral()
                out("$result")
                result
            }

            // Greater than
            5L -> {
                out("Greater than:")
                val values = takeValues()
                val result = if (values[0] > values[1]) 1L else 0L
                out("Result: $result = ${values[0]} > ${values[1]}")
                result
            }

            // Lesser than
            6L -> {
                out("Lesser than:")
                val values = takeValues()
                val result = if (values[0] < values[1]) 1L else 0L
                out("Result: $result = ${values[0]} < ${values[1]}")
                result
            }

            // Equal
            7L -> {
                out("Equal:")
                val values = takeValues()
                val result = if (values[0] == values[1]) 1L else 0L
                out("Result: $result = ${values[0]} = ${values[1]}")
                result
            }

            // Operator
            else -> {
                throw IllegalArgumentException("Unknown operation: $operation, pc: $pc")
            }
        }

//        out("Result: $result")

        indention = indention.substring(2)

        return result
    }

    fun takeLiteral(): Long {
        var value = 0L
        do {
            val last = take(1) == 0L
            value = value shl 4
            value += take(4)
        } while (!last)

        return value
    }

    fun takeValues(): List<Long> {

        val values = mutableListOf<Long>()

        val lengthType = take(1)

        if (lengthType == 0L) {
            val length = take(15)
//            out("Subpacket length: $length bits")
            val nextOperationPc = pc + length
            while (pc < nextOperationPc) {
                values += executeOperation()
            }
        } else {
            val length = take(11)
//            out("Subpackets: $length")
            for (i in 0 until length) {
                values += executeOperation()
            }
        }

        return values
    }

    fun take(length: Int): Long {
        val value = data.substring(pc, pc+length).toLong(2)

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
