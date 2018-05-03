#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"


double* gauss_method(matrix *m) {

    int cur_row, cur_col;

    // 
    for (int i = 0; i < m->col; i++) {

        for (int j = i+1; j < m->row; j++) {
        
            double coef = m->table[j][i] / m->table[i][i];
            add(m, j, i, -coef, 'r');            
        
        }
    }

    double x[m->row];
    for (int i = m->row-1; i >= 0; i--) {
        
        x[i] = m->table[i][m->col-1] / m->table[i][i];

        for (int j = m->row-1; j > i; j--) {

            x[i] = x[i] - m->table[i][j] * x[j] / m->table[i][i];
        
        }
    }

    return x;

}