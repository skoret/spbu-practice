package view

import model.*
import javax.swing.*

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class Drawer(val frame: JFrame) {

    fun draw(model: File) {

        val panel = ImagePanel(model.image)
        panel.setSize(model.width, model.height)

        frame.setBounds(100, 100, model.width, model.height)
        frame.contentPane = panel

    }

}