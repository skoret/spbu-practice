#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"
#include "operations.h"

void gauss_method(matrix *m, long double *x) {

    for (int i = 0; i < m->col; i++) {

        for (int j = i+1; j < m->row; j++) {

            long double coef = m->table[j][i] / m->table[i][i];
            add(m, j, i, -coef, 'r');            

        }
 
    }

    for (int i = m->row-1; i >= 0; i--) {
        
        x[i] = m->table[i][m->col] / m->table[i][i];

        for (int j = m->row-1; j > i; j--) {

            x[i] = x[i] - m->table[i][j] * x[j] / m->table[i][i];
        
        }

    }

}