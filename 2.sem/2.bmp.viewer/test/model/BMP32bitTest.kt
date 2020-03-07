package model

import java.io.File
import java.io.FileInputStream
import javax.imageio.ImageIO.read
import java.awt.image.BufferedImage
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

internal class BMP32bitTest {

    @Test
    fun createImage() {

        val files = listOf("led_32bit.bmp", "pahom_32bit.bmp", "sh_32bit.bmp", "panda_32bit.bmp")
        var model: BMP32bit
        var expectedImage: BufferedImage

        for (i in files) {

            print(i + ": ")

            val file = FileInputStream("input/bmp/" + i)
            val data = ByteArray(file.available())
            file.read(data)

            expectedImage = read(File("input/bmp/" + i))
            model = BMP32bit(i, data)

            model.parseHeader()
            model.createImage()

            assertEquals(expectedImage.height, model.image.height)
            print("height-ok, ")
            assertEquals(expectedImage.width, model.image.width)
            print("width-ok, ")

            for (j in 0..(model.image.height - 1)) {
                for (k in 0..(model.image.width - 1)) {

                    val actualPixel = model.image.getRGB(k, j)
                    val expectedPixel = expectedImage.getRGB(k, j)

                    assertEquals(expectedPixel, actualPixel)

                }
            }

            println("image-ok.")

        }

    }

}