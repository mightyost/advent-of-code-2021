package day17

import kotlin.math.absoluteValue
import kotlin.system.measureTimeMillis

/**
 * https://github.com/spyroid/Advent-of-Code-2021/blob/master/src/day17/Day17.kt
 */
class Simulation(val x1: Int, val x2: Int, val y1: Int, val y2: Int) {

    data class Bullet(var dx: Int, var dy: Int, var x: Int = 0, var y: Int = 0) {
        var maxY = y

        fun step() {
            x += dx
            y += dy--
            if (dx > 0) dx -= 1 else dx = 0
            maxY = maxOf(maxY, y)
        }
    }

    private fun simulate(x: Int, y: Int): Bullet? {
        val b = Bullet(x, y)
        while (b.y >= y1 && b.x <= x2) {
            b.step()
            if (b.x in x1..x2 && b.y in y1..y2) return b
        }
        return null
    }

    fun find(): Pair<Int, Int> {
        var maxY = -1
        var count = 0

        for (x in 1..x2) {
            for (y in y1..y1.absoluteValue) {
                val res = simulate(x, y)
                if (res != null) {
                    maxY = maxOf(maxY, res.maxY)
                    count++
                }
            }
        }
        return Pair(maxY, count)
    }
}

fun main() {
    var res = Simulation(20, 30, -10, -5).find()
    check(res.first == 45) { "Expected 45 but got ${res.first}" }
    check(res.second == 112) { "Expected 112 but got ${res.second}" }

    measureTimeMillis {
        // target area: x=34..67, y=-215..-186
        res = Simulation(34, 67, -215, -186).find()
    }.also { time ->
        println("⭐️ Part1: ${res.first} in $time ms")
        println("⭐️ Part2: ${res.second} in $time ms")
    }

    /*
    ⭐️ Part1: 23005 in 9 ms
    ⭐️ Part2: 2040 in 9 ms
    */
}