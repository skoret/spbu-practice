#include <stdio.h>
#include <stdlib.h>
#include "structMatrix.h"
#include "processing.h"

int main(int argc, char *argv[]) {
    if (argc != 2){
        printf("Provide exactly 1 arg - filename\n");
    }

    FILE *input;
    if ((input = fopen(argv[1], "r")) == NULL){
        printf("Can't open file.\n");
        exit(1);
    }


    matrix* matr = read_input(input);

    printf("%dx%d\n", matr->row, matr->col);
    for (int i=0; i<matr->col; i++){
        for (int j = 0; j <matr->row + 1; j ++){
            printf("%Lf ", matr->table[i][j]);
        }
        printf("\n");
    }

    return 0;
}