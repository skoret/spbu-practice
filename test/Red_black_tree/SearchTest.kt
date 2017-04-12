package Red_black_tree

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import RedBlackTree
import java.util.*

internal class SearchTest {

    @Test
    fun searchByIllegalKey() {
        val tree = RedBlackTree<Int, Int>()

        assertNull(tree.search(null))
    }

    @Test
    fun searchInEmptyTree() {

        val tree = RedBlackTree<Int, Int>()

        for (i in 1..10) {
            assertNull(tree.search(Random().nextInt()))
        }
    }

    @Test
    fun searchNonExistentValue() {

        val tree = RedBlackTree<Int, Int>()
        for (i in 1..100) {
            tree.insert(i, 2*i)
        }

        for (i in 1..100) {
            assertNotEquals(i, tree.search(i))
        }

    }

    @Test
    fun searchNonExistentKey() {

        val tree = RedBlackTree<Int, Int>()
        for (i in 1..100) {
            tree.insert(i, 2*i)
        }

        for (i in 101..200) {
            assertNull(tree.search(i))
        }

    }

    @Test
    fun search() {

        val tree = RedBlackTree<Int, Int>()
        for (i in 1..100) {
            tree.insert(i, 2*i)
        }

        for (i in 100 downTo 1) {
            assertEquals(i*2, tree.search(i))
        }
    }

}