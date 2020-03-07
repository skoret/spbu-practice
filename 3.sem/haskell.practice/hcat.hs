import System.IO
import System.Environment (getArgs)


main :: IO ()
main = do
    files <- getArgs
    if (files /= [])
        then do
            mapM_ (\f -> readFile f >>= putStrLn) files
        else
            loop
            
loop = do
    str <- getLine
    putStrLn str
    loop