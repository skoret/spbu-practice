
n = [1..]

f' = 1:(2:f')

fib = 1:1:(zipWith (+) fib (tail fib))

s = [x+y | x <- n, y <- fib]

t1 [] x = x
t1 (x:xs) y = x:(t1 y xs)

t2 :: [[a]]->[a]
t2 [] = []
t2 (xx:xxs) = t1 xx (t2 xxs)

t3 :: Integer->[Integer]
t3 n = f [2..n]
    where
        f [] = []
        f (x:xs) = x : f (xs `minus` [x*x, x*x+x..n])
        minus (x:xs) (y:ys) = case (compare x y) of
            LT -> x : minus xs (y:ys)
            EQ -> minus xs ys
            GT -> minus (x:xs) ys
        minus xs _ = xs

t4 :: Integer->[Integer]
t4 0 = []
t4 n = 
            