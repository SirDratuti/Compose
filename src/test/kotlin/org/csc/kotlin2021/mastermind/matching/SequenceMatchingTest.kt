package org.csc.kotlin2021.mastermind.matching

import org.csc.kotlin2021.mastermind.generation.Util.getGame
import org.csc.kotlin2021.mastermind.generation.Util.invokeFindDiffMethod
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

internal class SequenceMatchingTest {

    companion object {
        private val baseGame = getGame()
        private val smartGame = getGame(false, 5, 6)

        @JvmStatic
        fun baseSequences() = listOf(
            Arguments.of("ACEB", "BCDF", 1, 1),
            Arguments.of("EFAD", "EDAF", 2, 2),
            Arguments.of("ABCD", "DCBA", 0, 4),
            Arguments.of("AFGB", "ABFG", 1, 3)
        )

        @JvmStatic
        fun smartSequences() = listOf(
            Arguments.of("AAAAA", "ABCDE", 1, 0),
            Arguments.of("AFFAF", "FAAFA", 0, 4),
            Arguments.of("CBBAD", "BCADB", 0, 5),
            Arguments.of("EADEC", "ABCDE", 0, 4),
            Arguments.of("BAABD", "EECCE", 0, 0)
        )
    }

    @ParameterizedTest
    @MethodSource("baseSequences")
    fun testSequenceMatching(initial: String, actual: String, expectedFullMatch: Int, expectedPartMatch: Int) {
        val diffResult = invokeFindDiffMethod(baseGame, actual, initial)
        val actualFullMatch = diffResult.positions
        val actualPartMatch = diffResult.letters
        Assertions.assertEquals(
            expectedFullMatch, actualFullMatch, "Full matches don't equal! " +
                    "Actual full match count = $actualFullMatch, expected full match count = $expectedFullMatch"
        )
        Assertions.assertEquals(
            expectedPartMatch, actualPartMatch, "Part matches don't equal! " +
                    "Part full part count = $actualPartMatch, expected part match count = $expectedPartMatch"
        )
    }

    @ParameterizedTest
    @MethodSource("smartSequences")
    fun testSmartSequenceMatching(initial: String, actual: String, expectedFullMatch: Int, expectedPartMatch: Int) {
        val diffResult = invokeFindDiffMethod(smartGame, initial, actual)
        val actualFullMatch = diffResult.positions
        val actualPartMatch = diffResult.letters
        Assertions.assertEquals(
            expectedFullMatch, actualFullMatch, "Full matches don't equal! " +
                    "Actual full match count = $actualFullMatch, expected full match count = $expectedFullMatch"
        )
        Assertions.assertEquals(
            expectedPartMatch, actualPartMatch, "Part matches don't equal! " +
                    "Part full part count = $actualPartMatch, expected part match count = $expectedPartMatch"
        )
    }
}
