#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "h_swap.h"
#include "h_comparator.h"
#include "h_bubble_sort.h"
#include "h_insertion_sort.h"
#include "h_quick_sort.h"
#include "h_merge_sort.h"



int main()
 {
	int n, i;
	FILE *f_in = fopen("in.txt", "r");                    //Открыли файл для чтения.
	if (f_in == 0) 
	{
		printf("Error opening input file\n");
		exit(1);
	}
	fscanf(f_in, "%d\n", &n);                           // Прочитали число n, тогда у нас не больше n строк.
	int *len_str = (int*)malloc(sizeof(int) * n);     // Объявили массив для длин строк.
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
	} while (c != EOF);
	int j;
	printf("Quantity of strings: %d\n", i);
	char** a = (char**)malloc(sizeof(char*) * i);       // Выделили память для массива указателей на указатели.
	rewind(f_in);                                       //Сдвинули каретку в начало файла.
	fscanf(f_in, "%d\n", &n); 	// Пропустили первую строку.
	
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
	
	printf("Choose a kind of sort:\nBubble sort -> 1\nInsertion sort -> 2\nQuick sort -> 3\nMerge sort -> 4\n  Your choose: ");
	scanf("%d", &j);
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
	
	FILE *f_out = fopen("out.txt", "w");
	for (j = 0; j != i; j++)
	{
	 //f(f_out, '%d:', j+1);
	 fputs(a[j], f_out);
	 fputc('\n',f_out);	 //Вывели отсортированный массив строк в файл и на экран.
	 printf("%s\n",a[j]);
	 free(a[j]);
	}
	fclose(f_out);
	free(a);
	return 0;
 }
