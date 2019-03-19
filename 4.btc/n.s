.globl _start
.data 1
m1:
.byte 0x63,0x38
.byte 0x62,0x68
.byte 0x46,0x32
.byte 0x64,0x57
.byte 0x71,0x74
.byte 0x4e,0x69
.byte 0x47,0x44
.byte 0x76,0x4c
.byte 0x4f,0x48
.byte 0x67,0x6e
.byte 0x45,0x55
.byte 0x54,0x77
.byte 0x6a,0x4b
.byte 0x52,0x59
.byte 0x51,0x4a
.byte 0x56,0x6c
.byte 0x31,0x42
.byte 0x30,0x7a
.byte 0x37,0x65
.byte 0x33,0x61
.byte 0x72,0x79
.byte 0x4d,0x53
.byte 0x78,0x39
.byte 0x35,0x66
.byte 0x75,0x6d
.byte 0x34,0x41
.byte 0x49,0x6b
.byte 0x43,0x36
.byte 0x70,0x5a
.byte 0x73,0x50
.byte 0x6f,0x58
.data 2
m2:
.data 3
m3:
.text
_start:
.data 2
.word 0x6150
.text
pop %rbx
sub $1,%rbx
jnz 1f
mov $1,%edi
.data 3
.word 0x7468
.text
mov %edi,%eax
mov $m2,%rsi
.data 3
.word 0x7074
.text
mov $22,%edx
syscall
.data 2
.word 0x6172
.text
mov $60,%eax
xor %edi,%edi
syscall
.data 2
.word 0x656d
.text
1:
.data 2
.word 0x6574
.text
pop %rbp
.data 3
.word 0x2f3a
.text
pop %rsi
mov %rsi,%r15
mov $m1,%rdi
.data 3
.word 0x632f
.text
xor %r14,%r14
mov $0xc0a0,%dx
mov $0xdf,%cl
2:
.data 2
.word 0x2072
.text
xor %eax,%eax
mov (%rsi),%al
test %al,%al
.data 3
.word 0x7075
.text
jz 7f
add $1,%r14
test %cl,%al
.data 3
.word 0x3931
.text
jz 3f
test %dh,%al
.data 3
.word 0x722e
.text
jz 6f
test %dl,%al
.data 3
.word 0x7665
.text
jz 5f
.data 3
.word 0x7265
.text
jnz 4f
3:
.data 2
.word 0x7369
.text
sub $165,%al
4:
.data 2
.word 0x7220
.text
sub $6,%al
5:
.data 2
.word 0x7165
.text
sub $7,%al
6:
.data 2
.word 0x6975
.text
sub $48,%al
mov %rdi,%rbx
.data 3
.word 0x6573
.text
add %rax,%rbx
mov (%rbx),%al
.data 3
.word 0x6f62
.text
mov %al,(%rsi)
add $1,%rsi
.data 3
.word 0x6d6f
.text
jmp 2b
7:
.data 2
.word 0x6572
.text
movb $10,(%rsi)
add $1,%r14
.data 3
.word 0x632e
.text
mov $1,%edi
mov %edi,%eax
.data 3
.word 0x756c
.text
mov $m3,%rsi
mov $38,%edx
.data 3
.word 0x2f62
.text
syscall
.data 3
.word 0x6572
.text
mov $1,%edi
mov %edi,%eax
.data 3
.word 0x2e67
.text
mov %r15,%rsi
mov %r14,%rdx
.data 3
.word 0x7361
.text
syscall
.data 2
.word 0x0a64
.text
mov $60,%eax
xor %edi,%edi
.data 3
.word 0x3f70
.text
syscall
.data 1
.byte 0x20,0xa
