package day08p1

import readInput

fun main() {

//    val data = readData("day08/data_test")
    val data = readData("day08/data")

    val count = data
        .flatMap { it.output }
        .filter { it.length in setOf(2, 4, 3, 7) }
        .size

    println("Count: $count")

    /*
    Count: 548
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
)