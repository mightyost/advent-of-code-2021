fun main() {
    fun part1(input: List<String>): Int {

        var count = 0;

        for (i in 1..9) {
            if (input[i-1] < input[i]) {
                count++
            }
        }

        return count
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val testInput = readInput("dayXX/data_test")
    println(part1(testInput))

    val input = readInput("dayXX/data")
//    println(part1(input))
}
