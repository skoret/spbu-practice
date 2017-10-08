
import Data.Maybe

f1 :: (Maybe a -> b) -> Maybe a -> Maybe a -> Maybe a -> Maybe (b, b, b)
f1 f a b c | isNothing a || isNothing b || isNothing c = Nothing
           | otherwise = Just (f a, f b, f c)

f1' :: (Maybe a -> b) -> [Maybe a] -> Maybe [b]
f1' _ [] = Just []
f1' f a | any isNothing a = Nothing
        | otherwise = Just (map f a)