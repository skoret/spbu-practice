#pragma once

typedef struct node_number
{
	char digit;
	struct node_number *next;
	struct node_number *prev;
} node_number;

typedef struct node_stack
{
	char sign;
	long length;
	struct node_number *number;
	struct node_number *tail;
	struct node_stack *next;
} node_stack;
