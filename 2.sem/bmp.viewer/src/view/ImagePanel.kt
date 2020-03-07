package view

import javax.swing.JPanel
import java.awt.Graphics
import java.awt.image.BufferedImage

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class ImagePanel(private val image: BufferedImage): JPanel() {

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        g.drawImage(image, 0, 0, this)

    }

}