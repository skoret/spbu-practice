package model

import java.awt.image.BufferedImage

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class BMP24bit(override val name: String, override val data: ByteArray): BMP(name, data) {

    override fun createImage() {

        image = BufferedImage(width, height, 1)
        var currentByte = data.size - 1

        for (j in 0..(height - 1)) {

            currentByte -= alignment

            for (i in (width - 1) downTo 0) {

                val pixel = getColorFormBytes(currentByte, false) // Color(red, green, blue)
                currentByte -= 3

                image.setRGB(i, j, pixel.rgb)

            }

       }

       setChanged()
       notifyObservers()

   }

}