package day20p1

import readInput

fun main() {

//    val name = "day20/data_test"
    val name = "day20/data"

    var data = readInput(name)
        .map { it.replace('.', '0') }
        .map { it.replace('#', '1') }

    val algo = data[0]

    data = data.drop(2)

    var pixels = mutableSetOf<Pair<Int, Int>>()

    for (y in 0 until data.size) {
        for (x in 0 until data[y].length) {
            if (data[y][x] == '1') {
                pixels += Pair(x, y)
            }
        }
    }

    var border = '0'

    for (i in 1 .. 2) {

        val minX = pixels.minOf { it.first }
        val maxX = pixels.maxOf { it.first }
        val minY = pixels.minOf { it.second }
        val maxY = pixels.maxOf { it.second }

        val newPixels = mutableSetOf<Pair<Int, Int>>()
        for (y in (minY-1)..(maxY+1)) {
            for (x in (minX-1)..(maxX+1)) {
                var index = "0"
                for (dy in -1..1) {
                    for (dx in -1..1) {
                        if (x + dx < minX || x + dx > maxX || y + dy < minY || y + dy > maxY) {
                            index += border
                        } else
                        if (Pair(x + dx, y + dy) in pixels) {
                            index += 1
                        } else {
                            index += 0
                        }
                    }
                }
                val pixel = algo[index.toInt(2)]

                if (pixel == '1') {
                    newPixels += Pair(x, y)
                }
            }
        }

        pixels = newPixels

        if (border == '0') {
            border = algo.first()
        } else {
            border = algo.last()
        }
    }

    pixels.print()
    println("Number of lit pixels: ${pixels.size}")

    /*
    Number of lit pixels: 5275
    */
}

fun Set<Pair<Int, Int>>.print() {
    var txt = ""

    val minX = minOf { it.first }
    val maxX = maxOf { it.first }
    val minY = minOf { it.second }
    val maxY = maxOf { it.second }

    for (y in minY..maxY) {
        for (x in minX..maxX) {
            if (Pair(x, y) in this) {
                txt += "#"
            } else {
                txt += "."
            }
        }
        txt += "\n"
    }

    println(txt)
}

// 5275