#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "h_swap.h"
#include "h_comparator.h"
#include "h_bubble_sort.h"
#include "h_insertion_sort.h"
#include "h_quick_sort.h"
#include "h_merge_sort.h"



int main(int argc, char **argv)
{
	if (argc != 3)
	{
    		fprintf(stdout, "Unexpected number of arguments\n");
    		exit(1);
	}	
	int n = atoi(argv[1]);			// Прочитали число n, тогда у нас не больше n строк.
	FILE *f_in = fopen(argv[2], "r");                    //Открыли файл для чтения.
	if (f_in == 0) 
	{
		fprintf(stdout, "Error opening input file\n");
		exit(1);
	}                           
	int *len_str = (int*)malloc(sizeof(int) * n);     // Объявили массив для длин строк.
	int i;
	for (i = 0; i != n; i++)
	{
		len_str[i] = 0;                           // Инициализировали его нулями.
	}
	i = 0;
	char c;
	do // Посчитали длинну каждой строки.
	{
		c = fgetc(f_in);
		if (c != EOF)
		{
			if ((c != '\n')) 
			{
				len_str[i]++;
			}
			else
			{
				i++;
			}
		}
	} while ((c != EOF) && (i != n));
	int j;
	fprintf(stdout, "Quantity of strings: %d\n", i);
	char** a = (char**)malloc(sizeof(char*) * i);       // Выделили память для массива указателей на указатели.
	rewind(f_in);                                       //Сдвинули каретку в начало файла.	
	for(j = 0; j != i; j++)  
	{
		a[j] = (char*)malloc(sizeof(char)*(len_str[j]+1));        // Веделили память для каждой строки.
		int k = 0;
		while (((c = fgetc(f_in)) != '\n')&&(c != EOF))
		{
			a[j][k++] = c;
		}
		a[j][k] = '\0';
	}
	fclose(f_in);
	free(len_str);	
	
	fprintf(stdout, "Choose a kind of sort:\nBubble sort -> 1\nInsertion sort -> 2\nQuick sort -> 3\nMerge sort -> 4\n  Your choose: ");
	if (!fscanf(stdin, "%d", &j))
	{
		fprintf(stdout, "Error reading.\n");
		exit(1);
	}		
	switch(j)
	{
		case 1:
			bubble_sort(a, i);
			break;
		case 2:
			insertion_sort(a, i);
			break;
		case 3:
			quick_sort(a, i-1);
			break;
		case 4: 
			merge_sort(a, i);
			break;
		default:
			quick_sort(a, i-1);
			break;
	}
	
	for (j = 0; j != i; j++)
	{
		fputs(a[j], stdout);
		fputc('\n',stdout);	 //Вывели отсортированный массив строк на экран.
		free(a[j]);
	}
	free(a);
	return 0;
}
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
 
