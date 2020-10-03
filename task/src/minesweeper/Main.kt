package minesweeper
import java.util.*
import kotlin.random.Random
val scanner = Scanner(System.`in`)
var primaryArray = arrayOf( CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) ,
        CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9))
var numberOfMines: Int = 0
var numberOfSurroundingBombs = 0
val numbers = charArrayOf('1', '2', '3', '4', '5', '6', '7', '8')
var finalMiensField =  arrayOf( CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) ,
        CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9))

class LookingAround(x: Int  ,y: Int ) {
    private val checksArray = arrayOf(Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
                             ,Pair(x - 1, y)                    , Pair(x + 1, y)
                             ,Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1))
    fun check() {
        numberOfSurroundingBombs = 0
        for (i in checksArray) {
            if (i.first in 0..8 && i.second in 0..8){
                if (primaryArray[i.second][i.first] == 'X') {
                    numberOfSurroundingBombs++
                }
            }
        }
    }
}
fun firstClick(): Boolean {
    for (i in 0..8) {
        for (l in 0..8) {
            if (finalMiensField[i][l] != '.') {
                return false
            }
        }
    }
    return true
}
class Playing {
    fun setDeleteMines(): Boolean {
        print("Set/unset mines marks or claim a cell as free: ")
        val y = scanner.nextInt() - 1
        val x = scanner.nextInt() - 1
        when(scanner.next()) {
            "free" -> {
                when  {
                    firstClick() -> {
                        when {
                            primaryArray[x][y] == 'X' -> {
                                do {
                                    primaryArray[x][y] = '.'
                                    printingDots()
                                    placingMines(numberOfMines)
                                    lookingAround()
                                } while (primaryArray[x][y] != '.')
                                floodFill(x, y)
                                revealingHints()
                            }
                            primaryArray[x][y] == '.' -> {
                                floodFill(x, y)
                                revealingHints()
                            }
                            primaryArray[x][y] in numbers -> {
                                do {
                                    primaryArray[x][y] = '.'
                                    printingDots()
                                    placingMines(numberOfMines)
                                    lookingAround()
                                } while (primaryArray[x][y] != '.')
                                floodFill(x, y)
                                revealingHints()
                            }
                        }
                    } else -> {
                    when {
                        primaryArray[x][y] == 'X' -> {
                            for (i in 0..8) {
                                for (l in 0..8) {
                                    if (primaryArray[i][l] == 'X') finalMiensField[i][l] = 'X'
                                }
                            }
                            println()
                            printingTheArray()
                            println("You stepped on a mine and failed!\n")
                            return false
                        }
                        primaryArray[x][y] == '.' && finalMiensField[x][y] != '/' -> {
                            finalMiensField[x][y] = '/'
                            floodFill(x, y)
                            revealingHints()
                        }
                        primaryArray[x][y] in numbers -> {
                            finalMiensField[x][y] = primaryArray[x][y]
                        }
                        finalMiensField[x][y] == '*' -> {
                            finalMiensField[x][y] = '.'
                        }
                        finalMiensField[x][y] == '/' -> {

                        }
                    }
                }
                }
            }
            "mine" -> {
                if (finalMiensField[x][y] == '*') {
                    if (primaryArray[x][y] == 'X') {
                        finalMiensField[x][y] = '.'
                    } else {
                        finalMiensField[x][y] = primaryArray[x][y]
                    }
                } else {
                finalMiensField[x][y] = '*'
                }
            }
        }
        return true
    }
}

class CheckingAnswer {
    fun checkStarsSolution(): Boolean {
        for (i in 0..8) {
            for (j in 0..8) {
                if (primaryArray[i][j] == 'X') {
                        if (finalMiensField[i][j] != '*') return false
                    }
                }
            }
        println("Congratulations! You found all the mines!")
        return true
    }
    fun checkDotsSolution(): Boolean {
        val finalMiensFieldConverted =  arrayOf( CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9) ,
        CharArray(9) , CharArray(9) , CharArray(9) , CharArray(9))
        for (i in 0..8) {
            for (l in 0..8) {
                if (finalMiensField[i][l] == '/') finalMiensFieldConverted[i][l] = '.' else finalMiensFieldConverted[i][l] = finalMiensField[i][l]
            }
        }
        for (i in 0..8) {
            for (j in 0..8) {
                if (primaryArray[i][j] != 'X') {
                      if (finalMiensFieldConverted[i][j] != primaryArray[i][j]) return false
                } else if (primaryArray[i][j] == 'X') {
                    if (finalMiensFieldConverted[i][j] != '.') return false
                }
            }
        }
        println("Congratulations! You found all the mines!")
        return true
    }
}

fun floodFill(x: Int, y: Int) {
    val checksArray = arrayOf(Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
            ,Pair(x - 1, y)                    , Pair(x + 1, y)
            ,Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1))
    if (x > 8 || y > 8 || x < 0 || y < 0) return
    if (primaryArray[x][y] != '.') return

    primaryArray[x][y] = '/'
    finalMiensField[x][y] = primaryArray[x][y]

    for (i in checksArray) {
        floodFill(i.first, i.second)
    }
}

fun revealingHints() {
    for (x in 0..8) {
        for (y in 0..8) {
           if (primaryArray[x][y] == '/') {
               val checksArray = arrayOf(Pair(x - 1, y + 1), Pair(x, y + 1), Pair(x + 1, y + 1)
                       ,Pair(x - 1, y)                    , Pair(x + 1, y)
                       ,Pair(x - 1, y - 1), Pair(x, y - 1), Pair(x + 1, y - 1))
               for (i in checksArray) {
                   if (i.first >= 0 && i.second >= 0 && i.first <= 8 && i.second <= 8 ) {
                       if (primaryArray[i.first][i.second] in charArrayOf('1', '2', '3', '4', '5', '6', '7', '8')) finalMiensField[i.first][i.second] = primaryArray[i.first][i.second]
                   }
               }
               primaryArray[x][y] = '.'
           }
           }
        }
    }


fun main() {
    print("How many mines do you want on the field? ")
    numberOfMines = scanner.nextInt()
    printingDots()
    placingMines(numberOfMines)
    lookingAround()
    printingTheArray()
    gamePLay()
}

fun printingTheArray() {
    var rowOrder = 1
    println(" │123456789│")
    println("—│—————————│")
    for (i in finalMiensField) {
        print(rowOrder);print("|");print(i);println("|")
        rowOrder++
    }
    println("—│—————————│")
}

fun printingDots() {
    for (i in primaryArray) {
        for (l in 0 until 9) {
            i[l] = '.'
        }
    }
    for (i in finalMiensField) {
        for (l in 0 until 9) {
            i[l] = '.'
        }
    }

}

fun placingMines(numberOfMines: Int) {
    for (i in 1..numberOfMines) {
        var l = Random.nextInt(9)
        var j = Random.nextInt(9)
        while (primaryArray[l][j] == 'X') {
            l = Random.nextInt(9)
            j = Random.nextInt(9)
        }
        primaryArray[l][j] = 'X'
    }
}

fun lookingAround() {
    for (y in 0 until 9) {
        for (x in 0 until 9) {
            if (primaryArray[y][x] != 'X') {
                val lookAround = LookingAround(x, y)
                lookAround.check()
                if (numberOfSurroundingBombs > 0) {
                    primaryArray[y][x] = "$numberOfSurroundingBombs".toCharArray()[0]
                }
            }
        }
    }
}

 fun gamePLay() {
     val playing = Playing()
     val checkAnswer = CheckingAnswer()
     do {
         if (!playing.setDeleteMines()) break
         printingTheArray()
     } while (!(checkAnswer.checkStarsSolution() || checkAnswer.checkDotsSolution()))
 }