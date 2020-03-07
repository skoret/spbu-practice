#include <stdio.h>

#include "print_strings.h"

void print_strings(char** string, int quantity_strings, int file_size)
{
	int i = 0;
	int j = 0;
	while ((j < quantity_strings) && (i < file_size - 1))
		{
			while (string[j][i] != '\n')
			{
				fprintf(stdout, "%c", string[j][i++]);
			}
			j++;
			i = 0;
			fprintf(stdout, "\n");
		}
}
