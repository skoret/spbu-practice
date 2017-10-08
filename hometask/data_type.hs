
-- #0
curL_un = curry
succ0 f = curry . f

-- #1
-- curR0 = curry
-- succ1 n = ???

-- #2
deep'R_0 (a,b) = (b,a)
succ2 f (a,b) = (c,(a,d))
    where
        (c,d) = f b

-- -- #3
deepR_0 (a,b) = b
succ3 f (a,b) = f b

-- -- #4
f_1 a (f1, f2) = (f1 a, f2 a)
succ4 f a (f1,f2) = (f1 a, f a f2)

-- #5
