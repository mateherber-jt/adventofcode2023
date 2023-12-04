package day03

import println
import readInput
import kotlin.text.isDigit

// TODO never generalize & cleanup
fun part1(input: List<String>): Int {
    val validPos = mutableSetOf<Pair<Int, Int>>()
    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, c ->
            if (!c.isDigit() && c != '.') {
                validPos += row - 1 to column - 1
                validPos += row - 1 to column
                validPos += row - 1 to column + 1
                validPos += row to column - 1
                validPos += row to column + 1
                validPos += row + 1 to column - 1
                validPos += row + 1 to column
                validPos += row + 1 to column + 1
            }
        }
    }
    var accumulator = ""
    var valid = false
    var result = 0
    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, c ->
            if (c.isDigit()) {
                accumulator += c
                if (!valid && validPos.contains(row to column)) {
                    valid = true
                }
            } else {
                if (accumulator != "" && valid) {
                    result += accumulator.toInt()
                }
                accumulator = ""
                valid = false
            }
        }
        if (accumulator != "" && valid) {
            result += accumulator.toInt()
        }
        accumulator = ""
        valid = false
    }
    return result
}

fun part2(input: List<String>): Int {
    val validPos = mutableSetOf<Pair<Int, Int>>()
    val validPos2 = mutableMapOf<List<Pair<Int, Int>>, MutableList<Int>>()
    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, c ->
            if (!c.isDigit() && c == '*') {
                validPos2[listOf(row - 1 to column - 1,
                        row - 1 to column,
                        row - 1 to column + 1,
                        row to column - 1,
                        row to column + 1,
                        row + 1 to column - 1,
                        row + 1 to column,
                        row + 1 to column + 1)] = mutableListOf()
            }
        }
    }
    var accumulator = ""
    val keys = mutableSetOf<List<Pair<Int, Int>>>()
    input.forEachIndexed { row, line ->
        line.forEachIndexed { column, c ->
            if (c.isDigit()) {
                accumulator += c
                validPos2.keys.filter {
                    it.contains(row to column)
                }.forEach {
                    keys += it
                }
            } else {
                keys.forEach {
                    validPos2[it]!!.add(accumulator.toInt())
                }
                accumulator = ""
                keys.clear()
            }
        }
        keys.forEach {
            validPos2[it]!!.add(accumulator.toInt())
        }
        accumulator = ""
        keys.clear()
    }
    return validPos2.values.filter { it.size == 2 }.sumOf { it[0] * it[1] }
}

fun main() {
    val testInput = readInput("\\day03\\Day03_test")
    val testInput2 = readInput("\\day03\\Day03_test2")
    check(part1(testInput) == 4361)
    check(part2(testInput2) == 467835)

    val input = readInput("\\day03\\Day03")
    part1(input).println()
    part2(input).println()
}
