package day09p2

import readInput

fun main() {

//    val data = readInput("day09/data_test")
    val data = readInput("day09/data")

    val lows = mutableListOf<Int>()
    val sizes = mutableListOf<Int>()

    for (y in 0 until data.size) {
        for (x in 0 until data[y].length) {

            val height = data.height(x, y)

            if (height >= data.height(x - 1, y)) continue
            if (height >= data.height(x + 1, y)) continue
            if (height >= data.height(x, y - 1)) continue
            if (height >= data.height(x, y + 1)) continue

            lows += height

            sizes += data.basin(Pos(x, y), mutableSetOf()).size
        }
    }

    val risk = lows.map { it + 1 }.sum()

    println("Risk: $risk")

    sizes.sortDescending()

    val product = sizes[0] * sizes[1] * sizes[2]

    println("Product: $product")
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

fun List<String>.basin(pos:Pos, lower: MutableSet<Pos>): Set<Pos> {

    // Out of bounds?
    if (pos.y < 0 || pos.y >= this.size || pos.x < 0 || pos.x >= this[pos.y].length) {
        return lower
    }

    // Already tested?
    if (lower.contains(pos)) {
        return emptySet()
    }

    // Barrier?
    if (height(pos.x, pos.y) >= 9) {
        return lower
    }

    lower += pos

    return basin(Pos(pos.x + 1, pos.y), lower) +
            basin(Pos(pos.x - 1, pos.y), lower) +
            basin(Pos(pos.x, pos.y + 1), lower) +
            basin(Pos(pos.x, pos.y - 1), lower)
}

data class Pos(val x: Int, val y:Int)