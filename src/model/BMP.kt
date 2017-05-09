package model

import java.awt.Color
import java.awt.image.BufferedImage
import java.util.*

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

abstract class BMP(override val name: String, override val data: ByteArray): File, Observable() {

    override lateinit var image: BufferedImage

    //header
    override var height: Int = 0
    override var width: Int = 0
    protected var offset: Long = 0
    private var bitCount: Int = 0

    protected var alignment: Int = 0
    protected val colorTable = mutableListOf<Color>()

//     useless header's fields
//    var size: Long = 0
//    var biSize: Long = 0
//    var planes: Int = 0
//    var compression: Long = 0
//    var sizeImage: Long = 0
//    var xPelsPerMeter: Int = 0
//    var yPelsPerMeter: Int = 0
//    var crlUsed: Long = 0
//    var crlImportant: Long = 0

    override fun parseHeader() {

        offset = getValueOfBytes(4, 0x0a)
        width = getValueOfBytes(4, 0x12).toInt()
        height = getValueOfBytes(4, 0x16).toInt()
        bitCount = getValueOfBytes(2, 0x1c).toInt()

        val length = width * (bitCount / 8)
        if (length % 4 != 0) {
            alignment = 4 - (length % 4)
        }

//        size = getValueOfBytes(4, 0x02)
//        biSize = getValueOfBytes(4, 0x0e)
//        planes = getValueOfBytes(2, 0x1a).toInt()
//        compression = getValueOfBytes(4, 0x1e)
//        sizeImage = getValueOfBytes(4, 0x22)
//        xPelsPerMeter = getValueOfBytes(4, 0x26).toInt()
//        yPelsPerMeter = getValueOfBytes(4, 0x2a).toInt()
//        crlUsed = getValueOfBytes(4, 0x2e)
//        crlImportant = getValueOfBytes(4, 0x32)

    }

    protected fun getColorFormBytes(currentByte: Int, alphaChanel: Boolean = true): Color {

        var index = currentByte

        var alpha: Int = 0xff
        if (alphaChanel) {
            alpha = data[index--].toInt()
            if (alpha < 0) alpha += 256
            if (alpha == 0) alpha = 0xff
        }

        var red = data[index--].toInt()
        if (red < 0) red += 256

        var green = data[index--].toInt()
        if (green < 0) green += 256

        var blue = data[index].toInt()
        if (blue < 0) blue += 256

        return Color(red, green, blue, alpha)

    }

}