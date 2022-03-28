package io.github.devrawr.snake.input.impl

import io.github.devrawr.snake.GameData
import io.github.devrawr.snake.gameData
import io.github.devrawr.snake.input.InputHandler
import io.github.devrawr.snake.player.MoveType
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyEvent
import org.jnativehook.keyboard.NativeKeyListener
import java.util.logging.Level
import java.util.logging.LogManager
import java.util.logging.Logger


object CLIInputHandler : InputHandler
{
    override fun observe(gameData: GameData)
    {
        // disable ugly logging
        LogManager.getLogManager().reset()
        Logger.getLogger(GlobalScreen::class.java.getPackage().name).apply {
            this.level = Level.OFF
        }

        // register global screen stuff
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(CLIKeyListener)
    }
}

object CLIKeyListener : NativeKeyListener
{
    override fun nativeKeyPressed(event: NativeKeyEvent)
    {

    }

    override fun nativeKeyReleased(event: NativeKeyEvent)
    {
        val direction = when (event.keyCode)
        {
            57416 -> MoveType.Up
            57424 -> MoveType.Down
            57419 -> MoveType.Left
            57421 -> MoveType.Right
            else -> return
        }

        val player = gameData.player

        if (player.movement != direction)
        {
            player.move(direction)
        }
    }

    override fun nativeKeyTyped(p0: NativeKeyEvent?)
    {
    }
}