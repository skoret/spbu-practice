module PMEval where

import PMParser

-- implement this
eval what cases = OK 42

data EvalRez =
    OK Int           -- success
  | BadProgram       -- adding integers to functions, getting tag of integer,
                     -- unbound variables, etc.
  | PMatchFail       -- patterns are not exhaustive.
  deriving (Show,Eq)

optsE = undefined
optsP = undefined
