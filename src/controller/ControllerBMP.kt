package controller

import model.*
import view.Viewer
import java.io.FileInputStream

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class ControllerBMP(path: String, val view: Viewer): Controller {

    val file = FileInputStream(path)
    val name = path.takeLastWhile { it != '/' }
    val data = ByteArray(file.available())

    init {

        file.read(data)

        if (!validateFormat()) throw IllegalArgumentException("Wrong format file.")

    }

    override fun validateFormat(): Boolean {
        return (data[0].toInt() == 0x42) && (data[1].toInt() == 0x4d)
    }

    override fun createModel(): BMP {

        val bitCount = data[0x1c].toInt()
        val model: BMP

        when (bitCount) {
            8 -> {
                model = BMP8bit(name, data)
            }
            24 -> {
                model = BMP24bit(name, data)
            }
            32 -> {
                model = BMP32bit(name, data)
            }
            else -> {
                throw Throwable("Sorry, Viewer can't work with this file.")
            }
        }

        model.addObserver(view)
        model.parseHeader()
        model.createImage()

        return model

    }

}