package day08p2

import readInput

fun main() {

//    val data = readData("day08/data_test")
    val data = readData("day08/data")

    val count = data
        .flatMap { it.output }
        .filter { it.length in setOf(2, 4, 3, 7) }
        .size

    println("Count: $count")

    val sum = data.map { it.decode() }.sum()

    println("Sum: $sum")

    /*
    Count: 548
    Sum: 1074888
    */
}

fun readData(filename: String): List<Output> {
    val rows = readInput(filename)

    val outputs = mutableListOf<Output>()

    for (row in rows) {
        val pair = row.split(" | ")
        val digits = pair[0]
            .split(" ")
        val output = pair[1]
            .split(" ")

        outputs += Output(digits, output)
    }

    return outputs
}

data class Output(
    val digits: List<String>,
    val output: List<String>
) {
    fun decode(): Int {

        // Mapping of segments to the list of digit's segment count (where it is used)
        val segments = mutableMapOf<Char, MutableList<Int>>()

        // Calc list of digit's segment count per segment
        for (digit in digits) {
            for (segment in digit) {
                if (segments[segment] == null) {
                    segments[segment] = mutableListOf()
                }
                segments[segment]!!.add(digit.length)
            }
        }

        // Resolve and map the segments to their right segment
        val segmentMapping = mapOf(
            segments.filter { it.value.size == 4 }.map { it.key }.first() to 'e',
            segments.filter { it.value.size == 6 }.map { it.key }.first() to 'b',
            segments.filter { it.value.size == 7 }.filter { it.value.contains(4) }.map { it.key }.first() to 'd',
            segments.filter { it.value.size == 7 }.filter { !it.value.contains(4) }.map { it.key }.first() to 'g',
            segments.filter { it.value.size == 8 }.filter { !it.value.contains(4) }.map { it.key }.first() to 'a',
            segments.filter { it.value.size == 8 }.filter { it.value.contains(4) }.map { it.key }.first() to 'c',
            segments.filter { it.value.size == 9 }.map { it.key }.first() to 'f'
        )

        // Transform wrong input to corrected and sorted
        val correctOutput = output
            .map {
                it.toCharArray()
                    .map { segmentMapping[it]!! }
                    .sorted()
                    .joinToString("")
            }

        // Mapping of segments to its digit
        val segmentsDigit = mapOf(
            "abcefg" to 0,
            "cf" to 1,
            "acdeg" to 2,
            "acdfg" to 3,
            "bcdf" to 4,
            "abdfg" to 5,
            "abdefg" to 6,
            "acf" to 7,
            "abcdefg" to 8,
            "abcdfg" to 9,
        )

        // Concatenate output and convert it to an int
        return correctOutput.joinToString("") { segmentsDigit[it]!!.toString() }.toInt()
    }
}