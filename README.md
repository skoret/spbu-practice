# Sort_strings. Results by GProf.
  **Quick sort:**

  % time  |cumulative seconds  | self seconds   | calls  |self s/call  |total s/call | name  
 ------|---------|----------|--------|---------|---------|-------
 55.65  |    2.62 |    2.62 | 11909043 | 0.00   | 0.00 | **comparator**
 39.77   |   4.48  |   1.87 |          |        |       | **main**
  2.99    |  4.62   |  0.14 |       1  |  0.14  |  2.77 | **quick_sort**
  0.21    |  4.63    | 0.01 | 2189125  |  0.00  |  0.00 | **swap**

*Call graph (explanation follows)
granularity: each sample hit covers 2 byte(s) for 0.22% of 4.63 seconds*

index |% time |  self |children |  called |   name
------|-------|-------|---------|---------|----------
      |       |       |         |         |       *spontaneous*
[1]   |100.0  | 1.87  | 2.77    |         |  **main [1]**
      |       | 0.14  | 2.63    |  1/1    |      *quick_sort [2]*
___|___|___|___|___|___
      |       |       |         |418936   |      *quick_sort [2]*
      |       | 0.14  | 2.63    |  1/1    |      *main [1]*
[2]   | 59.7  | 0.14  | 2.63    | 1+418936|  **quick_sort [2]**
      |       | 2.62  | 0.00 |11909043/11909043 |   comparator [3]
      |       |   0.01|    0.00| 2189125/2189125     |*swap [4]*
  |         |        |      | 418936             |*quick_sort [2]*
___|___|___|___|___|___
  |          |  2.62   | 0.00| 11909043/11909043   |  quick_sort [2]
[3] |   56.4 |   2.62  |  0.00 | 11909043  |     **comparator [3]**
___|___|___|___|___|___
    |       |     0.01 |   0.00 |2189125/2189125 |    *quick_sort [2]*
[4]  |    0.2  |  0.01  |  0.00 |2189125       |  **swap [4]**

**Merge sort**

 % time  |cumulative seconds  | self seconds   | calls  |self s/call  |total s/call | name  
 ------|---------|----------|--------|---------|---------|-------  
 68.85 |    1.97 |   1.97   |        |          |       |**main**
 24.88 |    2.68 |   0.71   |8486494  |   0.00 |    0.00 | **comparator**
  3.50 |    2.78 |   0.10   |470545   |  0.00 |    0.00  |**merge**
  0.35 |    2.79 |   0.01   |    1    |10.02  | 821.76  |**merge_sort_recursively**
  0.00 |    2.79 |   0.00   |119278    | 0.00 |    0.00  |**copy_array**
  0.00 |    2.79 |   0.00   |104482    | 0.00 |    0.00  |**swap**
  0.00 |    2.79 |   0.00   |    1     |0.00 |  821.76  |**merge_sort**

*Call graph (explanation follows)
granularity: each sample hit covers 2 byte(s) for 0.36% of 2.79 seconds*

index |% time |  self |children |  called |   name
------|-------|-------|---------|---------|----------
      |       |       |         |         |    spontaneous
[1]  |  100.0 |  1.97 |  0.82   |         |   main [1]
     |        | 0.00  | 0.82    |  1/1    |      merge_sort [2]
___|___|___|___|___|___
     |        | 0.00  | 0.82    |  1/1    |    main [1]
[2]  |  29.4  | 0.00  | 0.82    |  1      |  merge_sort [2]
     |        | 0.01  | 0.81    |  1/1    |      merge_sort_recursively [3]
___|___|___|___|___|___
     |        |       |     |  941090     |        merge_sort_recursively [3]
     |        | 0.01  | 0.81  |    1/1    |      merge_sort [2]
[3]  |  29.4  | 0.01  | 0.81  |    1+941090 | merge_sort_recursively [3]
     |        | 0.10  | 0.69  | 470545/470545   |  merge [4]
     |        | 0.02  | 0.00  |208402/8486494  |  comparator [5]
     |        | 0.00  | 0.00  |119278/119278    | copy_array [6]
     |        | 0.00  | 0.00 |104482/104482  |    swap [7]
     |         |         |      | 941090          |   merge_sort_recursively [3]
___|___|___|___|___|___
    |    |      0.10 |  0.69 | 470545/470545 |    merge_sort_recursively [3]
[4] |   28.5  | 0.10 |  0.69 | 470545       | merge [4]
    |      |    0.69 |  0.00 | 8278092/8486494 |   comparator [5]
___|___|___|___|___|___
     |        |   0.02   | 0.00|  208402/8486494   | merge_sort_recursively [3]
     |        |   0.69   | 0.00| 8278092/8486494   | merge [4]
[5]  |   25.5 |   0.71   | 0.00| 8486494       | comparator [5]
___|___|___|___|___|___
   |       |      0.00  |  0.00 | 119278/119278   |   merge_sort_recursively [3]
[6]    |  0.0 |   0.00  |  0.00 | 119278   |      copy_array [6]
___|___|___|___|___|___
        |       | 0.00 |   0.00 | 104482/104482  |    merge_sort_recursively [3]
[7] |     0.0  |  0.00 |   0.00 | 104482   |      swap [7]

