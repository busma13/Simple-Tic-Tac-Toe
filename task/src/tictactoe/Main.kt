package tictactoe
import java.util.Scanner

fun main() {
    val scanner = Scanner(System.`in`)
    var gameOngoing = true // Useful when we add ability to play multiple times
    var c = "         "
    var whoseTurn = 'X'

    // Print the initial empty game grid
    printGrid(c)

    // Loops until "gameOngoing" becomes false: there is a winner or a draw
    while (gameOngoing) {

        var newCellsString = ""
        var badInput = true
        var totalEmpty = 0
        var winner = false

        // Loops until user inputs valid data, then updates the game grid.
        while (badInput) {
            print("Enter the coordinates: ")
            val coordinates: String = scanner.nextLine()
            val row: Char = coordinates[0]
            val column: Char = coordinates[2]

            if (checkIfNotNumber(row) || checkIfNotNumber(column)) {
                println("You should enter numbers!")
                continue
            }

            if (checkIfNotBetween1And3(row) || checkIfNotBetween1And3(column)) {
                println("Coordinates should be from 1 to 3!")
                continue
            }

            if (checkIfCellOccupied(row, column, c)) {
                println("This cell is occupied! Choose another one")
                continue
            }

            //Update the grid with the user's move
            val changedCell = convertRowColumnToCell(row, column)

            for (index in 0..8) {
                newCellsString += if (index == changedCell) whoseTurn
                else c[index]
            }

            badInput = false
            c = newCellsString
        }

        // Tally total empty spaces (' ' characters) on board
        for (element in c) {
            if (element == ' ') totalEmpty++
        }

        // Check for three in a row vertically
        for (i in 0..2) {
            if (c[i] == ' ') continue
            else if (c[i] != c[i + 3]) continue
            else {
                if (c[i] != c[i + 6]) continue
                else {
                    winner = true
                }
            }
        }

        // Check for three in a row horizontally
        for (i in 0..6 step 3) {
            if (c[i] == ' ') continue
            else if (c[i] != c[i + 1]) continue
            else {
                if (c[i] != c[i + 2]) continue
                else {
                    winner = true
                }
            }
        }

        // Check for three in a row diagonally
        if (c[4] != ' ') {
            if (c[4] == c[0] && c[4] == c[8] || (c[4] == c[2] && c[4] == c[6])) {
                winner = true
            }
        }

        printGrid(c)

        // Checks to see if the game is over.
        if (winner) {
            println("$whoseTurn wins")
            gameOngoing = false
        } else if (totalEmpty == 0 && !winner) {
            println("Draw")
            gameOngoing = false
        }

        whoseTurn = if (whoseTurn == 'X') 'O'
                    else 'X'
    }
}

//Print grid
fun printGrid(c: String) {
    println("---------")
    println("| ${c[0]} ${c[1]} ${c[2]} |")
    println("| ${c[3]} ${c[4]} ${c[5]} |")
    println("| ${c[6]} ${c[7]} ${c[8]} |")
    println("---------")
}

//Returns the cell number corresponding to the row and column given
fun convertRowColumnToCell(row: Char, column: Char): Int {
    return when {
        row.digitToInt() == 1 -> column.digitToInt() - 1
        row.digitToInt() == 2 -> column.digitToInt() + 2
        else -> column.digitToInt() + 5
    }
}

//Returns true if the cell is occupied
fun checkIfCellOccupied(row: Char, column: Char, cells: String): Boolean {
    return cells[convertRowColumnToCell(row, column)] == 'X'
        || cells[convertRowColumnToCell(row, column)] == 'O'
}

// Returns true if the character is not the number 1, 2, or 3
fun checkIfNotBetween1And3(character: Char): Boolean {
    return (character.code < 49 || character.code > 51)
}

// Returns true if the character is not a number
fun checkIfNotNumber(character: Char): Boolean {
    return (character.code < 48 || character.code > 57)
}