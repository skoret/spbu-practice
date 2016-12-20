#include <stdio.h>
#include <stdlib.h>

#include "long_numbers.h"
#include "long_arithmetic.h"
#include "struct.h"

int comparator_mod(node_stack *head)
{
	if (!head || !head->next)
	{
		printf("nothing to compare|empty stack.\n");
		return -1;
	}
	if (head->length > head->next->length)
	{
		return 0; //if mod(a) > mod(b)
	}
	if (head->length < head->next->length)
	{
		return 1; //if mod(a) < mod(b)
	}
	node_number *a = head->tail;
	node_number *b = head->next->tail;
	while ((a->digit == b->digit) && a->prev)
	{
		a = a->prev;
		b = b->prev;
	}
	if (a->digit > b->digit)
	{
		return 0;
	}
	if (a->digit < b->digit)
	{
		return 1;
	}
	//if mod(a) = mod(b)
	return 2;
}


//addition modules of two numbers, sign res = sign prenult number

void sum(node_stack **head)
{
	node_stack *res = number_create();
	res->sign = (*head)->next->sign;
	node_stack *l = (*head);
	node_stack *r = (*head)->next;
	char div = 0;
	while (l->number && r->number)
	{
		digit_push_in_tail(res, ((r->number->digit + l->number->digit + div) % 10));
		div = (r->number->digit + l->number->digit + div) / 10;
		digit_delete_from_head(l);
		digit_delete_from_head(r);
	}
	while (l->number)
	{
		digit_push_in_tail(res, ((l->number->digit + div) % 10));
		div = (l->number->digit + div) / 10;
		digit_delete_from_head(l);
	}
	while (r->number)
	{
		digit_push_in_tail(res, ((r->number->digit + div) % 10));
		div = (r->number->digit + div) / 10;
		digit_delete_from_head(r);
	}
	if (div)
	{
		digit_push_in_tail(res, div);
	}
	number_delete(head);
	number_delete(head);
	res->next = *head;
	*head = res;
}

void diff(node_stack **head)
{
	if (comparator_mod(*head) == 2) //if a=b then (a-b)=0
	{
		number_delete(head); //del a
		number_delete(head); //del b
		node_stack *tmp = number_create();
		digit_push_in_head(tmp, 0);
		tmp->next = *head;
		*head = tmp;
		return;
	}
	node_number *min = NULL;
	node_number *max = NULL;
	node_stack *res = number_create();
	if (comparator_mod(*head))
	{
		min = (*head)->number;
		max = (*head)->next->number;
		res->sign = (*head)->next->sign;
	}
	else
	{
		min = (*head)->next->number;
		max = (*head)->number;
		if ((*head)->sign != (*head)->next->sign)
		{
			res->sign = (*head)->sign;
		}
		else
		{
			res->sign = ((*head)->sign + 1) % 2;
		}
	}
	char diffrence = 0;
	while (max && min)
	{
		diffrence = max->digit - min->digit;
		if (diffrence < 0)
		{
			diffrence += 10;
			max->next->digit--;
		}
		digit_push_in_tail(res, diffrence);
		max = max->next;
		min = min->next;
	}
	while (max)
	{
		while (max->digit < 0)
		{
			max->digit += 10;
			max->next->digit--;
		}
		digit_push_in_tail(res, max->digit);
		max = max->next;
	}
	while (!res->tail->digit && res->tail->prev)
	{
		digit_delete_from_tail(res);
	}
	number_delete(head);
	number_delete(head); //del min and max
	res->next = *head;
	*head = res;
}

void compos(node_stack **head)
{
	if (!(*head) || !(*head)->next)
	{
		printf("there aren't enough numbers for this operation|empty stack.\n");
		return;
	}
	node_stack *res = number_create();
	res->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;
	if (!(*head)->tail->digit || !(*head)->next->tail->digit) //if (a=0) or (b=0) then a*b=0
	{
		digit_push_in_head(res, 0);
		res->sign = 0;
		number_delete(head);
		number_delete(head);
		res->next = *head;
		*head = res;
		return;
	}
	//if (a=1) or (b=1) then a*b=(a)or(b)
	if (((*head)->tail->digit == 1) && ((*head)->length == 1))
	{
		(*head)->next->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;
		number_delete(head);
		return;
	}
	if (((*head)->next->tail->digit == 1) && ((*head)->next->length == 1))
	{
		(*head)->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;
		number_delete(&(*head)->next);
		return;
	}
	node_number *l = (*head)->number;
	node_number *r = (*head)->next->number;
	long i = (*head)->length + (*head)->next->length;
	while (i--)
	{
		digit_push_in_head(res, 0); // res = 0
	}
	char mod = 0;
	char div = 0;
	node_number *tmp_start = res->number;
	node_number *tmp = res->number;
	while (l)
	{
		while (r)
		{
			mod = tmp->digit;
			tmp->digit = ((l->digit * r->digit) + div + mod) % 10;
			div = ((l->digit * r->digit) + div + mod) / 10;
			r = r->next;
			tmp = tmp->next;
		}
		while(div)
		{
			mod = tmp->digit;
			tmp->digit = (div + mod) % 10;
			div = (div + mod) / 10;
			tmp = tmp->next;
		}
		r = (*head)->next->number;
		tmp_start = tmp_start->next;
		tmp = tmp_start;
		l = l->next;
		digit_delete_from_head(*head);
	}
	while (!res->tail->digit && res->tail->prev)
	{
		digit_delete_from_tail(res);
	}
	number_delete(head);
	number_delete(head);
	res->next = *head;
	*head = res;
	return;
}

void quotient(node_stack **head)
{
	if (!(*head) || !(*head)->next)
	{
		printf("there aren't enough numbers for this operation|empty stack.\n");
		return;
	}
	//if  (b=0) then a/b -- error
	if (!(*head)->tail->digit)
	{
		printf("impossible operation.\n");
		return;
	}
	//if b=1 then a/b=a
	if (((*head)->tail->digit == 1) && ((*head)->length == 1))
	{
		(*head)->next->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;
		number_delete(head);
		return;
	}
	node_stack *res = number_create();
	res->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;
	// if (a=b) then a/b = +-1
	if (comparator_mod(*head) == 2)
	{
		number_delete(head);
		number_delete(head);
		digit_push_in_head(res, 1);
		res->next = (*head);
		(*head) = res;
		return;
	}
	// if (a<b) then a/b = 0 in Z
	if (!comparator_mod(*head))
	{
		number_delete(head);
		number_delete(head);
		digit_push_in_head(res, 0);
		res->next = *head;
		*head = res;
		return;
	}
	//if a>b then a/b=res
	int quot = 0;
	(*head)->sign = 0;
	(*head)->next->sign = 0;
	node_stack *denominator = number_copy(*head);
	node_stack *dividend = number_create();
	denominator->next = dividend;
	while ((*head)->next->tail)
	{
		do
		{
			if (dividend->tail && !dividend->tail->digit)
			{
				digit_delete_from_tail(dividend);
			}
			digit_push_in_head(dividend, (*head)->next->tail->digit);
			digit_delete_from_tail((*head)->next);
			if (!comparator_mod(denominator) && (*head)->next->tail)
			{
				digit_push_in_head(res, 0);
			}
		} while (!comparator_mod(denominator) && (*head)->next->tail);
		while (comparator_mod(denominator))
		{
			diff(&denominator);
			quot++;
			dividend = denominator;
			denominator = number_copy(*head);
			denominator->next = dividend;
		}
		digit_push_in_head(res, quot);
		quot = 0;
	}
	while (!res->tail->digit && res->tail->prev)
	{
		digit_delete_from_tail(res);
	}
	number_delete(&denominator);
	number_delete(&dividend);
	number_delete(head);
	number_delete(head);
	res->next = (*head);
	*head = res;
	return;
}
