## Tash #4: Bitcoin :moneybag:

    1. Receive email with `noname` file:
> 5J6CkuTkeQ611csdhSYdG3ds3fQyiKDPFzcfuVmjZXTV8Zh8p6o
> 5J5m7axWauecpCNn44snWLUiMZntBcUF5VHgFx36v5cp77ZwfRJ
> 5J6CkuTkdnnQRxmjx4QqF67cnpm2w9CuCA6PCje7YeVQ2fyFZv8
> 5J6CfD3gkauHtrLZDjr6Sk1ikaYpwPDEektqdB3HCdsmmy9rXnr

    2. Each of 500 lines is WIF private key with format base58.encode("80" + large_random_hex + checksum)
    3. Decode each line from base58, remove first 2 and last 8 bytes:
> 242420206f60232424236f23242024232e676c6f626c235f7374617274202023
> 232320246f38383838383824242323232e646174612031202323202323232023
> 242420206f38383838383820232024246d313a23232023202320232320232023
> 242324206f38383838383820242024202e6279746523307836332c3078333823

    3. Get ascii symbols from this hex-lines
> \$\$  o\`\#\$\$\#o\#\$ \$\#.globl\#_start  \#  
> \#\# \$o888888\$\$\#\#\#.data 1 \#\# \#\#\# \#  
> \$\$  o888888 \# \$\$m1:\#\# \# \# \#\# \# \#   
> \$\#\$ o888888 \$ \$ .byte\#0x63,0x38\#    

    1. Get right half of this text, replace '#' with 'space' and strip each line:
```s
.globl _start
.data 1
m1:
.byte 0x63,0x38
...
```
    5. Save this code in file `n.s` and compile:
```sh
$ gcc -c n.s -o n.o
$ ld noname.o -o n
```

    6. Run this programm:
```sh
$ ./n
Parameter is required
$ ./n 0123456789ABCDEFGHIJKLMNOPQASTUVWXYZabcdefghijklmnopqrstuvwxyz
http://cup19.reverseboom.club/reg.asp?c8bhF2dWqtNiGDvLOHgnEUTwjKRNQJVl1B0z7e3aryMSx95fum4AIkC6pZsPoX 
```
    7. This script replaces each symbol with 1-to-1 map from memory. Its order is defined by `.byte 0x.. 0x..` directives
    8. Get left half of text from WIF-keys, replace '#' and '$' with 'space' in each line:
```
       d88`     
      .888V     
      o8888     
      o'  b     
      o   o     
      o`  d     
      o888888"  
       88888P   
      o88888'   
      o    .    
...
```
    8. Rewrite this ascii-text and mirror each symbol.
    9. Send this string with 20 sybmols to programm and get link