#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void other()
{
    printf("Ups, it`s function 'other'!\n");
    return;
}

void input(char* argc)
{
    char buff[4];
    strcpy(buff, argc);
    return;
}

int main(int argv, char** argc)
{
    printf("It's address other: %p\n", other);
    if (argv != 2)
    {
        printf("Please, try one more time.\n");
        return 0;
    }
    input(argc[1]);
    printf("Look, you've write string: %s.\n", argc[1]);
    return 0;
}
