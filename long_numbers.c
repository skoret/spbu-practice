#include <stdio.h>
#include <stdlib.h>

#include "long_numbers.h"
#include "struct.h"



void read_number(node_stack **head, char c, char sign)
{
	node_stack *tmp = (node_stack*)malloc(sizeof(node_stack));
	push_digit_in_head(&(tmp->number), &(tmp->tail), c - '0');
	tmp->length = 1;
	while ((c = getchar()) != '\n')
	{
		push_digit_in_head(&(tmp->number), &(tmp->tail), c - '0');
		tmp->length++;
	}
	tmp->sign = sign - '0';
	tmp->next = *head;
	*head = tmp;
}

void print_number(node_stack *head)
{
	if (head && head->tail)
	{
		node_number *tmp = head->tail;
		if (head->sign)            //check sign number
		{
			printf("%c", '-');
		}
		while (tmp)
		{
			printf("%d", tmp->digit);
			tmp = tmp->prev;
		}
		printf("\n");
		free(tmp);
	}
	else
	{
		printf("------\n");
	}
}

void delete_number(node_stack **head)
{
	if (!(*head))
	{
		printf("Nothing to delete.\n");
		return;
	}
	node_number *del;
	node_stack *op;
	
	while ((*head)->number)
	{
		del = (*head)->number;
		(*head)->number = (*head)->number->next;
		free(del);
	}
	op = (*head);
	*head = (*head)->next;
	free(op);
	op = NULL;
}

void push_digit_in_head(node_number **head, node_number **tail, char dig)
{
	node_number *tmp = (node_number*)malloc(sizeof(node_number));
	tmp->digit = dig;
	tmp->prev = NULL;
	tmp->next = *head;
	if (*head)
	{
		(*head)->prev = tmp;
	}
	else
	{
		*tail = tmp;
	}
	*head = tmp;
}

void push_digit_in_tail(node_number **head, node_number **tail, char dig)
{
	node_number *tmp = (node_number*)malloc(sizeof(node_number));
	tmp->digit = dig;
	tmp->next = NULL;
	tmp->prev = *tail;
	if (*tail)
	{
		(*tail)->next = tmp;
	}
	else
	{
		*head = tmp;
	}
	*tail = tmp;
}

void delete_digit_from_head(node_number **head)
{
	node_number *tmp = *head;
	*head = (*head)->next;
	if (*head)
	{
		(*head)->prev = NULL;
	}
	free(tmp);
}
	
void delete_digit_from_tail(node_number **tail)
{
	node_number *tmp = *tail;
	*tail = (*tail)->prev;
	if (*tail)
	{
		(*tail)->next = NULL;
	}
	free(tmp);
}


	
	
	


















