
add' 0 = id
add' x | x > 0 = add' (x-1) . (+1)
       | x < 0 = add' (x+1) . (subtract 1)

mult' 1 = id
mult' x = helper x 0 
    where
        helper x acc y | x > 0 = helper (x-1) (acc + y) y
                       | x < 0 = helper (x+1) (acc - y) y
                       | otherwise = acc

sub' x = negate . add' (-x)

quot' x y = mult' (mult' (signum x) (signum y)) (helper 0 (abs x) (abs y))
        where
            helper acc x y | x < y = acc
                           | otherwise = helper (acc+1) (x-y) y

rem' x y = mult' (signum x) (helper (abs x) (abs y))
        where
            helper x y | x < y = x
                       | otherwise = helper (x-y) y  

isPrime n = iterate 2
        where
            n' = abs n
            iterate i | i*i > n' = True
                       | otherwise = rem' n' i /= 0 && iterate (i+1)

divCount n = helper 1 n 1
        where
            helper acc n p | n == 1 = 1
                           | p*2 > n = acc
                           | rem' n p == 0 = helper (acc+1) n (p+1)
                           | otherwise = helper acc n (p+1)

divSum n = helper 0 n 1
        where
            helper acc n p | n == 1 = 1
                           | p*2 > n = acc + n
                           | rem' n p == 0 = helper (acc + p) n (p+1)
                           | otherwise = helper acc n (p+1)

f 1 = 1 + 1/1
f n = 1 + 1/(f (n-1))

b n = helper 0 n
    where 
        helper acc n | n == 0 = acc
                     | otherwise = helper (1/(n + acc)) (n-1)

sumsin n = (sin (sumseq 0 n)) / helper 0 n
    where
        sumseq acc n | n == 0 = acc
                     | otherwise = sumseq (acc + n) (n-1)
        helper acc n | n == 0 = acc
                     | otherwise = helper (acc + sin n) (n-1)

sumfac n = helper 0 n
    where
        fact acc n | n == 0 = acc
                   | otherwise = fact (acc * n) (n-1)
        helper acc n | n == 0 = acc
                     | otherwise = helper (acc + fact 1  n) (n-1)

-- серёжино
nseq n = count 1 n 
    where
        count p n | p > n = 0
                  | p == n = 1
                  | p < n = count (p+1) (n-p) + count (p+1) n

g n = iterate 2 n
    where
        iterate p n | p == 2 && rem' n 2 /= 0 = isPrime (n-2)
                    | (p*2 < n) 
                        && (not (isPrime p) || not (isPrime (n-p))) = iterate (p+1) n
                    | p /= (n-p) = True
                    | otherwise = False