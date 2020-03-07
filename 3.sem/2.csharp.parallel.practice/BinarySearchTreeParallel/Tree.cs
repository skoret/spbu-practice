using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace BinarySearchTreeParallel
{
    public class Tree<K, V> where K : IComparable<K>
    {
        private Node<K, V> _root;
        
        private bool IsEmpty()
        {
            return _root == null;
        }

        public Node<K, V> Search(K key)
        {
            var curr = _root;
            while (curr != null && !curr.key.Equals(key))
            {
                curr = curr.key.CompareTo(key) < 0 ? curr.right : curr.left;
            }
            return curr;
        }

        public void Insert(K key, V value)
        {
            if (_root == null)
            {
                _root = new Node<K, V>(key, value);
                return;
            }

            var curr = _root;
            var parent = curr.parent;

            while (curr != null && !curr.key.Equals(key))
            {
                parent = curr;
                curr = curr.key.CompareTo(key) < 0 ? curr.right : curr.left;
            }

            if (curr == null)
            {
                curr = new Node<K, V>(key, value) {parent = parent};

                if (parent.key.CompareTo(key) < 0)
                {
                    parent.right = curr;
                    return;
                }
                if (parent.key.CompareTo(key) > 0)
                {
                    parent.left = curr;
                    return;
                }
            }
            else
            {
                curr.value = value;
                return;
            }
        }

        public void Delete(K key)
        {
            var victim = Search(key);
            if (victim == null) return;
            Delete(victim);
        }
        
        private bool Delete(Node<K, V> victim)
        {
            if (victim == null) return false;
                
            if (victim.IsLeaf())
            {                
                if (victim == _root)
                {
                    _root = null;
                    return true;
                }

                if (victim == victim.parent.right)
                {
                    victim.parent.right = null;
                    return true;
                }
                if (victim == victim.parent.left)
                {
                    victim.parent.left = null;
                    return true;
                }
            
            }
            else if (victim.right != null)
            {
                var next = FindMin(victim.right);
                var res = Delete(next);
                if (!res) return false;
                victim.key = next.key;
                victim.value = next.value;
                return true;
            }
            else if (victim.left != null)
            {
                var prev = FindMax(victim.left);
                var res = Delete(prev);
                if (!res) return false;
                victim.key = prev.key;
                victim.value = prev.value;
                return true;
            }
            return false;
        }

        private Node<K, V> FindMin()
        {
            return FindMin(_root);
        }
        
        private Node<K, V> FindMin(Node<K, V> node)
        {
            if (node == null) return null;
            var minNode = node;
            while (minNode.left != null)
            {
                minNode = minNode.left;
            }
          return minNode;
        }

        private Node<K, V> FindMax()
        {
            return FindMax(_root);
        }

        private Node<K, V> FindMax(Node<K, V> node)
        {
            if (node == null) return null;
            var maxNode = node;
            while (maxNode.right != null)
            {
                maxNode = maxNode.right;
            }
            return maxNode;
        }

        private Node<K, V> NextSmaller(Node<K, V> node)
        {
            if (node == null) return null;
            
            var smaller = node;
    
            if (smaller.left != null) return FindMax(smaller.left);

            while (smaller != null && smaller == smaller.parent?.left)
            {
                smaller = smaller.parent;
            }

            return smaller?.parent;
        }
    
        // reverse in-order iterator
        public IEnumerator<Node<K, V>> GetEnumerator()
        {
            var node = FindMax();
            var last = FindMin();

            while (node != null && node.key.CompareTo(last.key) >= 0)
            {
                var next = node;
                node = NextSmaller(node);
                yield return next;
            }
        }
    
        public void Print()
        {
            if (IsEmpty())
            {
                Console.WriteLine("tree is empty");
                return;
            }

            Console.WriteLine();
            
            foreach (var node in this)
            {
                var indent = "";
                var i = 0;

                while (i < node.High())
                {
                    i++;
                    indent += "  ";
                }

                Console.WriteLine(indent + node.Branch() + node);
            }

            Console.WriteLine("_____________________");
        }
    }
}