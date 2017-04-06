/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class BinarySearchTree<K: Comparable<K>, V>: TreeInterface<K, V>, Iterable<Node<K, V>> {

    private var root: Node<K, V>? = null

    fun isEmpty() = root == null

    override fun search(key: K?) = searchNode(key)?.value

    private fun searchNode(key: K?, currentNode: Node<K, V>? = root): Node<K, V>? {

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

        newNode = Node(key, value)

        if (root == null) {
            root = newNode
            return
        }
        insertRecursively(root!!, newNode)
        return
    }

    private fun insertRecursively(currentNode: Node<K, V>, newNode: Node<K, V>) {

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

    override fun delete(key: K?) {

        val node = searchNode(key) ?: return

        when {
            (node.isLeaf()) -> {
                when {
                    (node == root) -> {
                        root = null
                        return
                    }
                    (node == node.parent!!.left) -> {
                        node.parent!!.left = null
                        return
                    }
                    (node == node.parent!!.right) -> {
                        node.parent!!.right = null
                        return
                    }
                }
            }
            (node.right != null) -> {
                val nextKey = findMin(node.right)!!.key
                val nextValue = findMin(node.right)!!.value
                delete(nextKey)
                node.key = nextKey
                node.value = nextValue
            }
            (node.left != null) -> {
                val prevKey = findMax(node.left)!!.key
                val prevValue = findMax(node.left)!!.value
                delete(prevKey)
                node.key = prevKey
                node.value = prevValue
            }
        }

    }

    private fun findMin(node: Node<K, V>? = root): Node<K, V>? {
        var minNode = node
        while (minNode?.left != null) {
            minNode = minNode.left
        }
        return minNode
    }

    private fun findMax(node: Node<K, V>? = root): Node<K, V>? {
        var maxNode = node
        while (maxNode?.right != null) {
            maxNode = maxNode.right
        }
        return maxNode
    }

    private fun nextSmaller(node: Node<K, V>?): Node<K, V>? {
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
    override fun iterator(): Iterator<Node<K, V>> {
        return (object: Iterator<Node<K, V>>{
            var node = findMax()
            var next = findMax()
            val last = findMin()

            override fun hasNext(): Boolean {
                return  (node != null) && (node!!.key >= last!!.key)
            }

            override fun next(): Node<K, V> {
                next = node
                node = nextSmaller(node)
                return next!!
            }
        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BinarySearchTree<*, *>) return false

        val iteratorThis = this.iterator()

        for (i in other) {
            if (!iteratorThis.hasNext()) return false
            if (i != iteratorThis.next()) return false
        }
        if (iteratorThis.hasNext()) return false

        return true
    }

}
