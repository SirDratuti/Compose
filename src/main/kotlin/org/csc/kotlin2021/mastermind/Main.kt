package org.csc.kotlin2021.mastermind

fun main(args: Array<String>) {
    if (args.size != 1) {
        throw IllegalArgumentException(
            "You should add exactly one argument: 'console' for console UI or 'graphics' for GUI"
        )
    }

    when (args[0]) {
        "console" -> {
            playMastermind()
        }
        "graphics" -> {
            graphicGame()
        }
        else -> {
            throw IllegalArgumentException("Invalid argument")
        }
    }
}
