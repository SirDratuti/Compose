package org.csc.kotlin2021.mastermind

import java.lang.NumberFormatException
import java.lang.StringBuilder

enum class GameState {
    LOSE,
    WIN,
    ACTIVE
}

class Game private constructor(
    private val differentLetters: Boolean = true,
    private val secretLength: Int = 4,
    private val alphabetSize: Int = 8,
    private val gameLength: Int = 100
) {
    private val range: CharRange = 'A'..'A' + (alphabetSize - 1)
    private val secret = generateSecret()
    var isComplete: GameState = GameState.ACTIVE
        private set
    var turns = 0
        private set

    fun validate(playerGuess: String): String {
        println(secret)
        val check = playerGuess.length == secretLength && playerGuess.all {
            it.isUpperCase() && it in range
        }
        return if (check) {
            findDiff(secret, playerGuess).result
        } else {
            "Incorrect input: $playerGuess." +
                    " It should consist of $secretLength letters (${range.joinToString(", ")})."
        }
    }

    private fun generateSecret(): String {
        return if (differentLetters) {
            range.shuffled()
                .take(secretLength)
                .joinToString("")
        } else {
            val builder = StringBuilder()
            repeat(secretLength) {
                builder.append(range.random())
            }
            builder.toString()
        }
    }

    private fun findDiff(secret: String, playerGuess: String): DiffResult {
        turns++
        val positions = playerGuess.zip(secret).count { it.first == it.second }
        if (positions == secretLength) {
            isComplete = GameState.WIN
            Recorder.addRecord(
                "Solved $secret with $alphabetSize" +
                        "${if (differentLetters) " different" else ""} letters in $turns " +
                        if (turns == 1) "turn" else "turns"
            )
            return DiffResult(positions, 0, "Complete")
        }
        val letters = range.sumOf { character ->
            secret.count { it == character }.coerceAtMost(playerGuess.count { it == character })
        } - positions
        return if (turns < gameLength) {
            DiffResult(positions, letters, "Positions: $positions; letters: $letters.")
        } else {
            isComplete = GameState.LOSE
            DiffResult(positions, letters, "Game Ended")
        }
    }

    companion object {
        class GameBuilder {
            var alphabetSize = 8
            var secretLength = 4
            var differentLetters = true
            var gameLength = 100

            fun changeFromString(parameters: List<String>): Boolean {
                return if (parameters.size != 4) {
                    println("There must be exactly 4 parameters")
                    false
                } else {
                    try {
                        differentLetters = parameters[0] == "1"
                        secretLength = parameters[1].toInt()
                        alphabetSize = parameters[2].toInt()
                        gameLength = parameters[3].toInt()
                    } catch (ignored: NumberFormatException) {
                        println("All arguments must be numbers")
                    }
                    true
                }
            }

            fun build(): Game {
                return if (differentLetters && secretLength > alphabetSize) {
                    println("Impossible to generate secret with given parameters, changed to default")
                    Game()
                } else {
                    Game(
                        differentLetters,
                        secretLength.coerceAtLeast(1),
                        alphabetSize.coerceAtMost(26).coerceAtLeast(1),
                        gameLength.coerceAtLeast(1)
                    )
                }
            }
        }
    }
}

data class DiffResult(val positions: Int, val letters: Int, val result: String)
