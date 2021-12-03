package day03p2

import readInput

fun main() {

//    val data = readInput("day03/data_test")
    val data = readInput("day03/data")

    var o2 = data
    var co2 = data

    for (i in 0 until data[0].length) {
        o2 = mostCommon(o2, i)
        co2 = leastCommon(co2, i)
    }

    val o2Rating = o2[0].toInt()
    val co2Rating = co2[0].toInt()

    println("O2: $o2 ($o2Rating)")
    println("CO2: $co2 ($co2Rating)")

    val lifeRating = o2Rating * co2Rating

    println("Life rating: $lifeRating")

    /*
    O2: [111101010001] (3921)
    CO2: [001101000100] (836)
    Life rating: 3277956
     */
}

fun mostCommon(data: List<String>, pos: Int): List<String> {

    val ones = mutableListOf<String>()
    val zeros = mutableListOf<String>()

    for (value in data) {
        if (value[pos] == '1') {
            ones += value
        } else {
            zeros += value
        }
    }

    return if (ones.size >= zeros.size) ones else zeros
}

fun leastCommon(data: List<String>, pos: Int): List<String> {

    if (data.size == 1) {
        return data
    }

    val ones = mutableListOf<String>()
    val zeros = mutableListOf<String>()

    for (value in data) {
        if (value[pos] == '1') {
            ones += value
        } else {
            zeros += value
        }
    }

    return if (ones.size < zeros.size) ones else zeros
}

fun String.toInt() = Integer.parseInt(this, 2);