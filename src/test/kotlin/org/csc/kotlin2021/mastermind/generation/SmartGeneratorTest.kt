package org.csc.kotlin2021.mastermind.generation

import org.csc.kotlin2021.mastermind.generation.Util.getGame
import org.csc.kotlin2021.mastermind.generation.Util.invokeGenerateSecretMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SmartGeneratorTest {

    @Test
    fun defaultGeneration() {
        val instance = getGame(false)
        val result = invokeGenerateSecretMethod(instance)
        Assertions.assertTrue(result.matches(Regex("^[A-H]{4}$")))
    }

    @Test
    fun smallGeneration() {
        val instance = getGame(false, 3, 4)
        val result = invokeGenerateSecretMethod(instance)
        Assertions.assertTrue(result.matches(Regex("^[A-D]{3}$")))
    }

    @Test
    fun digGeneration() {
        val instance = getGame(false, 10, 26)
        val result = invokeGenerateSecretMethod(instance)
        Assertions.assertTrue(result.matches(Regex("^[A-Z]{10}$")))
    }

    @Test
    fun nonAlphabetGeneration() {
        val instance = getGame(false, 10, 150)
        val result = invokeGenerateSecretMethod(instance)
        Assertions.assertTrue(result.matches(Regex("^[A-Z]{10}$")))
    }
}
