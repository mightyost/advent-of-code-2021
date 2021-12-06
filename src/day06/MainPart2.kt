package day06p2

fun main() {

//    var offsets = mutableListOf(3,4,3,1,2)
    val offsets = mutableListOf(1,1,1,3,3,2,1,1,1,1,1,4,4,1,4,1,4,1,1,4,1,1,1,3,3,2,3,1,2,1,1,1,1,1,1,1,3,4,1,1,4,3,1,2,3,1,1,1,5,2,1,1,1,1,2,1,2,5,2,2,1,1,1,3,1,1,1,4,1,1,1,1,1,3,3,2,1,1,3,1,4,1,2,1,5,1,4,2,1,1,5,1,1,1,1,4,3,1,3,2,1,4,1,1,2,1,4,4,5,1,3,1,1,1,1,2,1,4,4,1,1,1,3,1,5,1,1,1,1,1,3,2,5,1,5,4,1,4,1,3,5,1,2,5,4,3,3,2,4,1,5,1,1,2,4,1,1,1,1,2,4,1,2,5,1,4,1,4,2,5,4,1,1,2,2,4,1,5,1,4,3,3,2,3,1,2,3,1,4,1,1,1,3,5,1,1,1,3,5,1,1,4,1,4,4,1,3,1,1,1,2,3,3,2,5,1,2,1,1,2,2,1,3,4,1,3,5,1,3,4,3,5,1,1,5,1,3,3,2,1,5,1,1,3,1,1,3,1,2,1,3,2,5,1,3,1,1,3,5,1,1,1,1,2,1,2,4,4,4,2,2,3,1,5,1,2,1,3,3,3,4,1,1,5,1,3,2,4,1,5,5,1,4,4,1,4,4,1,1,2)

    val days = 256

    val spawns = LongArray(days + 1) { 0 }

    // Calc spawn days for start set
    for (offset in offsets) {
        for (day in offset+1 until spawns.size step 7) {
            spawns[day] += 1L
        }
    }

    // Go through spawn array and calc their spawns
    for (day in 1 until spawns.size) {
        val spawned = spawns[day]

        for (i in day + 9 until spawns.size step 7) {
            spawns[i] += spawned
        }
    }

    // Sum all spawned + initial size
    val total = spawns.sum() + offsets.size

    println("Total: $total")

    /*
    Total: 1681503251694
     */
}