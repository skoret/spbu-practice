package model

import java.awt.image.BufferedImage

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class BMP8bit(override val name: String, override val data: ByteArray): BMP(name, data) {

    override fun parseHeader() {
        super.parseHeader()

        var currentByte = 0x36
        while (currentByte.toLong() != offset) {

            val pixel = getColorFormBytes(currentByte + 0x03)
            colorTable.add(pixel)
            currentByte += 0x04

        }

    }

    override fun createImage() {

        image = BufferedImage(width, height, 3)
        var currentByte = data.size - 1

        for (j in 0..(height - 1)) {

            currentByte -= alignment

            for (i in (width - 1) downTo 0) {

                val index = getValueOfBytes(1, currentByte).toInt()
                val pixel =  colorTable[index]
                currentByte--

                image.setRGB(i, j, pixel.rgb)

            }

        }

        setChanged()
        notifyObservers()

    }

}