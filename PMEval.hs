module PMEval where

import PMParser
import Text.Parsec

-- next two structures are used by parser
optsE :: Ops Exp
optsE = TermConstruction
  { int = const Const
  , tag = const Tag
  , var = const Var
  , field = const Field
  , binop = BinOp
  , econstr = const Func
  , ifthenelse = const IfThenElse
  } -- for expressions

optsP :: PattOps Patt
optsP = PattConstruction
  { wild = const Wild
  , pconstr = const Pconstr
  , named = const Named
  , pconst = const Pconst
  } -- for patterns

data Exp = 
    Const Int
  | Tag String
  | Var String
  | Field Int String
  | BinOp BinOpSort Exp Exp
  | Func String [Exp]
  | IfThenElse Exp Exp Exp
  deriving (Show)

data Patt = 
    Wild
  | Pconstr String [Patt]
  | Named String
  | Pconst Int
  deriving (Show)

data EvalRez =
    OK Int           -- success
  | BadProgram       -- adding integers to functions, getting tag of integer,
                     -- unbound variables, etc.
  | PMatchFail       -- patterns are not exhaustive.
  deriving (Show,Eq)


-- implement this function :: expr -> [(pattern,expr)] -> EvalRez
-- which tries to match `expr` using specified patterns and right-hand-sides
-- and returns appropritate answer. For, example
--    eval `parseScrutinee optsE "A"` [`parseCase optsp optsE "A -> 42"`]
--      should return (OK 42)
-- and
--    eval `parseScrutinee optsE "A"` []
--      should fail with PMatchFail because pattern matching is not exhaustive.

-- For examples about which expression and patterns can be written see tests file.
eval :: Either Text.Parsec.ParseError Exp -> [Either Text.Parsec.ParseError (Patt, Exp)] -> EvalRez
-- eval what cases = OK 42
eval _ [] = PMatchFail
eval (Right what) ((Right c):cs) = case comp what c of
    PMatchFail -> eval (Right what) cs
    t -> t
    where
      comp _ (Wild,e) = OK 40 --reduce e
      comp (Const x) (Pconst y,e) 
        | x == y = OK 41 --reduce e
        | otherwise = PMatchFail
      comp _ _ = PMatchFail



