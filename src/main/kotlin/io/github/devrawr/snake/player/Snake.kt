package io.github.devrawr.snake.player

import io.github.devrawr.snake.height
import io.github.devrawr.snake.width

class SnakeBody
{
    val head = BodyPart(0, 0)
    val moved = hashMapOf<BodyPart, MutableList<MoveTarget>>()
    val parts = mutableListOf<BodyPart>()
    var movement: MoveType? = MoveType.Right

    var alive = true

    fun move(to: MoveType)
    {
        this.movement = to

        val target = MoveTarget(
            to, this.head.x, this.head.y
        )

        for (part in this.parts)
        {
            moved.putIfAbsent(part, mutableListOf())
            moved[part]!!.add(target)
        }
    }

    fun tick()
    {
        for (part in parts)
        {
            val moved = this.moved[part]
            val target = moved?.firstOrNull()

            if (moved == null || target == null)
            {
                if (this.movement != null)
                {
                    part.moveTo(this.movement!!)
                }

                continue
            }

            if (part.x == target.x && part.y == target.y)
            {
                moved.remove(target)
                continue
            }

            part.moveTo(target.type)
        }

        this.movement?.let {
            this.head.moveTo(it)
        }

        this.alive = this.isWithinBounds()
    }

    private fun isWithinBounds(): Boolean
    {
        val x = this.head.x
        val y = this.head.y

        return !(x < 0 || x >= width || y < 0 || y >= height)
    }
}

class BodyPart(
    var x: Int,
    var y: Int
)
{
    fun moveTo(moveType: MoveType)
    {
        when (moveType)
        {
            MoveType.Up -> y--
            MoveType.Down -> y++
            MoveType.Right -> x++
            MoveType.Left -> x--
        }
    }
}

enum class MoveType
{
    Up,
    Down,
    Left,
    Right
}

data class MoveTarget(
    val type: MoveType,
    val x: Int,
    val y: Int
)