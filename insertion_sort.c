#include "insertion_sort.h"
#include "swap.h"
#include "comparator.h"

void insertion_sort(char** array, int n)
{
	int j;
	for(int i = 1; i != n; i++)
	{
		j = i;
		while((j>0) && comparator(array[j-1], array[j]))
		{
			swap(&array[j-1],&array[j]);
			j--;
		}
	}
}
