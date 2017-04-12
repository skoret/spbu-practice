/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class BNode<K: Comparable<K>, V>: NodeInterface<K, V> {

    val keys = ArrayList<K>()
    val values = ArrayList<V>()
    val childs = ArrayList<BNode<K, V>>()

    fun size() = keys.size

    fun value(key: K?): V?{

        if (key == null) return null

        val index = keys.indexOf(key)
        if (index == -1) return null

        return values[index]
    }

    override fun isLeaf() = childs.isEmpty()

    override fun toString(): String {
        var node = "["
        var i = 0
        while (i < size() - 1) {
            node += this.values[i].toString() + "|"
            i++
        }
        node += this.values[i].toString() + "]"

        return node
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BNode<*, *>) return false

        if (keys != other.keys) return false
        if (values != other.values) return false

        return true
    }

}
