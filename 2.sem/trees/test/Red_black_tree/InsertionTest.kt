package Red_black_tree

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import RedBlackTree
import RBNode

internal class InsertionTest {

    @Test
    fun insertOneKey() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(20, 20)
        root.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        actualTree.insert(20, 20)

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRightRightCase() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRightLeftCase() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 30, 25)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertLeftLeftCase() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(30, 25, 20)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertLeftRightCase() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf( 30, 20, 25)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertRecoloringRelatives() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root
        node2.color = false

        val node3 = RBNode(40, 40)
        node2.right = node3
        node3.parent = node2

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun insertIllegalKeyValue() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        root.right = node2
        node2.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30)) {
            actualTree.insert(i, i)
        }

        actualTree.insert(null, 40)
        actualTree.insert(40, null)
        actualTree.insert(null, null)

        assertEquals(expectedTree, actualTree)

    }

    /** Insert according to the following scenario:
     * 1) Insert root(20, 20) -> recoloring root in black
     * 2) Insert node(25, 25), node(30, 30) -> RightRight case
     * 3) Insert node(40, 40) -> recoloring father, uncle, grandfather; recoloring root in black
     * 4) Insert node(35, 35) -> RightLeft case
     * 5) Insert node(27, 27) -> recoloring father, uncle, grandfather
     * 6) Insert node(26, 26) -> LeftLeft case
     * 7) Insert node(10, 10)
     * 8) Insert node(15, 15) -> LeftRight case
     * 9) Insert node(28, 28) -> recoloring father, uncle, grandfather;
     *                           RightLeft case(for grandfather);
     * 10) Insert illegal key and value
     */
    @Test
    fun insertByScenario() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(27, 27)
        root.color = false

        val node1 = RBNode(35, 35)
        root.right = node1
        node1.parent = root

        val node2 = RBNode(30, 30)
        node1.left = node2
        node2.parent = node1
        node2.color = false

        val node3 = RBNode(40, 40)
        node1.right = node3
        node3.parent = node1
        node3.color = false

        val node4 = RBNode(28, 28)
        node2.left = node4
        node4.parent = node2

        val node5 = RBNode(25, 25)
        root.left = node5
        node5.parent = root

        val node6 = RBNode(26, 26)
        node5.right = node6
        node6.parent = node5
        node6.color = false

        val node7 = RBNode(15, 15)
        node5.left = node7
        node7.parent = node5
        node7.color = false

        val node8 = RBNode(20, 20)
        node7.right = node8
        node8.parent = node7

        val node9 = RBNode(10, 10)
        node7.left = node9
        node9.parent = node7

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27, 26, 10, 15, 28, null)) {
            actualTree.insert(i, i)
        }

        assertEquals(expectedTree, actualTree)

    }

}