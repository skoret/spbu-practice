import view.Viewer
import java.io.IOException

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

fun main(args: Array<String>) {

    val viewer = Viewer()

    if (args.isNotEmpty()) {
        viewer.run(args[0])
    }

    while (true) {

        var path: String? = null

        try {

            path = readLine()

        } catch(e: IOException) {

            println(e.message)
            break

        } finally {

            viewer.run(path)

        }

    }

}