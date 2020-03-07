package Red_black_tree

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import RedBlackTree
import RBNode

internal class DeletionTest {

    private fun createTree(): RedBlackTree<Int, Int> {

        val tree = RedBlackTree<Int, Int>()

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

        tree.root = root

        return tree

    }

    @Test
    fun deleteFromEmptyTree() {

        val expectedTree = RedBlackTree<Int, Int>()
        val actualTree = RedBlackTree<Int, Int>()

        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun deleteNonExistentKey() {

        val expectedTree = createTree()

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27, 26, 10, 15, 28)) {
            actualTree.insert(i, i)
        }

        for (i in listOf(21, 22, 31, 32, 41, 42)) {
            actualTree.delete(i)
        }

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun deleteIllegalKey() {

        val expectedTree = createTree()

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27, 26, 10, 15, 28, null)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(null)

        assertEquals(expectedTree, actualTree)

    }

    @Test
    fun deleteRootLeaf() {

        val expectedTree = RedBlackTree<Int, Int>()

        val actualTree = RedBlackTree<Int, Int>()

        actualTree.insert(25, 25)

        actualTree.delete(25)

        assertEquals(expectedTree, actualTree)
    }

    /** Simple == deletion root with two red children
     * */
    @Test
    fun deleteRootNonLeafSimple() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(30, 30)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(25)

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun deleteRightRedLeaf() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(25, 20, 30)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun deleteLeftRedLeaf() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(30, 30)
        root.right = node1
        node1.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(25, 20, 30)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)
    }

    /** Internal node == node with one or two children
     *  Case1 == next smaller/bigger node is red leaf
     */
    @Test
    fun deleteInternalNodeCase1() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(40, 40)
        root.right = node2
        node2.parent = root
        node2.color = false

        val node3 = RBNode(27, 27)
        node2.left = node3
        node3.parent = node2

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(25, 20, 30, 40, 27)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    /** Case2 == Black node with one left red child
     */
    @Test
    fun deleteInternalNodeCase2() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(27, 27)
        root.right = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(25, 20, 30, 27)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    /** Case3 == Black node with one right red child
     */
    @Test
    fun deleteInternalNodeCase3() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(20, 20)
        root.left = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(40, 40)
        root.right = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(25, 20, 30, 40)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    /** Case1 == (node == root)
     * */
    @Test
    fun case1() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(30, 30)
        root.right = node1
        node1.parent = root

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 35)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(35)
        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)

    }

    /** Case2Left == red brother with two black child
     * when (node < parent)
     * */
    @Test
    fun case2Left() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(30, 30)
        root.color = false

        val node1 = RBNode(40, 40)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(45, 45)
        node1.right = node2
        node2.parent = node1

        val node3 = RBNode(25, 25)
        root.left = node3
        node3.parent = root
        node3.color = false

        val node4 = RBNode(27, 27)
        node3.right = node4
        node4.parent = node3

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 27, 45)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)
    }

    /** Case2Right == red brother with two black child
     * when (node > parent)
     * */
    @Test
    fun case2Right() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(20, 20)
        root.color = false

        val node1 = RBNode(25, 25)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(15, 15)
        root.left = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 15)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    /** Case3 == black brother with two black child
     * */
    @Test
    fun case3() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(35, 35)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(20, 20)
        root.left = node2
        node2.parent = root
        node2.color = false

        val node3 = RBNode(40, 40)
        node1.right = node3
        node3.parent = node1

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(27)
        actualTree.delete(30)

        assertEquals(expectedTree, actualTree)
    }

    /** Case4Left == black brother with red child
     * when (parent < child < brother)
     * */
    @Test
    fun case4Left() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(30, 30)
        root.color = false

        val node1 = RBNode(35, 35)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(25, 25)
        root.left = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 35, 30)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)
    }

    /** Case4Right == black brother with red child
     * when (parent > child > brother)
     * */
    @Test
    fun case4Right() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(30, 30)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(20, 20)
        root.left = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 30, 35, 25)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(35)

        assertEquals(expectedTree, actualTree)
    }

    /** Case5Left == black brother with red child
     * when (child > brother > parent)
     * */
    @Test
    fun case5Left() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(30, 30)
        root.color = false

        val node1 = RBNode(40, 40)
        root.right = node1
        node1.parent = root
        node1.color = false

        val node2 = RBNode(25, 25)
        root.left = node2
        node2.parent = root
        node2.color = false

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(20)

        assertEquals(expectedTree, actualTree)
    }

    /** Case5Right == black brother with red child
     * when (parent > brother > child)
     * */
    @Test
    fun case5Right() {

        val expectedTree = RedBlackTree<Int, Int>()

        val root = RBNode(25, 25)
        root.color = false

        val node1 = RBNode(27, 27)
        root.right = node1
        node1.parent = root

        val node2 = RBNode(26, 26)
        node1.left = node2
        node2.parent = node1
        node2.color = false

        val node3 = RBNode(35, 35)
        node1.right = node3
        node3.parent = node1
        node3.color = false

        val node4 = RBNode(30, 30)
        node3.left = node4
        node4.parent = node3

        val node5 = RBNode(15, 15)
        root.left = node5
        node5.parent = root
        node5.color = false

        val node6 = RBNode(20, 20)
        node5.right = node6
        node6.parent = node5

        val node7 = RBNode(10, 10)
        node5.left = node7
        node7.parent = node5

        expectedTree.root = root

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 35, 27, 26, 10, 15)) {
            actualTree.insert(i, i)
        }

        actualTree.delete(40)

        assertEquals(expectedTree, actualTree)
    }

    @Test
    fun deleteByScenario() {

        val expectedTree = RedBlackTree<Int, Int>()

        val actualTree = RedBlackTree<Int, Int>()

        for (i in listOf(20, 25, 30, 40, 27, 45, 19, 18, 21, 22, 23, 17, 26)) {
            actualTree.insert(i, i)
        }

        for (i in listOf(17, 27, 18, 22, 23, 26, 19, 40, 25, 30, 20, 21, 45)) {
            actualTree.delete(i)
        }

        assertEquals(expectedTree, actualTree)

    }

}