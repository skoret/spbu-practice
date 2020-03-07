
import Data.Maybe

t1 :: (a -> b -> c -> d) -> Maybe a -> Maybe b -> Maybe c -> Maybe d
t1 f a b c =
        c >>= \x ->
        b >>= \y ->
        a >>= \z -> 
        return (f z y x)
 
t1' :: ([a] -> b) -> [Maybe a] -> Maybe b
t1' f a = map (\x -> )

data Zipper a = Elem [a] a [a] deriving Show

current (Elem _ b _) = b 

next (Just (Elem a b (c:cs))) = Just (Elem (b:a) c cs)
next _ = Nothing

prev (Just (Elem (a:as) b c)) = Just (Elem as a (b:c))
prev _ = Nothing

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
