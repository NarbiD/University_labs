main = print "hello"

-- lab 1
task'3 :: Ord a => [a] -> [a]
task'3 lst = helper [] [] [] (minimum lst) (maximum lst) lst where
  helper st med en mn mx [] = st++med++en
  helper st med en mn mx (x:xs) | x == mn = helper (x:st) med en mn mx xs
                               | x == mx = helper st med (x:en) mn mx xs
                               | otherwise = helper st (med++[x]) en mn mx xs

task'53 :: [a] -> ([a],[a])
task'53 lst = helper [] [] lst 0 where
  helper l1 l2 [] count = (l1,l2)
  helper l1 l2 (x:xs) count | count`mod`2==0 = helper (l1++[x]) l2 xs (count+1)
                            | otherwise = helper l1 (l2++[x]) xs (count+1)

-- lab 2
task''3 :: [Int] -> [(Int, Int)]
task''3 lst = helper (zip lst [1..]) [] where
  a >* b = fst a > fst b
  helper [] _ = []
  helper [x] _ = [x]
  helper (x:xs) res = helper' xs x $if x>*(head xs) then res++[x] else res
  helper' [x] prev res = if x>*prev then res++[x] else res
  helper' (x:xs) prev res = helper' xs x $if x>*prev && x>*(head xs) then res++[x] else res
  