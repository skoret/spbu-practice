using System;
using System.Collections.Generic;
using System.Text;

namespace BinarySearchTreeParallel
{
    public class Node<K, V>: IEquatable<Node<K, V>> where K : IComparable<K>
    {
        public K key;
        public V value;
        public Node<K, V> parent = null;
        public Node<K, V> left= null;
        public Node<K, V> right = null;

        public Node(K k, V v)
        {
            key = k;
            value = v;
        }
        
        public bool IsLeaf()
        {
            return right == null && left == null;
        }

        internal int High()
        {
            var currentNode = this;
            var count = 0;
            while (currentNode.parent != null)
            {
                currentNode = currentNode.parent;
                count++;
            }
            return count;
        }

        internal string Branch()
        {
            if (parent == null) return "";
            if (key.CompareTo(parent.key) > 0) return "/";
            return "\\";
        }

        public override string ToString()
        {
            return "(" + key + "," + value + ")";
        }

        public static bool operator ==(Node<K, V> node1, Node<K, V> node2)
        {
            if (ReferenceEquals(node1, node2)) return true;
            if (ReferenceEquals(node2, null)) return false;
            return node2.Equals(node1);
        }

        public static bool operator !=(Node<K, V> node1, Node<K, V> node2)
        {
            return !(node1 == node2);
        }
        
        public override bool Equals(object obj)
        {
            if (ReferenceEquals(null, obj)) return false;
            if (ReferenceEquals(this, obj)) return true;
            return Equals(obj as Node<K, V>);
        }

        public bool Equals(Node<K, V> other)
        {
            if (ReferenceEquals(null, other)) return false;
            if (ReferenceEquals(this, other)) return true;
            return EqualityComparer<K>.Default.Equals(key, other.key) && EqualityComparer<V>.Default.Equals(value, other.value);
        }

        public override int GetHashCode()
        {
            unchecked
            {
                return (EqualityComparer<K>.Default.GetHashCode(key) * 397) ^ EqualityComparer<V>.Default.GetHashCode(value);
            }
        }
    }
}