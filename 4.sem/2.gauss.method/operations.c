#include <stdio.h>
#include <math.h>
#include "matrix.h"
#include "operations.h"


void print_row(matrix *m, int row)
{
  for (int i=0; i < m->col + 1; i++) {
    printf("%Lf ", m->table[row][i]);
  }
  printf("\n");
}

/*
  add row/column to row/column with scalar
*/
void add(matrix *m, int to, int what,long double scalar, char orient)
{
  if (orient == 'r')
  {
    for (int i = 0; i < m->col + 1; i++)
      m->table[to][i] += scalar * m->table[what][i];
      return;
  }
  if (orient == 'c')
  {
    for (int i = 0; i < m->row; i++)
      m->table[i][to] += scalar * m->table[i][what];
      return;
  }
  printf("Try again. Wrong orient!");

}

/*
  multiplication row/column by scalar
*/
void mul(matrix *m, int what, long double scalar, char orient)
{
  if (orient == 'r')
  {
    for (int i = 0; i < m->col + 1; i++)
      m->table[what][i] += scalar * m->table[what][i];
      return;
  }
  if (orient == 'c')
  {
    for (int i = 0; i < m->row; i++)
      m->table[i][what] += scalar * m->table[i][what];
      return;
  }
  printf("Try again. Wrong orient!");
}

/*
  swap two rows/columns
*/
void swap(matrix *m, int what, int with, char orient)
{
  if (orient == 'r')
  {
    long double *tmp  = m->table[what];
    m->table[what] = m->table[with];
    m->table[with] = tmp;
    return;
  }
  if (orient == 'c')
  {
    long double tmp;
    for (int i = 0; i < m->row;i++)
    {
      tmp = m->table[i][what];
      m->table[i][what] = m->table[i][with];
      m->table[i][with] = tmp;
    }
    return;
  }
  printf("Try again. Wrong orient!");
}

int argmax(matrix *m, int col, int row)
{
  int max = row;
  for (int i = row + 1; i < m->row; i++)
  {
	  if (fabsl(m->table[i][col]) > fabsl(m->table[max][col]))
	  {
		  max = i;
	  }
  }
  return max;
}
