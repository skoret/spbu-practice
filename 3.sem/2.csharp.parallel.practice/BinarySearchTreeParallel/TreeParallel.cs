using System;
using System.Collections.Generic;

namespace BinarySearchTreeParallel
{
    public class TreeParallel<K, V> where K : IComparable<K>
    {
        private Node<K, V> _root;
        private readonly object _locker = new object();
        
        private bool IsEmpty()
        {
            return _root == null;
        }

        public Node<K, V> Search(K key)
        {
            while (true)
            {
                var curr = _root;
                while (curr != null && !curr.key.Equals(key))
                {
                    curr = curr.key.CompareTo(key) < 0 ? curr.right : curr.left;
                }
                if (curr != null)
                {
                    lock (curr)
                    {
                        var tmp = _root;
                        while (tmp != null && !tmp.key.Equals(key))
                        {
                            tmp = tmp.key.CompareTo(key) < 0 ? tmp.right : tmp.left;
                        }
                        if (tmp != curr) continue;
                        
                        return curr;
                    }
                }
                return null;
            }
        }

        public void Insert(K key, V value)
        {
            lock (_locker)
            {
                if (_root == null)
                {
                    _root = new Node<K, V>(key, value);
                    return;
                }
            }

            while (true)
            {
                var pair = Find(key);
                var curr = pair.Item1;
                var parent = pair.Item2;

                if (curr == null)
                {
                    lock (parent)
                    {
                        var tmp = Find(key).Item2;
                        
                        if (tmp != parent) continue;
                            
                        curr = new Node<K, V>(key, value) {parent = parent};

                        if (parent.right == null && parent.key.CompareTo(key) < 0)
                        {
                            parent.right = curr;
                            return;
                        }
                        if (parent.left == null && parent.key.CompareTo(key) > 0)
                        {
                            parent.left = curr;
                            return;
                        }
                    }
                }
                else
                {
                    lock (curr)
                    {
                        curr.value = value;
                        return;
                    }
                }
            }
        }
        
        public void Delete(K key)
        {
            while (true)
            {
                var pair = Find(key);
                var victim = pair.Item1;
                var parent = pair.Item2;
            
                if (victim == null) 
                    return;
                
                if (parent == null && victim.IsLeaf())
                {
                    lock (victim)
                    {
                        pair = Find(key);
                        var tmp = pair.Item1;
                        var tmpParent = pair.Item2;
                        
                        if (tmp == null || tmpParent != null || !tmp.IsLeaf()) 
                            continue;
                        
                        _root = null;
                        return;
                    }
                }
                else if (parent != null && victim.IsLeaf())
                {
                    lock (parent)
                    lock (victim)
                    {
                        pair = Find(key);
                        var tmp = pair.Item1;
                        var tmpParent = pair.Item2;
                        
                        if (tmp == null || tmpParent == null || !tmp.IsLeaf()) 
                            continue;
                        
                        if (victim == victim.parent.right)
                        {
                            victim.parent.right = null;
                        }
                        else if (victim == victim.parent.left)
                        {
                            victim.parent.left = null;
                        }
                        return;
                    }
                }
                else if (victim.right != null && victim.left == null)
                {
                    if (parent == null)
                    {
                        lock (victim)
                        {
                            pair = Find(key);
                            var tmp = pair.Item1;
                            var tmpParent = pair.Item2;
                            
                            if (tmp == null || tmpParent != null || tmp.right == null || tmp.left != null)
                                continue;

                            victim.right.parent = victim.parent;
                            _root = victim.right;
                        }
                    }
                    else
                    {
                        lock (parent)
                        lock (victim)
                        {
                            pair = Find(key);
                            var tmp = pair.Item1;
                            var tmpParent = pair.Item2;
                            
                            if (tmp == null || tmpParent == null || tmp.right == null || tmp.left != null) 
                                continue;

                            victim.right.parent = victim.parent;
                            if (victim == victim.parent.left)
                                victim.parent.left = victim.right;
                            else
                                victim.parent.right = victim.right;
                        }
                    }
                    return;
                }
                else if (victim.right == null && victim.left != null)
                {
                    if (parent == null)
                    {
                        lock (victim)
                        {
                            pair = Find(key);
                            var tmp = pair.Item1;
                            var tmpParent = pair.Item2;
                            
                            if (tmp == null || tmpParent != null || tmp.left == null || tmp.right != null) 
                                continue;

                            victim.left.parent = victim.parent;
                            _root = victim.left;
                        }
                    }
                    else
                    {
                        lock (parent)
                        lock (victim)
                        {
                            pair = Find(key);
                            var tmp = pair.Item1;
                            var tmpParent = pair.Item2;
                            
                            if (tmp == null || tmpParent == null || tmp.left == null || tmp.right != null) 
                                continue;

                            victim.left.parent = victim.parent;
                            if (victim == victim.parent.left)
                                victim.parent.left = victim.left;
                            else
                                victim.parent.right = victim.left;
                        }
                    }
                    return;
                }
                else if (parent != null)
                {
                    lock (parent)
                    lock (victim)
                    {
                        pair = Find(key);
                        var tmp = pair.Item1;
                        var tmpParent = pair.Item2;
                        
                        if (tmp == null || tmpParent == null || tmp.left == null || tmp.right == null) 
                            continue;
                        
                        var node = FindMin(victim.right);

                        if (node == null) continue;

                        lock (node)
                        {
                            var tmpNode = FindMin(tmp.right);
                            
                            if (tmpNode == null || tmpNode != node || node.left != null) 
                                continue;
                            
                            victim.key = node.key;
                            victim.value = node.value;

                            if (victim == node.parent)
                            {
                                victim.right = node.right;
                            }
                            else
                            {
                                node.parent.left = node.right;
                            }
                            if (node.right != null) node.right.parent = node.parent;
                        }
                    }
                    return;
                }
                else
                {
                    lock (victim)
                    {
                        pair = Find(key);
                        var tmp = pair.Item1;
                        var tmpParent = pair.Item2;
                        
                        if (tmp == null || tmpParent != null || tmp.left == null || tmp.right == null)
                            continue;
                        
                        var node = FindMin(victim.right);

                        if (node == null) continue;

                        lock (node)
                        {
                            var tmpNode = FindMin(tmp.right);
                            
                            if (tmpNode == null || tmpNode != node || node.left != null)
                                continue;

                            victim.key = node.key;
                            victim.value = node.value;

                            if (victim == node.parent)
                            {
                                victim.right = node.right;
                            }
                            else
                            {
                                node.parent.left = node.right;
                            }
                            if (node.right != null) 
                                node.right.parent = node.parent;
                        }
                    }
                    return;
                }
            }
        }

        private Tuple<Node<K, V>, Node<K, V>> Find(K key)
        {
            var tmp = _root;
            var tmpParent = tmp.parent;

            while (tmp != null && !tmp.key.Equals(key))
            {
                tmpParent = tmp;
                tmp = tmp.key.CompareTo(key) < 0 ? tmp.right : tmp.left;
            }
            
            return new Tuple<Node<K, V>, Node<K, V>>(tmp, tmpParent);
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