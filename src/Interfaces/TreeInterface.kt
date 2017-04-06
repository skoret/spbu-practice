/**
 * Created by Sergey Skaredov.
 * SPSU. 2017.
 */

interface TreeInterface<K: Comparable<K>, V> {

    fun search(key: K?): V?

    fun insert(key: K?, value: V?)

    fun delete(key: K?)

}