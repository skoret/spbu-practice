#include "bubble_sort.h"
#include "swap.h"
#include "comparator.h"

void bubble_sort(char** array, int n)
{
	int i,j;	
	for (i = 0; i < n-1; i++)
	{
	 for (j = 0; j < n-1-i; j++)
	 {
		if (comparator(array[j], array[j+1]))
		{
		 swap(&array[j], &array[j+1]);
		}
	 }
	}
}
