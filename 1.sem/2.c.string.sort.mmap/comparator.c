#include "comparator.h"

int comparator(char* a, char* b)
{
	int i = 0;
	while((a[i] != '\n')&&(b[i] != '\n'))
	{
		if (a[i] > b[i])
		{
			return 1;
		}
		else
		{
			if (a[i] < b[i]) 
			{
				return 0;
			}
		}
		i++;
	}
	if (a[i] == '\n')
	{
		return 0;
	}
	return 1;
}
