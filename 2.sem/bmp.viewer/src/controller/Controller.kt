package controller

import model.File

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

interface Controller {

    fun validateFormat(): Boolean
    fun createModel(): File

}