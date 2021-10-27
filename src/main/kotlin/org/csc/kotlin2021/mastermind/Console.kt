package org.csc.kotlin2021.mastermind

fun playMastermind() {
    val game = configGame().build()
    playGame(game)
    printResult(game)
}

private fun configGame(): Game.Companion.GameBuilder {
    println(
        """Enter 4 integers for game settings
            | 1) different letters in secret (1 for true, 0 for false)
            | 2) secret word length
            | 3) alphabet size
            | 4) game length
        """.trimMargin()
    )

    val gameBuilder = Game.Companion.GameBuilder()
    var isCorrect = false
    do {
        val parameters = readLine()
        parameters?.let {
            isCorrect = gameBuilder.changeFromString(
                it.replace("\\s+", " ").split(" ")
            )
        }
    } while (parameters == null || !isCorrect)
    return gameBuilder
}

private fun playGame(game: Game) {
    do {
        println("Guess a string")
        val userGuess = readLine()
        userGuess?.let {
            println(game.validate(it))
        }
    } while (game.isComplete == GameState.ACTIVE)
}

private fun printResult(game: Game) {
    println(
        """You ${game.isComplete} in ${game.turns} turns!
            Your records list:
        """.trimIndent()
    )
    Recorder.userRecords.playerResults.forEach {
        println(it)
    }
}
