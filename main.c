#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"
#include "processing.h"
#include "gauss_method.h"

int main(int argc, char *argv[]) {
    if (argc != 2){
        printf("Provide exactly 1 arg - filename\n");
        exit(1);
    }

    FILE *input;
    if ((input = fopen(argv[1], "r")) == NULL){
        printf("Can't open file.\n");
        exit(1);
    }


    matrix* matr = read_input(input);

    long double x[matr->col];
    gauss_method(matr, x);

    for (int i=0; i < matr->col; i++){
        printf("%Lf ", x[i]);
    }
    printf("\n");

    return 0;
}