package org.csc.kotlin2021.mastermind

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

@Preview
fun graphicGame() = application {
    var isPressed by remember { mutableStateOf(false) }
    val game = Game.Companion.GameBuilder()
    Window(
        onCloseRequest = ::exitApplication,
        title = "MasterMind",
        state = rememberWindowState(width = 450.dp, height = 300.dp)
    ) {
        if (isPressed) {
            GameView(game.build())
        } else {
            ConfigView(
                onPressed = { value -> isPressed = value },
                onGameChanged = { secretLength, alphabetSize, gameLength, differentLetters ->
                    game.apply {
                        this.secretLength = secretLength
                        this.alphabetSize = alphabetSize
                        this.gameLength = gameLength
                        this.differentLetters = differentLetters
                    }
                }
            )
        }
    }
}

@Composable
fun ConfigView(
    onPressed: (Boolean) -> Unit,
    onGameChanged: (Int, Int, Int, Boolean) -> Unit
) {
    var secretLength by remember { mutableStateOf("4") }
    var alphabetSize by remember { mutableStateOf("8") }
    var checkedState by remember { mutableStateOf(true) }
    var gameLength by remember { mutableStateOf("100") }
    val maxLength = 9

    val validate: (String) -> Boolean = { newValue ->
        newValue.length <= maxLength && newValue.all { it.isDigit() }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = secretLength,
            label = { Text("Secret length") },
            onValueChange = { newValue ->
                if (validate(newValue)) {
                    secretLength = newValue
                }
            }
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = alphabetSize,
            label = { Text("Alphabet size") },
            onValueChange = { newValue ->
                if (validate(newValue)) {
                    alphabetSize = newValue
                }
            }
        )
        OutlinedTextField(
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            value = gameLength,
            label = { Text("Game length") },
            onValueChange = { newValue ->
                if (validate(newValue)) {
                    gameLength = newValue
                }
            }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Different letters")
            Checkbox(
                checked = checkedState,
                onCheckedChange = { checkedState = it }
            )
            OutlinedButton(
                onClick = {
                    onPressed(true)
                    onGameChanged(
                        secretLength.toInt(),
                        alphabetSize.toInt(),
                        gameLength.toInt(),
                        checkedState
                    )
                }
            ) {
                Text("Start Game")
            }
        }
    }
}

@Composable
fun GameView(
    game: Game
) {
    var userInput by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("") }

    MaterialTheme {
        if (game.isComplete != GameState.ACTIVE) {
            GameResultView(game)
        } else {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = userInput,
                    maxLines = 1,
                    onValueChange = { newText ->
                        userInput = newText
                    },
                    label = { Text("Guess") }
                )
                OutlinedButton(
                    modifier = Modifier.align(Alignment.CenterHorizontally).padding(5.dp),
                    onClick = {
                        result = game.validate(userInput)
                    }
                ) {
                    Text("Take a guess")
                }
                GuessResult(result)
            }
        }
    }
}

@Composable
fun GuessResult(result: String) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(10.dp).fillMaxWidth(),
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 25.dp),
            text = result,
        )
    }
}

@Composable
fun GameResultView(game: Game) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(5.dp).fillMaxWidth()
    ) {
        Text(
            text = "You ${game.isComplete} in ${game.turns} turns!",
            modifier = Modifier.padding(horizontal = 25.dp).padding(vertical = 10.dp)
        )
        Text(
            text = "Your records list:",
            modifier = Modifier.padding(horizontal = 25.dp).padding(bottom = 10.dp)
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Recorder.userRecords.playerResults.forEach { result ->
                RecordRow(result)
            }
        }
    }
}

@Composable
fun RecordRow(result: String) {
    Text(
        text = result,
        fontSize = 15.sp,
        modifier = Modifier
            .padding(4.dp)
            .padding(horizontal = 10.dp)
    )
}
