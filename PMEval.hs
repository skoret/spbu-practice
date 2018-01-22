module PMEval where

import PMParser

data EvalRez =
    OK Int           -- success
  | BadProgram       -- adding integers to functions, getting tag of integer, etc.
  | PMatchFail       -- patterns are not exhaustive.
  deriving (Show,Eq)

optsE = undefined
optsP = undefined

-- implement this
eval what cases = OK 42
