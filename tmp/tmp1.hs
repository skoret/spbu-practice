module Demo where
import Data.Char
import Data.List

factorial n = if n == 0 then 1 else n * factorial (n - 1)

factorial' 0 = 1
factorial' n =  if n < 0 then error "arg must be >= 0" else n * factorial' (n - 1)

factorial'' n | n == 0 = 1
              | n > 0 = n * factorial'' (n - 1)
              | otherwise = error "arg must be >= 0"

factorial''' n | n >= 0 = let
                   helper acc 0 = acc
                   helper acc n = helper (acc * n) (n - 1)
                 in helper 1 n
               | otherwise = error "arg must be >= 0"

doubleFact :: Integer -> Integer
doubleFact 0 = 1
doubleFact 1 = 1
doubleFact 2 = 2
doubleFact n = if n < 0 then error "arg must be >= 0" else n * doubleFact (n - 2)

fibonacci :: Integer -> Integer
fibonacci n | n == 0 = 0
            | n == 1 = 1
            | n > 0 = fibonacci (n - 1) + fibonacci (n - 2)
            | n < 0 = fibonacci (n + 2) - fibonacci (n + 1)

fibonacci' n | n >= 0 = helper' 0 1 n
             | n < 0 = helper'' 0 1 n

helper' acc1 acc2 0 = acc1
helper' acc1 acc2 n = helper' (acc1 + acc2) acc1 (n - 1)

-- 0)_0 1)_1 2)_1 3)_2 4)_3 5)_5 6)_8

helper'' acc1 acc2 0 = acc1
helper'' acc1 acc2 n = helper'' (acc2 - acc1) acc1 (n + 1)

-- 0)_0 -1)_1 -2)_-1 -3)_2 -4)_-3

roots :: Double -> Double -> Double
         -> (Double, Double)
roots a b c =
    let 
        d = sqrt $ b ^ 2 - 4 * a * c
        x1 = (-b - d) / (2 * a)
        x2 = (-b + d) / (2 * a)
    in (x1, x2)

rootsDiff a b c = let
    (x1, x2) = roots a b c
    in x2 - x1

seqA :: Integer -> Integer
seqA n | n == 0 = 1
       | n == 1 = 2
       | n == 2 = 3
       | otherwise = let
            helper a0 a1 a2 0 = a0
            helper a0 a1 a2 n = helper a1 a2 (a2 + a1 - 2 * a0) (n - 1)
         in helper 1 2 3 n

sum'n'count :: Integer -> (Integer, Integer)
sum'n'count x | x == 0 = (0, 1)
              | otherwise = helper 0 0 $ abs x
    where
        helper acc dig x | x == 0 = (acc, dig)
                         | otherwise = helper (acc + x `mod` 10) (dig + 1) (x `div` 10)

integration :: (Double -> Double) -> Double -> Double -> Double
integration f a b | a == b = 0
                  | a > b = - integration f b a
                  | otherwise = step * ((f a + f b) / 2 + helper 0 (a + step))
    where
        step = (b - a) / 1000
        helper acc x | x >= b = acc
                     | otherwise = helper (acc + f x) (x + step)

-- ip = show a ++ show b ++ show c ++ show d

addTwo :: a -> a -> [a] -> [a]
addTwo a b c = (a:b:c)

oddsOnly :: Integral a => [a] -> [a]
oddsOnly [] = []
oddsOnly (x:xs) | odd x = x:(oddsOnly xs)
                | even x = oddsOnly xs


isPalindrome :: Eq a => [a] -> Bool
isPalindrome [] = True
isPalindrome [_] = True
isPalindrome [x,y] = x == y
isPalindrome (x:xs) | x == last xs = isPalindrome $ init xs
                    | otherwise = False

groupElems :: Eq a => [a] -> [[a]]
groupElems [] = []
groupElems (x:xs) = ((fst (span (== x) (x:xs))):(groupElems (snd (span (== x) xs))))

a = 10
b = 0
c = 0
d = 1

fold' f (x:xs) = foldl f (show x) xs

ip = fold' (\acc x -> acc ++ "." ++ (show x)) [a,b,c,d]

grault x 0 = x
grault x y = x

perms :: [a] -> [[a]]
perms [] = [[]]
perms [a] = [[a]]
perms (x:xs) = f x (length xs) $ perms xs
        where
            f z 0 zs = concatMap (\a -> [take 0 a ++ [z] ++ drop 0 a]) zs
            f z n zs = f z (n-1) zs ++ concatMap (\a -> [take n a ++ [z] ++ drop n a]) zs

delAllUpper :: String -> String
delAllUpper = unwords . filter (\x -> any isLower x) . words 

max3' :: Ord a => (a,a,a) -> a
max3' (a,b,c) = max a $ max b c

max3 :: Ord a => [a] -> [a] -> [a] -> [a]
max3 a b c = map (\x -> max3' x) $ zipWith3 (,,) a b c

data Odd = Odd Integer 
    deriving (Eq, Ord, Show)

instance Enum Odd where
    succ (Odd x) = Odd (x + 2)
    pred (Odd x) = Odd (x - 2)
    toEnum n = Odd (toInteger (2*n - 1))
    fromEnum (Odd x) = fromIntegral (x+1) `div` 2
    enumFrom (Odd x) = iterate succ (Odd x)
    enumFromThen (Odd x) (Odd y) = iterate (\(Odd z) -> Odd (z + y - x)) (Odd x)
    enumFromTo (Odd x) (Odd y) | (x > y) = []
                               | otherwise = takeWhile (/= (Odd y)) $ enumFrom (Odd x)
    -- enumFromThenTo (Odd x) (Odd y) (Odd z) | (x > z) && (x < y) = []
    --                                        | 
    --                                        |  takeWhile (<= (Odd z)) $ enumFromThen (Odd x) (Odd y)

test0 = succ (Odd 1) == (Odd 3)
test1 = pred (Odd 3) == (Odd 1)
-- enumFrom
test2 = (take 3 $ [Odd 1 ..]) == [Odd 1,Odd 3,Odd 5]
-- enumFromTo
-- -- По возрастанию
test3 = (take 3 $ [Odd 1..Odd 7]) == [Odd 1,Odd 3,Odd 5]
-- -- По убыванию
test4 = (take 3 $ [Odd 7..Odd 1]) == []
-- enumFromThen
-- -- По возрастанию
test5 = (take 3 $ [Odd 1, Odd 3 ..]) == [Odd 1,Odd 3,Odd 5]
-- -- По убыванию
test6 = (take 3 $ [Odd 3, Odd 1 ..]) == [Odd 3,Odd 1,Odd (-1)]
-- enumFromThenTo
-- -- По возрастанию
test7 =([Odd 1, Odd 5 .. Odd 7]) == [Odd 1,Odd 5]
-- -- По убыванию
test8 =([Odd 7, Odd 5 .. Odd 1]) == [Odd 7,Odd 5,Odd 3,Odd 1]
-- -- x1 < x3 && x1 > x2
test9 =([Odd 7, Odd 5 .. Odd 11]) == []
-- -- x1 > x3 && x1 < x2
test10 =([Odd 3, Odd 5 .. Odd 1]) == []

allTests = zip [0..] [test0, test1, test2, test3, test4, test5, test6, test7, test8, test9, test10]

-- coins = [2, 3, 7]
-- change :: Integer -> [[Integer]]
-- change n | (n<=0) = []
--          | (n < head coins) = []
--          | otherwise = [ (x:xs) | x <- coins, xs <- change (n-x), x<=n ]

evenOnly :: [a] -> [a]
evenOnly = f `seq` snd . foldl f (1,[])
    where
        f :: (Integer,[a]) -> a -> (Integer,[a])
        f = (\(n,s) x -> if mod n 2 == 0 then (n+1,s ++ [x]) else (n+1,s))

evenOnly' :: [a] -> [a]
evenOnly' = f `seq` reverse . snd . foldr f (0,[])
    where 
        f :: a -> (Integer,[a]) -> (Integer,[a])
        f = (\x p -> if mod (fst p) 2 == 0 then ((fst p)+1,(snd p) ++ [x]) else ((fst p)+1,snd p))


data Bit = Zero | One deriving Show
data Sign = Minus | Plus deriving Show
data Z = Z Sign [Bit] deriving Show

zToInt :: Z -> Int
zToInt (Z Minus a) = - (zToInt (Z Plus a))
zToInt (Z Plus a) = snd (foldl f (0,0) a)
    where
        f (n,x) Zero = (n+1,x)
        f (n,x) One = (n+1,x+2^n)

intToZ :: Int -> Z
intToZ a | a >= 0 = Z Plus (unfoldr ff a)
         | otherwise = Z Minus (unfoldr ff (abs a))
    where
        ff :: Int -> Maybe (Bit, Int)
        ff 0 = Nothing
        ff x = Just (if mod x 2 == 1 then One else Zero, div x 2)
        

add :: Z -> Z -> Z
add a b = intToZ (x+y)
    where
        x = zToInt a
        y = zToInt b
