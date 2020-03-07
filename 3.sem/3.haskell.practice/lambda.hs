import Data.List

data Lam = Var String
         | Abs String Lam
         | App Lam Lam deriving Show

ff :: Lam -> [String]
ff (Var a) = [a]
ff (Abs a b) = filter (/= a) $ ff b
ff (App a b) = ff a ++ ff b

subst :: Lam -> [(String, Lam)] -> Lam
subst term [] = term
subst (Var a) xs = maybe (Var a) snd $ find (\(s,_) -> s == a) xs
subst (Abs a b) xs = Abs a term
    where
        term = subst b $ filter (\(s,_) -> s /= a) xs
subst (App a b) xs = App (subst a xs) (subst b xs)
