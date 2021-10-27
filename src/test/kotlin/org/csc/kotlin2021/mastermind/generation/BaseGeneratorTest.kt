package org.csc.kotlin2021.mastermind.generation

import org.csc.kotlin2021.mastermind.generation.Util.getGame
import org.csc.kotlin2021.mastermind.generation.Util.invokeGenerateSecretMethod
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

internal class BaseGeneratorTest {

    @Test
    fun defaultGeneration() {
        val instance = getGame()
        val result = invokeGenerateSecretMethod(instance)
        assertTrue(result.matches(Regex("^[A-H]{4}$")))
    }

    @Test
    fun smallGeneration() {
        val instance = getGame(true, 3, 4)
        val result = invokeGenerateSecretMethod(instance)
        assertTrue(result.matches(Regex("^[A-D]{3}$")))
    }

    @Test
    fun digGeneration() {
        val instance = getGame(true, 10, 26)
        val result = invokeGenerateSecretMethod(instance)
        assertTrue(result.matches(Regex("^[A-Z]{10}$")))
    }

    @Test
    fun incorrectGeneration() {
        val instance = getGame(true, 10, 8)
        val result = invokeGenerateSecretMethod(instance)
        assertTrue(result.matches(Regex("^[A-H]{4}$")))
    }
}
