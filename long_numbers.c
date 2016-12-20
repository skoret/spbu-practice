#include <stdio.h>
#include <stdlib.h>

#include "long_numbers.h"
#include "struct.h"

node_stack* number_create()
{
	node_stack *tmp = (node_stack*)malloc(sizeof(node_stack));
	tmp->number = NULL;
	tmp->tail = NULL;
	tmp->next = NULL;
	tmp->sign = 0;
	tmp->length = 0;
	return tmp;
}

void number_read(node_stack **head, char c, char sign)
{
	node_stack *tmp = number_create();
	digit_push_in_head(tmp, c - '0');
	while ((c = getchar()) != '\n')
	{
		digit_push_in_head(tmp, c - '0');
	}
	tmp->sign = sign - '0';
	tmp->next = *head;
	*head = tmp;
}

void number_print(node_stack *head)
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
		printf("\n //number_length: %ld\n", head->length);
	}
	else
	{
		printf("------|empty stack\n");
	}
}

node_stack* number_copy(node_stack *head)
{
	node_stack *tmp = number_create();
	tmp->sign = head->sign;
	node_number *num = head->number;
	while (num)
	{
		digit_push_in_tail(tmp, num->digit);
		num = num->next;
	}
	return tmp;
}

void number_delete(node_stack **head)
{
	if (!(*head))
	{
		printf("nothing to delete|empty stack.\n");
		return;
	}
	while ((*head)->number)
	{
		digit_delete_from_head(*head);
	}
	node_stack *op = (*head);
	*head = (*head)->next;
	free(op);
	op = NULL;
}

void digit_push_in_head(node_stack *head, char dig)
{
	node_number *tmp = (node_number*)malloc(sizeof(node_number));
	tmp->digit = dig;
	tmp->prev = NULL;
	tmp->next = head->number;
	if (head->number)
	{
		head->number->prev = tmp;
	}
	else
	{
		head->tail = tmp;
	}
	head->number = tmp;
	head->length++;
}

void digit_push_in_tail(node_stack *head, char dig)
{
	node_number *tmp = (node_number*)malloc(sizeof(node_number));
	tmp->digit = dig;
	tmp->next = NULL;
	tmp->prev = head->tail;
	if (head->tail)
	{
		head->tail->next = tmp;
	}
	else
	{
		head->number = tmp;
	}
	head->tail = tmp;
	head->length++;
}

void digit_delete_from_head(node_stack *head)
{
	node_number *tmp = head->number;
	head->number = head->number->next;
	if (head->number)
	{
		head->number->prev = NULL;
	}
	else
	{
		head->tail = NULL;
	}
	free(tmp);
	tmp = NULL;
	head->length--;
}

void digit_delete_from_tail(node_stack *head)
{
	node_number *tmp = head->tail;
	head->tail = head->tail->prev;
	if (head->tail)
	{
		head->tail->next = NULL;
	}
	else
	{
		head->number = NULL;
	}
	free(tmp);
	tmp = NULL;
	head->length--;
}
