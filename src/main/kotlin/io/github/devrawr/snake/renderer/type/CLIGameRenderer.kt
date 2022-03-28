package io.github.devrawr.snake.renderer.type

import com.github.ajalt.mordant.animation.animation
import com.github.ajalt.mordant.animation.textAnimation
import com.github.ajalt.mordant.rendering.TextColors
import com.github.ajalt.mordant.terminal.Terminal
import io.github.devrawr.snake.gameData
import io.github.devrawr.snake.height
import io.github.devrawr.snake.renderer.Renderer
import io.github.devrawr.snake.sleepForFps
import io.github.devrawr.snake.width
import kotlin.concurrent.thread

object CLIGameRenderer : Renderer
{
    private val terminal = Terminal()

    override fun render()
    {
        val animation = terminal.textAnimation<Int> {
            val player = gameData.player

            if (!player.alive)
            {
                println("${player.head.x}, ${player.head.y}")
                return@textAnimation TextColors.red("You've died!")
            }

            val drawer = Array(height) {
                arrayOfNulls<String>(width)
            }

            val parts = player.parts

            for (part in parts)
            {
                drawer[part.y][part.x] = TextColors.cyan.bg("   ")
            }

            drawer[player.head.y][player.head.x] = TextColors.brightBlue.bg("   ")

            (0..height).joinToString("\n") { y ->
                (0..width).joinToString("") { x ->
                    terminal.colors.hsv(1, 1, 1)("x")
                    drawer
                        .getOrNull(y)
                        ?.getOrNull(x) ?: TextColors.white.bg("   ")
                }
            }
        }


        terminal.cursor.hide(showOnExit = true)

        thread(start = true) {
            Thread.sleep(200)

            var x = 0

            while (true)
            {


                animation.update(x++)
                sleepForFps()
            }
        }

//        val drawer = Array(height) {
//            arrayOfNulls<String>(width)
//        }
//
//        val player = gameData.player
//        val parts = player.parts
//
//        for (part in parts)
//        {
//            drawer[part.y][part.x] = TextColors.cyan.bg("   ")
//        }
//
//        drawer[player.head.y][player.head.x] = TextColors.brightBlue.bg("   ")
//
//        for (y in 0..height)
//        {
//            for (x in 0..width)
//            {
//                terminal.cursor.move {
//                    setPosition(x, y)
//                }
//
//                terminal.print(
//                    drawer
//                        .getOrNull(y)
//                        ?.getOrNull(x) ?: TextColors.white.bg("   ")
//                )
//            }
//
//            println()
//        }
    }
}