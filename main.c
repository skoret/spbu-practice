#include <stdio.h>
#include <stdlib.h>

int main(int argc, char *argv[]) {
    if (argc != 2){
        printf("Provide exactly 1 arg - filename\n");
    }

    FILE *input;
    if ((input = fopen(argv[1], "r")) == NULL){
        printf("Can't open file.\n");
        exit(1);
    }


    return 0;
}