package day11p1

import readInput

fun main() {

//    val data = readData("day11/data_test_mini")
//    val data = readData("day11/data_test")
    val data = readData("day11/data")

    val steps = 200000

    for (i in 1..steps) {

        // Increase energy
        data.increase()

        val flashes = mutableSetOf<Pos>()

        // Check energy and Flash
        for (y in 0 until data.size) {
            for (x in 0 until data[y].size) {
                if (data.energy(x, y) > 9) {
                    data.flash(Pos(x, y), flashes)
                }
            }
        }

        // Reset energy if flashed
        data.reset()

//        println("Step: $i")
//        data.print()

        if (flashes.size == data.size * data[0].size) {
            println("All flashed at step: $i")
            return
        }
    }
    /*
    All flashed at step: 351
    */
}

data class Pos(val x: Int, val y:Int)

fun readData(filename: String): List<MutableList<Int>> = readInput(filename).map { it.toCharArray().map { it.digitToInt() }.toMutableList() }

fun List<MutableList<Int>>.increase() {
    for (y in 0 until this.size) {
        for (x in 0 until this[y].size) {
            this.increase(x, y)
        }
    }
}

fun List<MutableList<Int>>.isOut(x: Int, y:Int): Boolean {
    if (x < 0 || x >= this[0].size) return true
    if (y < 0 || y >= this.size) return true
    return false
}

fun List<MutableList<Int>>.increase(x: Int, y:Int) {
    this[y][x] += 1
}

fun List<MutableList<Int>>.reset() {
    for (y in 0 until this.size) {
        for (x in 0 until this[y].size) {
            if (energy(x, y) > 9) {
                this[y][x] = 0
            }
        }
    }
}

fun List<MutableList<Int>>.flash(pos: Pos, flashes: MutableSet<Pos>) {
    if (flashes.contains(pos)) {
        return
    }

    flashes += pos

    for (dy in -1 .. 1) {
        for (dx in -1 .. 1) {
            if (dx == 0 && dy == 0) {
                continue
            }

            val x = pos.x + dx
            val y = pos.y + dy

            if (isOut(x, y)) {
                continue
            }

            increase(x, y)

            if (energy(x, y) > 9) {
                flash(Pos(x, y), flashes)
            }
        }
    }
}

fun List<MutableList<Int>>.energy(x: Int, y:Int): Int = this[y][x]

fun List<MutableList<Int>>.print() {
    for (y in 0 until this.size) {
        for (x in 0 until this[y].size) {
            print(energy(x, y))
        }
        println()
    }
    println()
}