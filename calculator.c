#include <stdio.h>
#include <stdlib.h>


#include "long_numbers.h"
#include "long_arithmetic.h"
#include "struct.h"



int main()
{
	node_stack *head_stack = NULL;
	char c;
	while (1)
	{
		c = getchar();
		switch (c)
		{
			case '+':
				if (head_stack && head_stack->next)
				{
					if (head_stack->sign == head_stack->next->sign)
					{
						sum(&head_stack);  // (a,b>0) -> sum(a,b)>0 
					}			  // or (a,b<0) -> sum(a,b)<0
					else
					{
						diff(&head_stack); // sign res(a-b) = sign max_mod(a,b)
					}						
				}
				else
				{
					printf("there aren't enough numbers for this operation.\n");
				}
				break;
			case '*':
				break;
			case '/':
				break;
			case '-':
				if ((c = getchar()) != '\n')
				{
					number_read(&head_stack, c, '1');
					break;
				}
				if (head_stack && head_stack->next)
				{
					if (head_stack->sign != head_stack->next->sign)
					{
						sum(&head_stack);  // (a>0, b>0) -> a-(-b)=a+b>0 like a
					}			  // (a>0, b>0) -> -a-b=-(a+b)<0 like -a
					else
					{
						diff(&head_stack);
					}
				}else
				{
					printf("there aren't enough numbers for this operation.\n");
				}
				break;
			case '=':
				printf("intermediate resul: ");
				number_print(head_stack);
				break;
			case 'd':
				number_delete(&head_stack);
				break;
			case 'c':
				if (comparator_mod(head_stack) != -1)
				{
					printf("result compare of moduls: %d\n", comparator_mod(head_stack));
				}
				break;
			case 'q':
				if (head_stack)
				{
					if (head_stack->next)
					{
						printf("there aren't enough operands for final result.\n");
						break;
					}
					number_print(head_stack);
					number_delete(&head_stack);
				}
				return 1;
			default:
				if (c != '\n')
				{
					number_read(&head_stack, c, '0');
					break;
				}
		}
	}
}





