#include <stdio.h>
#include <stdlib.h>
#include <string.h>
typedef char* point;

void swap(point *a, point *b)
 {
	 point tmp = *a;
	 *a = *b;
	 *b = tmp;
 }

int comparator(point a, point b)
 {
	int i = 0;
	while((a[i] != '\0')&&(b[i] != '\0'))
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
	if (a[i] == '\0')
	{
		return 0;
	}
	return 1;
 }
 
void quick_sort(point *array, int n)
 {
	 int l = 0, r = n;
	 point p;
	 p = array[n / 2];
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

void bubble_sort(point *array, int n)
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
 
void insertion_sort(point *array, int n)
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
 


int main()
 {
	int n, i;
	FILE *f_in = fopen("in.txt", "r");                    //Открыли файл для чтения.
	fscanf(f_in, "%d\n", &n);                           // Прочитали число n, тогда у нас не больше n строк.
	int *len_str = (int*)malloc(sizeof(int) * n);     // Объявили массив для длин строк.
	for (i = 0; i != n; i++)
	{
		len_str[i] = 0;                           // Инициализировали его нулями.
	}
	i = 0;
	int c;
	do // Посчитали длинну каждой строки.
	{
		c = fgetc(f_in);
		if ((c != '\n')&&(c != EOF)) 
		{
			len_str[i]++;
		}
		else
		{
			i++;
		}
	} while (c != EOF);
	int j;	
	point *a = (point *)malloc(sizeof(point) * i);       // Выделили память для массива указателей на указатели.
	rewind(f_in);                                       //Сдвинули каретку в начало файла.
	fscanf(f_in, "%d\n", &n); 	// Пропустили первую строку.
	
	for(j = 0; j != i; j++)  
	{
		a[j] = (point)malloc(sizeof(char)*(len_str[j]+1));        // Веделили память для каждой строки.
		int k = 0;
		while (((c = fgetc(f_in)) != '\n')&&(c != EOF))
		{
			a[j][k++] = c;
		}
		a[j][k] = '\0';
	}
	fclose(f_in);	
	
	//bubble_sort(a, i);
	//insertion_sort(a, i);
	//quick_sort(a,i-1);
	
	printf("Choose a kind of sort:\nBubble sort -> 1\nInsertion sort -> 2\nQuick sort -> 3\n  Your choose: ");
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
	}
	fclose(f_out);
	return 0;
 }