# Sort_strings. Results by GProf.
  **Quick sort:**

 % time | cumulative seconds | self seconds | calls | self s/call | total s/call | name  
:------:|:------------------:|:------------:|:-----:|:-----------:|:------------:|:-----:
 55.65  | 2.62 | 2.62 | 11909043 | 0.00 | 0.00 | **comparator**
 39.77  | 4.48 | 1.87 |          |      |      | **main**
  2.99  | 4.62 | 0.14 |        1 | 0.14 | 2.77 | **quick_sort**
  0.21  | 4.63 | 0.01 |  2189125 | 0.00 | 0.00 | **swap**

**Merge sort**

 % time | cumulative seconds | self seconds | calls | self s/call | total s/call | name  
:------:|:------------------:|:------------:|:-----:|:-----------:|:------------:|:-----:
 68.85 | 1.97 | 1.97 |         |       |        | **main**
 24.88 | 2.68 | 0.71 | 8486494 |  0.00 |   0.00 | **comparator**
  3.50 | 2.78 | 0.10 |  470545 |  0.00 |   0.00 | **merge**
  0.35 | 2.79 | 0.01 |       1 | 10.02 | 821.76 | **merge_sort_recursively**
  0.00 | 2.79 | 0.00 |  119278 |  0.00 |   0.00 | **copy_array**
  0.00 | 2.79 | 0.00 |  104482 |  0.00 |   0.00 | **swap**
  0.00 | 2.79 | 0.00 |       1 |  0.00 | 821.76 | **merge_sort**
