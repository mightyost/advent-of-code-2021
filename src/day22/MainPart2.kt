package day22p2

import readInput
import java.lang.Math.abs

data class Cube(
    val on: Boolean,
    val x: LongRange,
    val y: LongRange,
    val z: LongRange
) {
    fun center(range: LongRange): Double = (range.last + range.first) / 2.0
    fun size(range: LongRange): Long = abs(range.last - range.first + 1)
    fun count(): Long = size(x) * size(y) * size(z)

    fun split(other: Cube): List<Cube> {

        if (!intersects(other)) {
            return listOf(other)
        }

        if (other.x.first < this.x.first) {
            return split(Cube(other.on, this.x.first..other.x.last, other.y.first..other.y.last, other.z.first..other.z.last)) +
                Cube(other.on, other.x.first until this.x.first, other.y.first..other.y.last, other.z.first..other.z.last)
        }

        if (other.x.last > this.x.last) {
            return split(Cube(other.on, other.x.first..this.x.last, other.y.first..other.y.last, other.z.first..other.z.last)) +
                Cube(other.on, this.x.last+1..other.x.last, other.y.first..other.y.last, other.z.first..other.z.last)
        }

        if (other.y.first < this.y.first) {
            return split(Cube(other.on, other.x.first..other.x.last, this.y.first..other.y.last, other.z.first..other.z.last)) +
                Cube(other.on, other.x.first..other.x.last, other.y.first until this.y.first, other.z.first..other.z.last)
        }

        if (other.y.last > this.y.last) {
            return split(Cube(other.on, other.x.first..other.x.last, other.y.first..this.y.last, other.z.first..other.z.last)) +
                Cube(other.on, other.x.first..other.x.last, this.y.last+1..other.y.last, other.z.first..other.z.last)
        }

        if (other.z.first < this.z.first) {
            return split(Cube(other.on, other.x.first..other.x.last, other.y.first..other.y.last, this.z.first..other.z.last)) +
                Cube(other.on, other.x.first..other.x.last,other.y.first..other.y.last, other.z.first until this.z.first)
        }

        if (other.z.last > this.z.last) {
            return split(Cube(other.on, other.x.first..other.x.last, other.y.first..other.y.last, other.z.first..this.z.last)) +
                Cube(other.on, other.x.first..other.x.last, other.y.first..other.y.last, this.z.last+1..other.z.last)
        }

        return listOf(other)
    }

    fun intersects(other: Cube): Boolean =
        abs(center(other.x) - center(this.x)) < size(other.x)/2.0 + size(this.x)/2.0 &&
        abs(center(other.y) - center(this.y)) < size(other.y)/2.0 + size(this.y)/2.0 &&
        abs(center(other.z) - center(this.z)) < size(other.z)/2.0 + size(this.z)/2.0

}

fun main() {

    val input = readData("day22/data")
//    val input = readData("day22/data_test")

    var space = mutableListOf(input[0])

    input
        .drop(1)
        .forEach { cube ->

            // Go through all existing cubes in space,
            // split the ones overlapping with new cube.
            // Remove existing now smaller parts fully contained
            // within new cube.
            space = space
                .flatMap { cube.split(it) }
                .filterNot { it.intersects(cube) }
                .toMutableList()

            if (cube.on) {
                space += cube
            }
        }

    val count = space.sumOf { it.count() }

    println("Count: $count")

    /*
    Count: 1387966280636636
    */
}

fun readData(name: String): List<Cube> {
    val data = readInput(name)

    val cubes = mutableListOf<Cube>()

    for (row in data) {
        val parts = row.split(" ", "=", ",", "..")

        val on = parts[0] == "on"
        val x = parts[2].toLong() .. parts[3].toLong()
        val y = parts[5].toLong() .. parts[6].toLong()
        val z = parts[8].toLong() .. parts[9].toLong()

        cubes += Cube(on, x, y, z)
    }
    return cubes
}