
minlist [] = undefined
minlist [x] = x
minlist (x:y:xs) | x <= y = minlist (x:xs) 
                 | otherwise =  minlist (y:xs)

sumprod [] = 0
sumprod [x] = 0
sumprod (x:y:xs) = x*y + sumprod (y:xs)


check cond [] = False
check cond (x:xs) = cond x || check cond xs

sameDigits [] = False
sameDigits (x:xs) = (check (mod10 x) xs) || sameDigits xs
    where
        mod10 x y = mod x 10 == mod y 10
        

upDown [] = False
upDown [x] = False
upDown (x:y:xs) = up (x:y:xs)
    where
        up (x:y:xs) | xs == [] = False
                    | x == y = False
                    | x < y = up (y:xs)
                    | otherwise = down (y:xs)
        down (x:y:xs) | xs == [] = x > y
                      | x > y = down (y:xs)
                      | otherwise = False

f [] _ = []
f [x] len = [len]
f (l:ls) len | l < head ls = f ls (len+1)
             | otherwise = len:(f ls 1)

parts [] = False
parts [x] = False
parts (x:xs) = (2 <= foldr gcd l ls)
    where
        (l:ls) = f (x:xs) 1

-- parts2 [] = False
-- parts2 [x] = False
-- parts2 (x:xs) | odd length (x:xs) = False
--               | 

-----------------------------------------------------