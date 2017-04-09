import java.util.*

/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class BTree<K: Comparable<K>, V>(val t: Int): TreeInterface<K, V>, Iterable<BNode<K, V>> {

    private var root = BNode<K, V>()
    private val maxSize = 2*t -1
    private var height = 0

    override fun search(key: K?): V?{

        val node = searchNode(key) ?: return null

        return node.value(key)
    }

    private fun searchNode(key: K?, node: BNode<K, V>? = root): BNode<K, V>? {

        when {

            (key == null || node == null) -> return null

            (node.keys.contains(key)) -> return node

            (node.isLeaf()) -> return null

            else -> {

                var i = 0
                while ((i < node.keys.size) && (key > node.keys[i])) {
                    i++
                }
                return searchNode(key, node.childs[i])

            }
        }

    }

    override fun insert(key: K?, value: V?)  {

        when {
            (key == null || value == null) -> return

            (searchNode(key) != null) -> return

            (root.size() == maxSize) -> {

                val newRoot = BNode<K, V>()
                val currentRoot = root
                root = newRoot
                newRoot.childs.add(currentRoot)
                splitNode(currentRoot, 0, newRoot)
                insertNonFull(newRoot, key, value)
                height++

            }

            else -> {

                insertNonFull(root, key, value)

            }
        }
    }

    private fun splitNode(node: BNode<K, V>, i: Int, parent: BNode<K, V>) {

        val bro = BNode<K, V>()

        for (j in 0..(t - 2)) {
              bro.keys.add(node.keys[t])
              bro.values.add(node.values[t])
              node.keys.removeAt(t)
              node.values.removeAt(t)
        }

        if (!node.isLeaf()) {
            for (j in 0..(t - 1)) {
                bro.childs.add(node.childs[t])
                node.childs.removeAt(t)
            }
        }

        parent.childs.add(i + 1, bro)
        parent.keys.add(i, node.keys[t - 1])
        parent.values.add(i, node.values[t - 1])
        node.keys.removeAt(t - 1)
        node.values.removeAt(t - 1)

    }

    private fun insertNonFull(node: BNode<K, V>, key: K, value: V) {

        var i = node.size() - 1

        if (node.isLeaf()) {

            while ((i >= 0) && (key < node.keys[i])) {
                i--
            }
            node.keys.add(i + 1, key)
            node.values.add(i + 1, value)

        } else {

            while ((i >= 0) && (key < node.keys[i])) {
                i--
            }
            i++

            if (node.childs[i].size() == maxSize) {
                splitNode(node.childs[i], i, node)
                if (key > node.keys[i]) {
                    i++
                }
            }
            insertNonFull(node.childs[i], key, value)

        }

    }

    override fun delete(key: K?) {

        if (key == null) return

        if (searchNode(key) == null) {
            println("/doesn't exist/")
            return
        }

        deleteInternal(key, root)
    }

    private fun deleteInternal(key: K, node: BNode<K, V>) {

        val index = node.keys.indexOf(key)

        if (index != -1) {

            when {

                (node.isLeaf()) -> {
                    node.keys.removeAt(index)
                    node.values.removeAt(index)
                }

                (node.childs[index].keys.size >= t) -> {
                    val prevNode = nextSmaller(key, node)
                    node.keys[index] = prevNode.keys.last()
                    node.values[index] = prevNode.values.last()
                    deleteInternal(prevNode.keys.last(), node.childs[index])
                }

                (node.childs[index + 1].keys.size >= t) -> {
                    val nextNode = nextBigger(key, node)
                    node.keys[index] = nextNode.keys.first()
                    node.values[index] = nextNode.values.first()
                    deleteInternal(nextNode.keys.first(), node.childs[index + 1])
                }

                else -> {
                    mergeChilds(key, node)
                    deleteInternal(key, node.childs[index])
                }

            }

        } else {

            var i = 0
            while ((i < node.size()) && (key > node.keys[i])) {
                i++
            }

            if (node.childs[i].size() < t) {

                when {

                    (node.childs[i] != node.childs.last())
                    &&
                    (node.childs[i + 1].size() >= t) -> {

                        node.childs[i].keys.add(node.keys[i])
                        node.childs[i].values.add(node.values[i])
                        node.keys[i] = node.childs[i + 1].keys.first()
                        node.values[i] = node.childs[i + 1].values.first()
                        node.childs[i + 1].keys.removeAt(0)
                        node.childs[i + 1].values.removeAt(0)
                        if (!node.childs[i].isLeaf()) {
                            node.childs[i].childs.add(node.childs[i + 1].childs.first())
                            node.childs[i + 1].childs.removeAt(0)
                        }

                    }

                    (node.childs[i] != node.childs.first())
                    &&
                    (node.childs[i - 1].size() >= t) -> {

                        node.childs[i].keys.add(0, node.keys[i - 1])
                        node.childs[i].values.add(0, node.values[i - 1])
                        node.keys[i - 1] = node.childs[i - 1].keys.last()
                        node.values[i - 1] = node.childs[i - 1].values.last()
                        node.childs[i - 1].keys.removeAt(node.childs[i - 1].keys.size - 1)
                        node.childs[i - 1].values.removeAt(node.childs[i - 1].values.size - 1)
                        if (!node.childs[i].isLeaf()) {
                            node.childs[i].childs.add(0, node.childs[i - 1].childs.last())
                            node.childs[i - 1].childs.removeAt(node.childs[i - 1].childs.size - 1)
                        }

                    }

                    (node.childs[i] != node.childs.last()) -> {
                        mergeChilds(node.keys[i], node)
                    }

                    (node.childs[i] != node.childs.first()) -> {
                        mergeChilds(node.keys[i - 1], node)
                        i--
                    }

                }

            }

            deleteInternal(key, node.childs[i])
        }

    }

    private fun mergeChilds(key: K, parent: BNode<K, V>) {

        val index = parent.keys.indexOf(key)
        val left = parent.childs[index]
        val right = parent.childs[index + 1]

        left.keys.add(key)
        left.values.add(parent.values[index]!!)
        left.keys.addAll(right.keys)
        left.values.addAll(right.values)
        left.childs.addAll(right.childs)
        parent.childs.remove(right)
        parent.keys.removeAt(index)
        parent.values.removeAt(index)
        if (parent.keys.isEmpty()) {
            root = left
            println("new root")
        }

    }

    private fun nextSmaller(key: K, node: BNode<K, V>): BNode<K, V> {

       var currentNode = node.childs[node.keys.indexOf(key)]

        while (!currentNode.isLeaf()) {
            currentNode = currentNode.childs.last()
        }

        return currentNode

    }

    private fun nextBigger(key: K, node: BNode<K, V>): BNode<K, V> {

        var currentNode = node.childs[node.keys.indexOf(key) + 1]

        while (!currentNode.isLeaf()) {
            currentNode = currentNode.childs.first()
        }

        return currentNode

    }

    //breadth-first iterator
    override fun iterator(): Iterator<BNode<K, V>> {
        return (object: Iterator<BNode<K, V>> {

            var queue: Queue<BNode<K, V>> = LinkedList<BNode<K, V>>()
            init {
                if (root.keys.isNotEmpty()) {
                    queue.add(root)
                }
            }

            override fun hasNext() = queue.isNotEmpty()

            override fun next(): BNode<K, V> {
                val next = queue.remove()
                if (!next.isLeaf()) {
                    queue.addAll(next.childs)
                }
                return next
            }

        })
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BTree<*, *>) return false

        val iteratorThis = this.iterator()

        for (i in other) {
            if (!iteratorThis.hasNext()) return false
            if (i != iteratorThis.next()) return false
        }
        if (iteratorThis.hasNext()) return false

        return true
    }

    override fun print() {
        for (i in this) {
            println(i)
        }
        println()
        println("height $height")
        println("________________________")
    }

}