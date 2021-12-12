package day12p1

import readInput

fun main() {

//    val connections = readInput("day12/data_test")
    val connections = readInput("day12/data")

    val caves = mutableMapOf<String, Cave>()

    for (connection in connections) {
        val names = connection.split("-")
        val name1 = names[0]
        val name2 = names[1]

        val cave1 = caves.computeIfAbsent(name1) { Cave(name1) }
        val cave2 = caves.computeIfAbsent(name2) { Cave(name2) }

        cave1.connections.add(cave2)
        cave2.connections.add(cave1)
    }

    val starts = caves.map { it.value }. filter { it.isStart() }

    val routes = mutableListOf<List<String>>()

    for (start in starts) {
        getRoutes(start, listOf(start.name), routes)
    }

    for (route in routes) {
        println(route)
    }
    println("Routes: ${routes.size}")

    /*
    Routes: 4691
    */
}

fun getRoutes(cave: Cave, visited: List<String>, routes: MutableList<List<String>>) {

    for (connection in cave.connections) {

        if (connection.isEnd()) {
            routes.add(visited + connection.name)
            continue
        }

        // Cannot visit a small cave again...
        if (!connection.isBig() && visited.contains(connection.name)) {
            continue
        }

        getRoutes(connection, visited + connection.name, routes)
    }
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
