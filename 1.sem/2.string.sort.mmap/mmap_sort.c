#include <stdio.h>
#include <stdlib.h>
#include <sys/mman.h>
#include <sys/stat.h>
#include <fcntl.h>

#include "swap.h"
#include "comparator.h"
#include "bubble_sort.h"
#include "insertion_sort.h"
#include "quick_sort.h"
#include "merge_sort.h"
#include "print_strings.h"

int main(int argc, char **argv)
{
	if (argc != 3)
	{
    		fprintf(stdout, "Unexpected number of arguments\n");
    		exit(1);
	}	
	int quantity_strings = atoi(argv[1]);
	int fdin;
	struct stat buff;
	if ((fdin = open(argv[2], O_RDONLY, 0)) < 0)
	{
		fprintf(stdout, "Error, open file failed.\n");
    		exit(1);
	}
	if ((fstat(fdin, &buff) < 0))
	{
		fprintf(stdout, "Error, fstat file failed.\n");
    		exit(1);
	}
	if (!buff.st_size)
	{
		fprintf(stdout, "This file is empty.\n");
    		return 0;
	}	
	char *file = (char*)mmap(0, buff.st_size, PROT_READ | PROT_WRITE, MAP_PRIVATE, fdin, 0);
	if(file == MAP_FAILED)
	{
		fprintf(stdout, "Error, mmap file failed.\n");
    		exit(1);
	}
	file[buff.st_size-1] = '\n';
	char** string = (char**)malloc(sizeof(char*) * quantity_strings);
	int i = 1;
	int j = 0;
	string[0] = file; //Pointer on first string.
	while ((i != quantity_strings) && (j != buff.st_size))
	{
		if (file[j++] == '\n') //Pointers on other strings.
		{
			string[i++] = &(file[j]);
		}	
	}
	quantity_strings = i - 1;
	fprintf(stdout, "Quantity of strings: %d\n", quantity_strings);	
	fprintf(stdout, "Choose a kind of sort:\nBubble sort -> 1\nInsertion sort -> 2\nQuick sort -> 3\nMerge sort -> 4\n  Your choose: ");
	if (!fscanf(stdin, "%d", &j))
	{
		fprintf(stdout, "Error reading.\n");
		exit(1);
	}		
	switch(j)
	{
		case 1:
			bubble_sort(string, quantity_strings);
			break;
		case 2:
			insertion_sort(string, quantity_strings);
			break;
		case 3:
			quick_sort(string, quantity_strings-1);
			break;
		case 4: 
			merge_sort(string, quantity_strings);
			break;
		default:
			quick_sort(string, quantity_strings-1);
			break;
	}
	//print_strings(string, quantity_strings, buff.st_size);
	fprintf(stdout, "End of sort.\n");
	free(string);
	return 0;
}
