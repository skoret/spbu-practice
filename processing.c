//
// Created by sergey on 04.05.18.
//

#include <stdio.h>
#include <malloc.h>
#include "structMatrix.h"

matrix* read_input(FILE* input){
    int rows;
    int cols;
    fscanf(input, "%d", &rows);
    fscanf(input, "%d", &cols);

    matrix *matr = (matrix*)malloc(sizeof(matrix));
    matr->row = rows;
    matr->col = cols;

    long double **values = (long double**)malloc(sizeof(long double*)*matr->row);
    long double cur;
    for (int i = 0; i < matr->row; i++){
        values[i] = (long double*)malloc(sizeof(long double)*(matr->col + 1));
        for (int j = 0; j < matr->col + 1; j++){
            fscanf(input, "%Lf", &cur);
            values[i][j] = cur;
        }
    }
    matr->table = values;

    return matr;
}
