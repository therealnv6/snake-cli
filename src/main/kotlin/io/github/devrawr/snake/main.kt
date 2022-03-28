package io.github.devrawr.snake

import io.github.devrawr.snake.input.InputHandler
import io.github.devrawr.snake.input.impl.CLIInputHandler
import io.github.devrawr.snake.player.SnakeBody
import io.github.devrawr.snake.renderer.Renderer
import io.github.devrawr.snake.renderer.type.CLIGameRenderer
import kotlin.properties.Delegates

var width by Delegates.notNull<Int>()
var height by Delegates.notNull<Int>()

lateinit var grid: GameGrid
lateinit var renderer: Renderer
lateinit var inputHandler: InputHandler
lateinit var gameData: GameData

const val FPS = 8

object Main
{
    @JvmStatic
    fun main(args: Array<String>)
    {

        val size = args
            .getOrNull(0)
            ?.toIntOrNull() ?: 12

        width = size
        height = size

        grid = GameGrid(
            width = width,
            height = height
        )

        val player = SnakeBody()

        gameData = GameData(
            player, grid
        )

        renderer = CLIGameRenderer.apply {
            this.render()
        }

        inputHandler = CLIInputHandler.apply {
            this.observe(gameData)
        }

        while (true)
        {
            if (!player.alive) // if player dies, it will update on next tick
            {
                continue
            }

            player.tick()
            Thread.sleep(500)
        }
    }
}


fun sleepForFps()
{
    Thread.sleep((1000 / FPS).toLong())
}

fun <T> Array<T>.getOrNull(index: Int): T?
{
    return try
    {
        this[index]
    } catch (ignored: Exception)
    {
        null
    }
}

data class GameData(
    val player: SnakeBody,
    val grid: GameGrid
)

data class GameGrid(
    val height: Int,
    val width: Int
)