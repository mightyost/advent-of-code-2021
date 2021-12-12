package day12p2

import readInput

fun main() {

//    val connections = readInput("day12/data_test")
    val connections = readInput("day12/data")

    val caves = mutableMapOf<String, Cave>()

    // Parse and setup caves model
    for (connection in connections) {
        val names = connection.split("-")
        val name1 = names[0]
        val name2 = names[1]

        val cave1 = caves.computeIfAbsent(name1) { Cave(name1) }
        val cave2 = caves.computeIfAbsent(name2) { Cave(name2) }

        cave1.connections.add(cave2)
        cave2.connections.add(cave1)
    }

    // Get start caves
    val starts = caves.map { it.value }. filter { it.isStart() }

    val routes = mutableSetOf<List<String>>()

    // Walk the model and resolve possible routes
    for (start in starts) {
        findRoutes(start, listOf(start.name), routes)
    }

    println("Routes: ${routes.size}")

    /*
    Routes: 140718
    */
}

// Recursively find out possible routes
fun findRoutes(cave: Cave, visited: List<String>, routes: MutableSet<List<String>>) {

    for (connection in cave.connections) {

        // Are we done?
        if (connection.isEnd()) {
            routes.add(visited + connection.name)
            continue
        }

        // Cannot revisit "start" cave
        if (connection.isStart()) {
            continue
        }

        // Cannot revisit a small cave twice...
        if (!visited.canVisit(connection)) {
            continue
        }

        findRoutes(connection, visited + connection.name, routes)
    }
}

fun List<String>.canVisit(cave: Cave): Boolean {

    // It's ok to (re)visit big caves
    if (cave.isBig()) {
        return true
    }

    // Take all small caves visited and group them
    val visited = this.filter { it == it.lowercase() }.groupBy { it }

    // Number of revisits
    val revisits = visited.count { it.value.size > 1 }

    // No revisits yet, then it's ok to visit cave
    if (revisits == 0) {
        return true
    }

    // There is already a revisited cave, it's not allowed to another revisit...
    return !visited.containsKey(cave.name)
}

class Cave(
    val name: String
) {
    val connections = mutableSetOf<Cave>()

    fun isBig(): Boolean = name != name.lowercase()
    fun isStart(): Boolean = name == "start"
    fun isEnd(): Boolean = name == "end"

    override fun toString(): String {
        return name
    }
}