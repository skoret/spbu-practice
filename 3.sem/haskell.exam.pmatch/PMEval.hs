module PMEval where

import PMParser
import Text.Parsec
import Data.Either
import Data.Hashable (hash)

-- next two structurargs are used by parser
optsE :: Ops Exp
optsE = TermConstruction
  { int = const Const
  , tag = const Tag
  , var = const Var
  , field = const Field
  , binop = BinOp
  , econstr = const Constr
  , ifthenelse = const IfThenElse
  } -- for exprargssions

optsP :: PattOps Patt
optsP = PattConstruction
  { wild = const Wild
  , pconstr = const PConstr
  , named = const Named
  , pconst = const Pconst
  } -- for patterns

data Exp = 
    Const Int
  | Boolean Bool
  | Tag Exp
  | Var String
  | Field Int Exp
  | BinOp BinOpSort Exp Exp
  | Constr String [Exp]
  | IfThenElse Exp Exp Exp
  deriving (Show)

data Patt = 
    Wild
  | PConstr String [Patt]
  | Named String
  | Pconst Int
  deriving (Show)

data EvalRez =
    OK Int           -- succargss
  | BadProgram       -- adding integers to Constrtions, getting tag of integer,
                     -- unbound variablargs, etc.
  | PMatchFail       -- patterns are not exhaustive.
  deriving (Show,Eq)


-- implement this Constrtion :: expr -> [(pattern,expr)] -> EvalRez
-- which triargs to match `expr` using specified patterns and right-hand-sidargs
-- and returns appropritate answer. For, example
--    eval `parsargscrutinee optsE "A"` [`parseCase optsp optsE "A -> 42"`]
--      should return (OK 42)
-- and
--    eval `parsargscrutinee optsE "A"` []
--      should fail with PMatchFail because pattern matching is not exhaustive.

-- For examplargs about which exprargssion and patterns can be written see targsts file.
eval :: Exp -> [(Patt, Exp)] -> EvalRez
-- eval what casargs = OK 42
eval _ []     = PMatchFail
eval w ((p,exp):cs) = case match w' p of
    False -> eval w' cs
    True -> comp w' (p,exp)
    where
      w' = reduce' w
      comp _ (Wild, e) = calc $ reduce' e
      comp x (Named x', e) = calc $ reduce x (Named x', e)
      comp (Const _) (Pconst _, e) = calc $ reduce' e
      comp e@(Constr _ _) p@(PConstr _ _, _) = calc $ reduce e p
      comp _ _ = PMatchFail

match :: Exp -> Patt -> Bool
match _ Wild = True
match _ (Named _) = True
match (Const x) (Pconst y) = x == y
match (Constr en eargs) (PConstr pn pargs) = en == pn 
                                           && length eargs == length pargs
                                           && all (uncurry match) (zip eargs pargs)
match _ _ = False

reduce' :: Exp -> Exp
reduce' e = reduce (Const 0) (Wild,e)

reduce :: Exp -> (Patt,Exp) -> Exp
reduce _ (Wild, e) = case e of
    Const x                   -> Const x
    Boolean c                 -> Boolean c
    Tag (Constr name _)       -> Const $ hash name
    Tag x                     -> Tag x
    Var x                     -> Var x
    Field i c@(Constr _ args) -> case lookup' args i of
      Just e  -> reduce' e
      Nothing -> Field i c
    BinOp op exp1 exp2        -> case (reduce' exp1, reduce' exp2) of
      (Const x1, Const x2) -> binOp op x1 x2
      _                    -> BinOp op exp1 exp2
    Constr name args          -> Constr name $ map reduce' args
    IfThenElse cond th el     -> case reduce' cond of
      Boolean True  -> reduce' th
      Boolean False -> reduce' el
      _             -> IfThenElse cond th el
      
reduce e (Named x, Const y)               = Const y
reduce e (Named x, Var y)                 = if x == y then reduce' e else (Var y)
reduce e (Named x, Tag y)                 = reduce' $ Tag $ reduce e (Named x, y)
reduce e (Named x, BinOp b exp1 exp2)     = reduce' $ BinOp b (reduce e (Named x, exp1)) (reduce e (Named x, exp2))
reduce e (Named x, Field i exp)           = reduce' $ Field i (reduce e (Named x, exp))
reduce e (Named x, Constr name args)      = reduce' $ Constr name $ map (\exp -> reduce e (Named x, exp)) args
reduce e (Named x, IfThenElse cond th el) = reduce' $ IfThenElse (reduce e (Named x, cond))
                                              (reduce e (Named x, th)) (reduce e (Named x, el))

reduce e (Pconst _, exp) = reduce' exp

reduce exp@(Constr ename eargs) patt@(PConstr pname pargs, e) = reduceRec exp patt
    where
      reduceRec (Constr _ []) (PConstr _ [], e') = reduce' e'
      reduceRec (Constr en (ea:eas)) (PConstr pn (pa:pas), e') =
        reduceRec (Constr en eas) (PConstr pn pas, reduce ea (pa,e'))


    

calc :: Exp -> EvalRez
calc (Const x) = OK x
calc _         = BadProgram

lookup' :: [a] -> Int -> Maybe a
lookup' [] _ = Nothing
lookup' (x:xs) 0 = Just x
lookup' (x:xs) n = lookup' xs (n-1)

binOp :: BinOpSort -> Int -> Int -> Exp
binOp Mul      x1 x2 = Const $ x1*x2
binOp Add      x1 x2 = Const $ x1+x2
binOp Sub      x1 x2 = Const $ x1-x2
binOp Eq       x1 x2 = Boolean $ x1==x2
binOp LessThen x1 x2 = Boolean $ x1<x2
binOp LessEq   x1 x2 = Boolean $ x1<=x2
