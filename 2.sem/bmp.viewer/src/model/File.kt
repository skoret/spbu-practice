package model

import java.awt.image.BufferedImage

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

interface File {

    val name: String
    val data: ByteArray
    var image: BufferedImage
    var width: Int
    var height: Int

    fun parseHeader()
    fun createImage()

    //order == true -> little-endian
    fun getValueOfBytes(count: Int, index: Int, order: Boolean = true): Long {

        val bytes = data.copyOfRange(index, index + count)
        var value: Long = 0

        if (order) {
            bytes.reverse()
        }

        for (i in bytes) {

            var tmp = i.toInt()
            if (tmp < 0) tmp +=256
            value += tmp
            if (i != bytes.last()) value = value.shl(8)

        }

        return value

    }

}