package app.ctrlyati.srpgc

import javax.swing.JFrame

/**
 * Created by Yati on 29/11/2559.
 */
class GameRunner {

    var frame: JFrame? = null

    var world: World? = null
    var player: Player? = null
    var gameLoop: GameLoop? = null


    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val gr: GameRunner = GameRunner()
            gr.start(args)
        }
    }

    fun start(args: Array<String>) {

        // TODO create app.ctrlyati.srpgc.GameLoop here
        gameLoop = TestGameLoop()

        world = generateWorld()
        player = spawnPlayer(world as World)
        spawnMonster(world as World)
        startGameLoop()
    }

    fun generateWorld(): World {
        return World()
    }

    fun spawnMonster(world: World) {
        // TODO : add monster to the world
    }

    fun spawnPlayer(world: World): Player {
        val player: Player = Player()

        // TODO : add player to the world

        return player
    }


    fun startGameLoop() {
        var time: Long = System.currentTimeMillis()
        var deltaTime: Long

        if (gameLoop == null) return

        gameLoop?.onInit(time)

        while (true) {

            deltaTime = System.currentTimeMillis() - time
            time = System.currentTimeMillis()


            if (gameLoop?.onUpdate(deltaTime)?.not() as Boolean) break
            gameLoop?.onDraw()


        }

        gameLoop?.onEnd(time)
    }

}

interface GameLoop {
    // Game Loop

    fun onInit(time: Long)
    fun onUpdate(dt: Long): Boolean
    fun onDraw()
    fun onEnd(time: Long)

}

class TestGameLoop : GameLoop {

    var frame: JFrame = JFrame("Test Game")

    override fun onInit(time: Long) {

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        frame.setSize(320, 240)
        frame.isResizable = false
        frame.isVisible = true

    }

    override fun onUpdate(dt: Long): Boolean {
        @Suppress("SENSELESS_COMPARISON")
        return frame != null
    }

    override fun onDraw() {

    }

    override fun onEnd(time: Long) {

    }

}