package day09p1

import readInput

fun main() {

//    val data = readInput("day09/data_test")
    val data = readInput("day09/data")

    val lows = mutableListOf<Int>()

    for (y in 0 until data.size) {
        for (x in 0 until data[y].length) {

            val height = data.height(x, y)

            if (height >= data.height(x - 1, y)) continue
            if (height >= data.height(x + 1, y)) continue
            if (height >= data.height(x, y - 1)) continue
            if (height >= data.height(x, y + 1)) continue

            println("Low: $height at ($x, $y)")
            lows += height
        }
    }

    val risk = lows.map { it + 1 }.sum()

    println("Risk: $risk")

    /*
    Risk: 572
    */
}
fun List<String>.height(x: Int, y: Int): Int {
    if (y < 0 || y >= this.size || x < 0 || x >= this[y].length) {
        return Int.MAX_VALUE
    }

    return this[y][x].digitToInt()
}