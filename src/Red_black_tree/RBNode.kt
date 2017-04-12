/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

class RBNode<K: Comparable<K>, V>(var key: K, var value: V): NodeInterface<K, V> {

    var parent: RBNode<K, V>? = null
    var left: RBNode<K, V>? = null
    var right: RBNode<K, V>? = null
// true == red, false == black
    var color: Boolean = true

    override fun toString(): String {
        // {v} == red, [v] == black
        val open: String
        val close: String
        when {
            color -> {
                open = "{"
                close = "}"
            }
            else -> {
                open = "["
                close = "]"
            }
        }
        return open + this.value.toString() + close
    }

    override fun isLeaf(): Boolean = ((right == null) && (left == null))

    fun grandparent(): RBNode<K, V>? = this.parent?.parent

    fun uncle(): RBNode<K, V>? = this.parent?.brother()
    
    fun recoloring() {
        this.color = !this.color
    }

    fun brother(): RBNode<K, V>? {
        if (this == this.parent?.left) return this.parent!!.right
        return this.parent?.left
    }

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
        if (other !is  RBNode<*, *>) return false

        if (key != other.key) return false
        if (value != other.value) return false
        if (color != other.color) return false

        return true
    }

}
