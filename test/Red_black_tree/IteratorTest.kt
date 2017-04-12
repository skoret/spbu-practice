package Red_black_tree

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import RedBlackTree
import RBNode

internal class IteratorTest {

    @Test
    fun iteratorInEmptyTree() {

        val tree = RedBlackTree<Int, Int>()
        val emptyList = emptyList<RBNode<Int, Int>>()

        assertIterableEquals(emptyList, tree)
    }

    @Test
    operator fun iterator() {

        val tree = RedBlackTree<Int, Int>()
        val expectedValues = listOf(12, 8, 7, 5, 4, 3, 2, 1)

        for (i in listOf(7, 1, 4, 8, 3, 5, 2, 12)) {
            tree.insert(i, i)
        }

        assertIterableEquals(expectedValues, tree.map { it.value })

    }

}