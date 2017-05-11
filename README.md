# Trees
## Performance analysis
----------
#### Random keys
| Nodes | Binary search tree | Red-Black Tree | B-Tree |
|:---:|:---|:---|:---|
| 100 | - insert: 5  <br> - search one: 0 <br> - search all: 1 | - insert: 9 <br> - search one: 0 <br> - search all: 0 | - insert: 7 <br> - search one: 0 <br> - search all: 1 |
| 1k | - insert: 36 <br> - search one: 0 <br> - search all: 2 | - insert: 47 <br> - search one: 0 <br> - search all: 2 | - insert: 46 <br> - search one: 0 <br> - search all: 4 |
| 10k | - insert: 61 <br> - search one: 0 <br> - search all: 17 | - insert: 90 <br> - search one: 0 <br> - search all: 14 | - insert: 114 <br> - search one: 0 <br> - search all: 42 |
| 100k | - insert: 200 <br> - search one: 0 <br> - search all: 140 | - insert: 698 <br> - search one: 0 <br> - search all: 93 | - insert: 910 <br> - search one: 0 <br> - search all: 167 |
| 1m | - insert: 1.993 <br> - search one: 0 <br> - search all: 1.423 | - insert: 4.689 <br> - search one: 0 <br> - search all: 1.420 | - insert: 4.787 <br> - search one: 0 <br> - search all: 2.649 |
| 5m | - insert: 14.475 <br> - search one: 0 <br> - search all: 26.030 | - insert: 24.261 <br> - search one: 0 <br> - search all: 9.368 | - insert: 23.924 <br> - search one: 0 <br> - search all: 18.828 |
---------
#### Ordered keys
| Nodes | Binary search tree | Red-Black Tree | B-Tree |
|:---:|:---|:---|:---|
| 100 | - insert: 6  <br> - search one: 2 <br> - search all: 9 | - insert: 22 <br> - search one: 0 <br> - search all: 4 | - insert: 18 <br> - search one: 0 <br> - search all: 4 |
| 1k | - insert: 49 <br> - search one: 0 <br> - search all: 36 | - insert: 28 <br> - search one: 0 <br> - search all: 22 | - insert: 37 <br> - search one: 0 <br> - search all: 6 |
| 10k | - insert: 1.439 <br> - search one: 0 <br> - search all: 507 | - insert: 72 <br> - search one: 0 <br> - search all: 24 | - insert: 76 <br> - search one: 0 <br> - search all: 54 |
| 100k | - insert: -- <br> - search one: -- <br> - search all: -- | - insert: 379 <br> - search one: 0 <br> - search all: 97 | - insert: 345 <br> - search one: 0 <br> - search all: 221 |
| 1m | - insert: -- <br> - search one: -- <br> - search all: -- | - insert: 2.711 <br> - search one: 0 <br> - search all: 211 | - insert: 2.783 <br> - search one: 0 <br> - search all: 791 |
| 5m | - insert: -- <br> - search one: -- <br> - search all: -- | - insert: 12.946 <br> - search one: 0 <br> - search all: 1.069 | - insert: 11.121 <br> - search one: 0 <br> - search all: 9.327 |
---------
