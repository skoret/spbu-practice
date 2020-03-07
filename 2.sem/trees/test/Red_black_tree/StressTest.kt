package Red_black_tree

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import BinarySearchTree
import RedBlackTree
import BTree
import java.util.*

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

internal class StressTest {

    @Test
    fun insert() {

        val tree = RedBlackTree<Int, Int>()

        for (i in 1..19000000) {
            if (i % 500000 == 0) {
                println(i)
            }
            tree.insert(i, i)
        }

    }

    @Test
    fun delete() {

        val tree = RedBlackTree<Int, Int>()

        for (i in 1..19000000) {
            if (i % 1000000 == 0) {
                println(i)
            }
            tree.insert(i, i)
        }

        for (i in 1..19000000) {
            if (i % 500000 == 0) {
                println(i)
            }
            tree.delete(i)
        }

        assertEquals(RedBlackTree<Int, Int>(), tree)

    }

    @Test fun insertRandom() {

        val tree = RedBlackTree<Int, Int>()
        for (i in 1..25000000) {
            if (i % 500000 == 0) {
                println(i)
            }
            val k = Random().nextInt(40000000)
            tree.insert(k, k)
        }

    }

    @Test fun deleteRandom() {

        val tree = RedBlackTree<Int, Int>()

        for (i in 1..10000000) {
            if (i % 1000000 == 0) {
                println(i)
            }
            val k = Random().nextInt(40000000)
            tree.insert(k, k)
        }

        for (i in 1..10000000) {
            if (i % 500000 == 0) {
                println(i)
            }
            val k = Random().nextInt(40000000)
            tree.delete(k)
        }

    }

}