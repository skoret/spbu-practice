/* File c_merge_sort.c */

#include <stdlib.h>
#include "merge_sort.h"
#include "swap.h"
#include "comparator.h"

void copy_array(char** a, int n, char** b)
 {
 	for (int i = 0; i != n; i++)
 	{
 		b[i] = a[i]; 	
	}
 }
 
void merge(char** a, int fir, char** b, int sec, char** c)
 { 
 	int l = 0;
	int r = 0;
	int i = 0;
	while((l < fir) && (r < sec))
	{
		if (comparator(a[l],b[r]))
		{
			c[i] = b[r++];
		}
		else
		{
			c[i] = a[l++];
		}
		i++;
	}
	while(l < fir)
	{
		c[i++] = a[l++];
	}
	while(r < sec)
	{
		c[i++] = b[r++];
	}	
 }

void merge_sort_recursively(char** a, char** b, int  n , int *pos)
 {
	*pos = 0;
	if (n < 2)
	{
		return;
	}
	if (n == 2)
	{
		if (comparator(a[0], a[1]))
		{
			swap(&a[0], &a[1]);	
		}
	}
	
	int middle = n / 2;
	int res_a, res_b;
	
	merge_sort_recursively(a, b, middle, &res_a);
	merge_sort_recursively(a + middle, b + middle, n - middle, &res_b);
	
	if (res_a != res_b)
	{
		if (res_a == 0)
	 	{
	 		copy_array(b + middle, n - middle, a + middle);
	 	}
	 	else
	 	{
	 		copy_array(a + middle, n - middle, b + middle);
	 	}
	}
	if (res_a == 0)  
	{
		merge(a, middle, a+middle, n-middle, b);
		*pos = 1;
	}
	else
	{
	 	merge(b, middle, b+middle, n-middle, a);
		*pos = 0;
	}
 }
 
void merge_sort(char** a, int n)
 {
	if (n <= 1) return;
	if (n == 2) 
	{
		if (comparator(a[0],a[1]))
		{
			swap(&a[0],&a[1]);
		}
		return;
	}
	int where_m = 0;//  0== in massive a
	char** b = (char**)malloc(sizeof(char*)*n);
	merge_sort_recursively(a, b, n, &where_m);
	if (where_m!=0)
	{
		copy_array(b, n, a);
	}
	free(b);
 }
