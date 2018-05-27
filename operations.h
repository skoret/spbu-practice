#pragma once

void print_row(matrix *m, int row);
void add(matrix *m, int to, int what,long double scalar, char orient);
void mul(matrix *m, int what,long double scalar, char orient);
void swap(matrix *m, int what, int with, char orient);
int argmax(matrix *m, int col, int row);