#include <stdio.h>
#include <stdlib.h>

#include "long_numbers.h"
#include "long_arithmetic.h"
#include "struct.h"

int comparator_mod(node_stack *head)
{
	if (!head || !head->next)
	{
		printf("Nothing to compare.\n");
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
	char mod = 0;
	char div = 0;
	node_number *l = (*head)->number;
	node_number *r = (*head)->next->number;
	(*head)->next->length = 0;
	while (l && r)
	{
		mod = r->digit;
		r->digit = (mod + l->digit + div) % 10;
		div = (mod + l->digit + div) / 10;
		(*head)->next->length++;
		if (r->next == NULL)
		{
			(*head)->next->tail = r;
		}
		l = l->next;
		r = r->next;
	}
	while (r)
	{
		mod = r->digit;
		r->digit = (mod + div) % 10;
		div = (mod + div) / 10;
		(*head)->next->length++;
		if (r->next == NULL)
		{
			(*head)->next->tail = r;
		}
		r = r->next;
	}
	while (l)
	{
		mod = l->digit;
		l->digit = (mod + div) % 10;
		div = (mod + div) / 10;
		digit_push_in_tail(&r, &(*head)->next->tail, l->digit);
		(*head)->next->length++;
		l = l->next;
	}
	if (div)
	{
		digit_push_in_tail(&r, &(*head)->next->tail, div);
	}
	number_delete(head);
}

void diff(node_stack **head)
{
	if (comparator_mod(*head) == 2) //if a=b then (a-b)=0
	{
		number_delete(head); //del a
		number_delete(head); //del b
		node_stack *tmp = number_create();
		digit_push_in_head(&(tmp->number), &(tmp->tail), 0);
		tmp->length = 1;
		tmp->next = *head;
		*head = tmp;
		return;
	}
	node_number *min;
	node_number *max;
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
		digit_push_in_tail(&res->number, &res->tail, diffrence);
		res->length++;
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
		digit_push_in_tail(&res->number, &res->tail, max->digit);
		res->length++;
		max = max->next;
	}
	while (!res->tail->digit && res->tail->prev)
	{
		digit_delete_from_tail(&res->tail);
		res->length--;
	}
	number_delete(head);  
	number_delete(head); //del min and max
	res->next = *head;
	*head = res;
}

void compos(node_stack **head)
{
	node_stack *res = number_create();
	if (!(*head) || !(*head)) //if (a=0) or (b=0) then a*b=0
	{
		digit_push_in_head(&res->number, &res->tail, 0);
		res->sign = 0;
		number_delete(head);
		number_delete(head);
		res->next = *head;
		*head = res;
		return;
	}
	res->sign = ((*head)->sign == (*head)->next->sign) ? 0 : 1;	
	digit_push_in_head(&res->number, &res->tail, 0);
	char mod = 0;
	char div = 0;
	int rank = 1;
	node_number *l = (*head)->number;
	node_number *r = (*head)->next->number;
	while (l)
	{
		node_stack *tmp = number_create();
		tmp->next = res;
		while (r)
		{
			mod = ((l->digit * r->digit) + div) % 10;
			div = ((l->digit * r->digit) + div) / 10;
			digit_push_in_tail(&tmp->number, &tmp->tail, mod);
			r = r->next;
		}
		digit_push_in_tail(&tmp->number, &tmp->tail, div);
		div = 0;
		int i = 1;
		while (i++ < rank) //tmp rank shift
		{
			digit_push_in_head(&tmp->number, &tmp->tail, 0);
		}
		rank++;
		sum(&tmp);		
		r = (*head)->next->number;
		l = l->next;
	}
	digit_push_in_tail(&res->number, &res->tail, div);
	while (!res->tail->digit && res->tail->prev)
	{
		digit_delete_from_tail(&res->tail);
		res->length--;
	}
	number_delete(head);
	number_delete(head);
	res->next = *head;
	*head = res;
	return;
}





























