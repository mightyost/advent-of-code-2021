package day18p1

import readInput

class Node {
    var parent: Node? = null
    var left: Node? = null
    var right: Node? = null
    var value: Int? = null

    fun createLeftChild(): Node {
        val child = Node()
        child.parent = this
        left = child
        return child
    }

    fun createRightChild(): Node {
        val child = Node()
        child.parent = this
        right = child
        return child
    }

    fun isLeftChild() = this == this.parent?.left
    fun isRightChild() = this == this.parent?.right

    fun add(value: Node): Node {
        val root = Node()

        root.left = this
        root.right = value

        this.parent = root
        value.parent = root

        return root
    }

    fun leftValue(): Node? {
        var node: Node? = this
        while (node!!.parent != null && node.isLeftChild()) {
            node = node.parent
        }

        node = node.parent?.left ?: return null

        while (node!!.value == null) {
            node = node.right
        }

        return node
    }

    fun rightValue(): Node? {
        var node: Node? = this
        while (node!!.parent != null && node.isRightChild()) {
            node = node.parent
        }

        node = node.parent?.right ?: return null

        while (node!!.value == null) {
            node = node.left
        }

        return node
    }

    fun explode() {
        leftValue()?.let {
            it.value = it.value!! + this.left!!.value!!
        }

        rightValue()?.let {
            it.value = it.value!! + this.right!!.value!!
        }

        this.right = null
        this.left = null
        this.value = 0
    }

    fun split() {
        val left = this.value!! / 2
        val right = this.value!! - left
        createLeftChild().value = left
        createRightChild().value = right
        this.value = null
    }

    override fun toString(): String {
        return if (value != null) "$value" else "[${left},${right}]"
    }
}

fun findExplodeNode(node: Node, depht: Int = 0): Node? {
    if (node.value == null) {
        return findExplodeNode(node.left!!, depht + 1)
            ?: findExplodeNode(node.right!!, depht + 1)
    }

    if (depht > 4) {
        return node.parent
    }

    return null
}

fun findNodeToSplit(node: Node): Node? {
    if (node.value == null) {
        return findNodeToSplit(node.left!!)
            ?: findNodeToSplit(node.right!!)
    }

    if (node.value!! > 9) {
        return node
    }
    return null
}

fun magnitude(node: Node): Int {
    if (node.value != null) {
        return node.value!!
    }

    return 3 * magnitude(node.left!!) + 2 * magnitude(node.right!!)
}

fun splitInput(input: String): Pair<String, String> {
    var left = ""

    var depth = 0

    for (i in 1 until input.length) {
        left += input[i]
        when (input[i]) {
            '[' -> depth++
            ']' -> depth--
        }
        if (depth == 0) {
            break
        }
    }

    val right = input.substring(left.length + 2, input.length-1)

    return Pair(left, right)
}

fun parseInput(input: String, parent: Node) {

    val firstChild = parent.createLeftChild()
    val lastChild = parent.createRightChild()

    val childs = splitInput(input)

    if (childs.first.contains(",")) {
        parseInput(childs.first, firstChild)
    } else {
        firstChild.value = childs.first.toInt()
    }

    if (childs.second.contains(",")) {
        parseInput(childs.second, lastChild)
    } else {
        lastChild.value = childs.second.toInt()
    }
}

fun main() {

//    val data = readInput("day18/data_test")
    val data = readInput("day18/data")

    var first = Node()
    parseInput(data[0], first)

    for (i in 1 until data.size) {
        val second = Node()
        parseInput(data[i], second)

        val sum = first.add(second)

        println("After addition: $sum")

        while (true) {
            val nodeToExplode = findExplodeNode(sum)

            if (nodeToExplode != null) {
                nodeToExplode.explode()
                println("After explode: $sum : $nodeToExplode")
                continue
            }

            val nodeToSplit = findNodeToSplit(sum)

            if (nodeToSplit != null) {
                nodeToSplit.split()

                println("After split: $sum : $nodeToSplit")
                continue
            }

            break
        }

        first = sum
    }

    println("Magnitude: ${magnitude(first)}")
}
