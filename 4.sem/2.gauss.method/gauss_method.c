#include <stdio.h>
#include <stdlib.h>
#include "matrix.h"
#include "operations.h"

void gauss_method(matrix *m, long double *x) {

    // forward run
    for (int i = 0; i < m->col; i++) {

        int max_coef_idx = argmax(m, i, i); // find row with max leading coefficient
        swap(m, i, max_coef_idx, 'r'); // and place it on first place

        for (int j = i + 1; j < m->row; j++) {

            long double coef = m->table[j][i] / m->table[i][i]; // calculate leading coefficient
            add(m, j, i, -coef, 'r'); // substract current row from every row below

        }
 
    }

    // check last row
    if (m->table[m->row - 1][m->col]) {
        int flag = 1;
        for (int i = 0; i < m->col; i++) {
            if (m->table[m->row - 1][i]) {
                flag = 0;
            }
        }
        if (flag) {
            printf("There is no solutions.\n");
            return;
        }
    }

    // backward run
    for (int i = m->row - 1; i >= 0; i--) {
        
        x[i] = m->table[i][m->col] / m->table[i][i]; // first step of calculating
                                                    // current variables
        for (int j = m->row - 1; j > i; j--) {

            x[i] = x[i] - m->table[i][j] * x[j] / m->table[i][i]; // substract every calculated
                                                                 // variable with coefficient
                                                                // from answer for current row        
        }

    }

}