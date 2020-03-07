#include "quick_sort.h"
#include "swap.h"
#include "comparator.h"

void quick_sort(char **array,  int n)
{
	 int l = 0, r = n;
	 char* p;
	 p = array[0];
	 while(l <= r)
	 {
		 while(comparator(p, array[l]))
			 l++;
		 while(comparator(array[r], p))
			 r--;
		 if (l <= r)
		 {
			 swap(&array[l++], &array[r--]);
		 }
	 }
	 if (r > 0)
		 quick_sort(array, r);
	 if (n > l)
		 quick_sort(array + l, n-l);
}
