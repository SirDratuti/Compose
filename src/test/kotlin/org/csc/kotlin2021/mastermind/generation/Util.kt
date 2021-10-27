package org.csc.kotlin2021.mastermind.generation

import org.csc.kotlin2021.mastermind.DiffResult
import org.csc.kotlin2021.mastermind.Game
import java.lang.reflect.Method

object Util {

    @Throws(Exception::class)
    fun invokeGenerateSecretMethod(
        game: Game
    ): String {
        val m: Method = Game::class.java.getDeclaredMethod("generateSecret")
        m.isAccessible = true
        return m.invoke(game) as String
    }

    @Throws(Exception::class)
    fun invokeFindDiffMethod(
        game: Game,
        initial: String,
        actual: String
    ): DiffResult {
        val m: Method = Game::class.java.getDeclaredMethod("findDiff", String::class.java, String::class.java)
        m.isAccessible = true
        return m.invoke(game, initial, actual) as DiffResult
    }

    fun getGame(
        differentLetters: Boolean = true,
        secretLength: Int = 4,
        alphabetLength: Int = 8,
        gameLength: Int = 100
    ): Game {
        val builder = Game.Companion.GameBuilder()
        builder.apply {
            this.differentLetters = differentLetters
            this.secretLength = secretLength
            this.alphabetSize = alphabetLength
            this.gameLength = gameLength
        }
        return builder.build()
    }
}
