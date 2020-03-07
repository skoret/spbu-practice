package view

import model.*
import controller.*
import java.util.*
import javax.swing.JFrame

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class Viewer: Observer {

    private val formats = arrayOf("bmp", "BMP")
    private val frame = JFrame()
    private val drawer = Drawer(frame)
    private lateinit var model: File
    private lateinit var controller: Controller

    init {

        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE

    }


    fun run(path: String?) {

        try {

            val format = path?.takeLastWhile { it != '.' }
            if (!formats.contains(format)) {
                throw IllegalArgumentException("Not allowed format file.")
            }

            when (format) {
                "bmp", "BMP" -> {
                    controller = ControllerBMP(path, this)
                }
            }

            model = controller.createModel()

        } catch (e: Throwable) {
            println(e.message)
            return
        }

    }

    override fun update(o: Observable?, arg: Any?) {

        o as File

        frame.title = o.name
        drawer.draw(o)
        frame.isVisible = true

    }

}