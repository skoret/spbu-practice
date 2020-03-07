# Sort_strings_with_mmap

File with 470546 strings.

 **Quick sort:**

Each sample counts as 0.01 seconds.

   time | cumulative seconds | self seconds | calls | self ns/call | total ns/call | name 
------ | ------ | ------ | ------ | ------ | ------ | ------ 
 97.64 |  1.37 |  1.37 |  13685648 |  99.89 |  99.89 |  comparator 
 1.44 |  1.39 |  0.02 |  |  |  |  bubble_sort 
 1.44 |  1.41 |  0.02 |  |  |  |  quick_sort 
 0.00 |  1.41 |  0.00 |  2216685 |  0.00 |  0.00 |  swap 


Call graph

granularity: each sample hit covers 2 byte(s) for 0.71% of 1.41 seconds


 index | % time | self | children | called | name 197747 
------ | ------ | ------ | ------ | ------ | ------ 
 |  |  |  |  |  quick_sort  [1]
 [1] |  98.6 |  0.02 |  1.37 |  0+197747 |  quick_sort  [1]
 |  |  1.37 |  0.00 |  13685648/13685648 |  comparator  [2]
 |  |  0.00 |  0.00 |  2216685/2216685 |  swap  [4]
 |  |  |  |  197747 |  quick_sort  [1]
 |  |  1.37 |  0.00 |  13685648/13685648 |  quick_sort  [1]
 [2] |  97.1 |  1.37 |  0.00 |  13685648 |  comparator  [2]
 [2] |  |  |  |  |  |  <spontaneous>  [3]
 |  |  1.4 |  0.02 |  0.00 |  bubble_sort  [3]
 |  |  0.00 |  0.00 |  2216685/2216685 |  quick_sort  [1]
 [4] |  0.0 |  0.00 |  0.00 |  2216685 |  swap  [4]
 
 
 **Merge sort:**

Each sample counts as 0.01 seconds.

   time | cumulative seconds | self seconds | calls | self ms/call | total ms/call | name 
------ | ------ | ------ | ------ | ------ | ------ | ------ 
 91.65 |  0.62 |  0.62 |  8486494 |  0.00 |  0.00 |  comparator 
 7.39 |  0.67 |  0.05 |  470545 |  0.00 |  0.00 |  merge 
 1.48 |  0.68 |  0.01 |  |  |  |  quick_sort 
 0.00 |  0.68 |  0.00 |  104482 |  0.00 |  0.00 |  swap 
 0.00 |  0.68 |  0.00 |  1 |  0.00 |  673.46 |  merge_sort_recursively 


Call graph

granularity: each sample hit covers 2 byte(s) for 1.46% of 0.68 seconds


 index | % time | self | children | called | name 941090 
------ | ------ | ------ | ------ | ------ | ------ 
 |  |  |  |  |  merge_sort_recursively  [1]
 |  |  0.00 |  0.67 |  1/1 |  merge_sort  [2]
 [1] |  98.5 |  0.00 |  0.67 |  1+941090 |  merge_sort_recursively  [1]
 |  |  0.05 |  0.61 |  470545/470545 |  merge  [3]
 |  |  0.02 |  0.00 |  208402/8486494 |  comparator  [4]
 |  |  0.00 |  0.00 |  104482/104482 |  swap  [6]
 |  |  |  |  941090 |  merge_sort_recursively  [1]
 |  |  |  |  |  <spontaneous>  [2]
 |  |  98.5 |  0.00 |  0.67 |  merge_sort  [2]
 |  |  0.00 |  0.67 |  1/1 |  merge_sort_recursively  [1]
 |  |  0.05 |  0.61 |  470545/470545 |  merge_sort_recursively  [1]
 [3] |  96.3 |  0.05 |  0.61 |  470545 |  merge  [3]
 |  |  0.61 |  0.00 |  8278092/8486494 |  comparator  [4]
 |  |  0.02 |  0.00 |  208402/8486494 |  merge_sort_recursively  [1]
 |  |  0.61 |  0.00 |  8278092/8486494 |  merge  [3]
 [4] |  91.2 |  0.62 |  0.00 |  8486494 |  comparator  [4]
 [4] |  |  |  |  |  |  <spontaneous>  [5]
 |  |  1.5 |  0.01 |  0.00 |  quick_sort  [5]
 |  |  0.00 |  0.00 |  104482/104482 |  merge_sort_recursively  [1]
 [6] |  0.0 |  0.00 |  0.00 |  104482 |  swap  [6]

 