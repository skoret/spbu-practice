package controller

import model.*
import view.Viewer
import java.io.FileInputStream

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class ControllerBMP(private val path: String, private val view: Viewer): Controller {

    private val data: ByteArray

    init {

        val file = FileInputStream(path)
        data = ByteArray(file.available())
        file.read(data)

        if (!validateFormat()) throw IllegalArgumentException("Wrong format file.")

    }

    override fun validateFormat(): Boolean {
        return (data[0].toInt() == 0x42) && (data[1].toInt() == 0x4d)
    }

    override fun createModel(): BMP {

        val name = path.takeLastWhile { it != '/' }

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