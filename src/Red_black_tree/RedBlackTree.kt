/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class RedBlackTree<K: Comparable<K>, V>: TreeInterface<K, V>, Iterable<RBNode<K, V>> {

    private var root: RBNode<K, V>? = null

    fun isEmpty() = root == null

    override fun search(key: K?) = searchNode(key)?.value

    private fun searchNode(key: K?, currentNode: RBNode<K, V>? = root): RBNode<K, V>? {

        if (key == null) return null

        if ((currentNode == null) || (key == currentNode.key)) {
            return currentNode
        }

        if (key > currentNode.key) {
            return this.searchNode(key, currentNode.right)
        }
        else {
            return this.searchNode(key, currentNode.left)
        }
    }

    override fun insert(key: K?, value: V?) {

        if ((key == null) || (value == null)) return

        var newNode = searchNode(key)

        if (newNode != null) {
            newNode.value = value
            return
        }

        newNode = RBNode(key, value)

        if (root == null) {
            root = newNode
            root!!.recoloring()
            return
        }
        insertRecursively(root!!, newNode)
        balance(newNode)
        return
    }

    private fun insertRecursively(currentNode: RBNode<K, V>, newNode: RBNode<K, V>) {

        if (newNode.key > currentNode.key) {
            if (currentNode.right == null) {
                newNode.parent = currentNode
                currentNode.right = newNode
                return
            }
            else {
                insertRecursively(currentNode.right!!, newNode)
            }
        }

        if (newNode.key < currentNode.key) {
            if (currentNode.left == null) {
                newNode.parent = currentNode
                currentNode.left = newNode
                return
            }
            else {
                insertRecursively(currentNode.left!!, newNode)
            }
        }
    }

    fun rightRotate(node: RBNode<K, V>) {
        val tmp: RBNode<K, V> = node.left ?: return
        tmp.parent = node.parent
        if (node.parent != null) {
            if (node.key < node.parent!!.key) {
                node.parent!!.left = tmp
            } else {
                node.parent!!.right = tmp
            }
        } else {
            root = tmp
        }
        node.left = tmp.right
        node.left?.parent = node
        node.parent = tmp
        tmp.right = node
    }

    fun leftRotate(node: RBNode<K, V>) {
        val tmp: RBNode<K, V> = node.right ?: return
        tmp.parent = node.parent
        if (node.parent != null) {
            if (node.key < node.parent!!.key) {
                node.parent!!.left = tmp
            } else {
                node.parent!!.right = tmp
            }
        } else {
            root = tmp
        }
        node.right = tmp.left
        node.right?.parent = node
        node.parent = tmp
        tmp.left = node
    }

    private fun balance(node: RBNode<K, V>) {

        if (node.parent!!.color) {
            if ((node.uncle() != null) && (node.uncle()!!.color)) {
                balanceRecursively(node)
            } else {
                if (node.grandparent() != null) {
                    when {
                        (node.parent!!.key < node.grandparent()!!.key) && (node.key < node.parent!!.key)
                        -> balanceLeftLeft(node)
                        (node.parent!!.key > node.grandparent()!!.key) && (node.key > node.parent!!.key)
                        -> balanceRightRight(node)
                        (node.parent!!.key < node.grandparent()!!.key) && (node.key > node.parent!!.key)
                        -> balanceLeftRight(node)
                        (node.parent!!.key > node.grandparent()!!.key) && (node.key < node.parent!!.key)
                        -> balanceRightLeft(node)
                    }
                }
            }
        }

        if (root!!.color) root!!.recoloring()

        return
    }

    private fun balanceRecursively(node: RBNode<K, V>) {
        node.parent!!.recoloring()
        node.uncle()!!.recoloring()
        node.grandparent()!!.recoloring()
        if (node.grandparent() != root) balance(node.grandparent()!!)
    }

    private fun balanceLeftLeft(node: RBNode<K, V>) {
        node.parent!!.recoloring()
        node.grandparent()!!.recoloring()
        rightRotate(node.grandparent()!!)
    }

    private fun balanceRightRight(node: RBNode<K, V>) {
        node.parent!!.recoloring()
        node.grandparent()!!.recoloring()
        leftRotate(node.grandparent()!!)
    }

    private fun balanceLeftRight(node: RBNode<K, V>) {
        leftRotate(node.parent!!)
        balanceLeftLeft(node.left!!)
    }

    private fun balanceRightLeft(node: RBNode<K, V>) {
        rightRotate(node.parent!!)
        balanceRightRight(node.right!!)
    }

    override fun delete(key: K?) {
        if (key == null) return

        val node = searchNode(key, root) ?: return
        val min = findMin(node.right)

        when {
            ((node.right != null) && (node.left != null))
            -> {
                val nextKey = min!!.key
                val nextValue = min.value
                delete(min.key)
                node.key = nextKey
                node.value = nextValue
            }
            ((node == root) && node.isLeaf())
            -> {
                root = null
                return
            }
            (node.color && node.isLeaf())
            -> {
                if (node.key < node.parent!!.key) {
                    node.parent!!.left = null
                } else {
                    node.parent!!.right = null
                }
                return
            }
            (!node.color && ((node.left != null) && (node.left!!.color)))
            -> {
                node.key = node.left!!.key
                node.value = node.left!!.value
                node.left = null
                return
            }
            (!node.color && (node.right != null) && (node.right!!.color))
            -> {
                node.key = node.right!!.key
                node.value = node.right!!.value
                node.right = null
                return
            }
            else
            -> {
                case1(node)
            }
        }

        if (node.key == key) {
            if (node.key < node.parent!!.key) {
                node.parent!!.left = null
            } else {
                node.parent!!.right = null
            }
        }
        return
    }

    private fun case1(node: RBNode<K, V>) {
        if (node == root) {
            node.color = false
            return
        }

        if (node.key < node.parent!!.key) {
            case2Left(node)
        } else {
            case2Right(node)
        }
    }

    private fun case2Left(node: RBNode<K, V>) {
        val brother = node.brother()

        if (brother!!.color) {
            node.parent!!.recoloring()
            brother.recoloring()
            leftRotate(node.parent!!)
            case1(node)
            return
        }

        case3(node)
    }

    private fun case2Right(node: RBNode<K, V>) {
        val brother = node.brother()

        if (brother!!.color) {
            node.parent!!.recoloring()
            brother.recoloring()
            rightRotate(node.parent!!)
            case1(node)
            return
        }

        case3(node)
    }

    private fun case3(node: RBNode<K, V>) {
        val brother = node.brother()

        if (((brother!!.left == null) || !brother.left!!.color)
            &&
            ((brother.right == null) || !brother.right!!.color))
        {
            node.color = false
            brother.recoloring()
            if (node.parent!!.color) {
                node.parent!!.recoloring()
                return
            }
            case1(node.parent!!)
            return
        }

        if (node.key < node.parent!!.key) {
            case4Left(node)
        } else {
            case4Right(node)
        }
    }

    private fun case4Left(node: RBNode<K, V>) {
        val brother = node.brother()

        if ((brother!!.right == null) || !brother.right!!.color) {
            brother.recoloring()
            brother.left!!.recoloring()
            rightRotate(brother)
            case1(node)
            return
        }

        case5Left(node)
    }

    private fun case4Right(node: RBNode<K, V>) {
        val brother = node.brother()

        if ((brother!!.left == null) || !brother.left!!.color) {
            brother.recoloring()
            brother.right!!.recoloring()
            leftRotate(brother)
            case1(node)
            return
        }

        case5Right(node)
    }

    private fun case5Left(node: RBNode<K, V>) {
        val brother = node.brother()

        if ((brother!!.right != null) && brother.right!!.color) {
            brother.color = node.parent!!.color
            node.color = false
            node.parent!!.color = false
            brother.right!!.color = false
            leftRotate(node.parent!!)
            return
        }
    }

    private fun case5Right(node: RBNode<K, V>) {
        val brother = node.brother()

        if ((brother!!.left != null) && brother.left!!.color) {
            brother.color = node.parent!!.color
            node.color = false
            node.parent!!.color = false
            brother.left!!.color = false
            rightRotate(node.parent!!)
            return
        }
    }

    private fun findMin(node: RBNode<K, V>? = root): RBNode<K, V>? {
        var minNode = node
        while (minNode?.left != null) {
            minNode = minNode.left
        }
        return minNode
    }

    private fun findMax(node: RBNode<K, V>? = root): RBNode<K, V>? {
        var maxNode = node
        while (maxNode?.right != null) {
            maxNode = maxNode.right
        }
        return maxNode
    }

    private fun nextSmaller(node: RBNode<K, V>?): RBNode<K, V>? {
        var smaller = node ?: return null

        when {
            (smaller.left != null) -> {
                return findMax(smaller.left)
            }
            (smaller == smaller.parent?.left) -> {
                while (smaller == smaller.parent?.left) {
                    smaller = smaller.parent!!
                }
            }
        }
        return smaller.parent
    }

    // reverse in-order iterator
    override fun iterator(): Iterator<RBNode<K, V>> {
        return (object: Iterator<RBNode<K, V>>{
            var node = findMax()
            var next = findMax()
            val last = findMin()

            override fun hasNext(): Boolean {
                return  (node != null) && (node!!.key >= last!!.key)
            }

            override fun next(): RBNode<K, V> {
                next = node
                node = nextSmaller(node)
                return next!!
            }
        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RedBlackTree<*, *>) return false

        val iteratorThis = this.iterator()

        for (i in other) {
            if (!iteratorThis.hasNext()) return false
            if (i != iteratorThis.next()) return false
        }
        if (iteratorThis.hasNext()) return false

        return true
    }

    override fun print() = RBTreePrinter<K, V>().print(this)

}
