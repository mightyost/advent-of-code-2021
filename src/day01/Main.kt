package day01

import readIntInput

fun main() {
    fun part1(input: List<Int>): Int {

        var count = 0;

        for (i in 1 .. input.size-1) {
            if (input[i-1] < input[i]) {
                count++
                println("${input[i]} (increased) $count")
            } else {
                println("${input[i]} (decreased)")
            }
        }

        return count
    }

    fun part2(input: List<Int>): Int {

        var count = 0

        for (i in 0 .. input.size-1-3) {

            val sum1 = input[i+0] + input[i+1] + input[i+2]
            val sum2 = input[i+1] + input[i+2] + input[i+3]

            if (sum2 > sum1) {
                count++
                println("${sum1} ${sum2} (increased) $count")
            } else {
                println("${sum1} ${sum2} (decreased)")
            }
        }

        return count
    }

    val testInput = readIntInput("day01/data_test")
    println(part2(testInput))

    val input = readIntInput("day01/data")
    println(part2(input))
}
