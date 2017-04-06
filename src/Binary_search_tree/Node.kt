/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class Node<K: Comparable<K>, V>(var key: K, var value: V): NodeInterface<K, V> {

    var parent: Node<K, V>? = null
    var left: Node<K, V>? = null
    var right: Node<K, V>? = null

    override fun toString() = this.value.toString()

    override fun isLeaf(): Boolean = ((right == null) && (left == null))

    fun high(): Int {
        var currentNode = this
        var count = 0
        while (currentNode.parent != null) {
            currentNode = currentNode.parent!!
            count++
        }
        return count
    }

    fun branch(): String = when {
        this.parent == null -> ""
        this.key > this.parent!!.key -> "/"
        this.key < this.parent!!.key -> "\\"
        else -> ""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Node<*, *>

        if (key != other.key) return false
        if (value != other.value) return false

        return true
    }

}
