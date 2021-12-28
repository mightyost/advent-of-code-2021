package day22p1

import readInput
import java.lang.Integer.max
import java.lang.Integer.min

data class Cube(
    val on: Boolean,
    val x: IntRange,
    val y: IntRange,
    val z: IntRange
)

class Space {
    val space = Array(101) { Array(101) { Array(101) { false } } }

    fun execute(cube: Cube) {
        val xmin = max(-50, cube.x.first)
        val xmax = min(50, cube.x.last)
        val ymin = max(-50, cube.y.first)
        val ymax = min(50, cube.y.last)
        val zmin = max(-50, cube.z.first)
        val zmax = min(50, cube.z.last)

        for (z in zmin..zmax) {
            for (y in ymin..ymax) {
                for (x in xmin..xmax) {
                    space[x+50][y+50][z+50] = cube.on
                }
            }
        }
    }

    fun countOn(): Int {
        var count = 0
        for (z in -50..50) {
            for (y in -50..50) {
                for (x in -50..50) {
                    if (space[x+50][y+50][z+50]) {
                        count++
                    }
                }
            }
        }
        return count
    }
}

fun main() {

//    val cubes = readData("day22/data_test")
    val cubes = readData("day22/data")

    val space = Space()

    for (cube in cubes) {
        space.execute(cube)
    }

    val count = space.countOn()

    println(count)
}

fun readData(name: String): List<Cube> {
    val data = readInput(name)

    val cubes = mutableListOf<Cube>()

    for (row in data) {
        val parts = row.split(" ", "=", ",", "..")

        val on = parts[0] == "on"
        val x = parts[2].toInt() .. parts[3].toInt()
        val y = parts[5].toInt() .. parts[6].toInt()
        val z = parts[8].toInt() .. parts[9].toInt()

        cubes += Cube(on, x, y, z)
    }
    return cubes
}