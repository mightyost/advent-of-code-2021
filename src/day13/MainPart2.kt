package day13p1

import readInput

fun main() {

//    val name = "day13/data_test"
    val name = "day13/data"

    var coords = readCoords(name)
    val folds = readFolds(name)

    for (fold in folds) {

        val folded = mutableListOf<List<Int>>()

        val x = fold[0]
        val y = fold[1]

        // Fold around y?
        if (y > 0) {
            for (coord in coords) {
                if (coord[1] == y) {
                    continue
                }
                if (coord[1] < y) {
                    folded += coord
                } else {
                    folded += listOf(coord[0], y - (coord[1]-y))
                }
            }
        }

        // Fold around x?
        if (x > 0) {
            for (coord in coords) {
                if (coord[0] == x) {
                    continue
                }
                if (coord[0] < x) {
                    folded += coord
                } else {
                    folded += listOf(x - (coord[0]-x), coord[1])
                }
            }
        }

        coords = folded
    }

    coords.print()
    val dots = coords.toSet().size
    println("Dots: $dots")

    /*
    .##..###..#..#.####.###...##..#..#.#..#
    #..#.#..#.#..#....#.#..#.#..#.#..#.#..#
    #..#.#..#.####...#..#..#.#....#..#.####
    ####.###..#..#..#...###..#....#..#.#..#
    #..#.#.#..#..#.#....#....#..#.#..#.#..#
    #..#.#..#.#..#.####.#.....##...##..#..#
    Dots: 102
     */
}

fun readCoords(filename: String): List<List<Int>> = readInput(filename)
    .filter { !it.isEmpty() }
    .filter {
        it[0].isDigit()
    }
    .map { it.split(",")
        .map { it.toInt() }}

fun readFolds(filename: String): List<List<Int>> = readInput(filename)
    .filter { it.startsWith("fold") }
    .map { it.split(" ")[2] }
    .map { it.split("=") }
    .map { if (it[0] == "x") listOf(it[1].toInt(), 0 ) else listOf(0, it[1].toInt()) }

fun Collection<List<Int>>.print() {
    val W = this.maxOf { it[0] } + 1
    val H = this.maxOf { it[1] } + 1

    for (y in 0 until H) {
        for (x in 0 until W) {
            if (this.contains(listOf(x, y))) {
                print("#")
            } else {
                print(".")
            }
        }
        println()
    }
}

//fun readDots(filename: String): Array<Array<Int>> {
//    val data = readInput(filename).filter { it[0].isDigit() }.map { it.split(",") }
//
//    val W = data.maxOf { it[1].toInt() } + 1
//    val H = data.maxOf { it[1].toInt() } + 1
//
//    val paper = Array(H) { Array(W) { 0 } }
//
//    return null
//}

/*

...#..#..#.
....#......
...........
#..........
...#....#.#
...........
...........
-----------
...........
...........
.#....#.##.
....#......
......#...#
#..........
#.#........


6,10
0,14
9,10
0,3
10,4
4,11
6,0
6,12
4,1
0,13
10,12
3,4
3,0
8,4
1,10
2,14
8,10
9,0

fold along y=7
fold along x=5
 */