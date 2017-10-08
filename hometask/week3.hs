
import Data.Maybe

t1 :: (Maybe a -> b) -> Maybe a -> Maybe a -> Maybe a -> Maybe (b, b, b)
t1 f a b c | isNothing a || isNothing b || isNothing c = Nothing
           | otherwise = Just (f a, f b, f c)

t1' :: (Maybe a -> b) -> [Maybe a] -> Maybe [b]
t1' _ [] = Just []
t1' f a | any isNothing a = Nothing
        | otherwise = Just (map f a)

data Tree a = Leaf | Node a (Tree a) (Tree a) deriving (Show, Eq)

find Leaf _ = False
find (Node a x y) b | a == b = True
                    | otherwise = find x b || find y b

mapT :: (a -> b) -> (Tree a) -> (Tree b)
mapT f Leaf = Leaf
mapT f (Node a x y) = Node (f a) (mapT f x) (mapT f y)

data Tree' a = Leaf' | Node' a (Tree' a) (Tree' a) deriving (Show, Eq)

find' Leaf' _ = False
find' (Node' a x y) b | a == b = True
                      | a > b = find' x b
                      | otherwise = find' y b

data Tree'' a = Leaf'' a | Node'' a (Tree'' a) (Tree'' a) deriving (Show, Eq)

find'' (Leaf'' a) b = a == b
find'' (Node'' a x y) b | a == b = True
                        | a > b = find'' x b
                        | otherwise = find'' y b

mapT'' f (Leaf'' a) = Leaf'' (f a)
mapT'' f (Node'' a x y) = Node'' (f a) (mapT'' f x) (mapT'' f y)
