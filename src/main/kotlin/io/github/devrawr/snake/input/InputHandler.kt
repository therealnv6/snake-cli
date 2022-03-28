package io.github.devrawr.snake.input

import io.github.devrawr.snake.GameData

interface InputHandler
{
    fun observe(gameData: GameData)
}