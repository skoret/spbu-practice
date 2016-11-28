#pragma once

#include "struct.h"

node_stack* number_create();
void number_read(node_stack **head, char c, char sign);
void number_print(node_stack *head);
node_stack* number_copy(node_stack *head);
void number_delete(node_stack **head);
void digit_push_in_head(node_stack *head, char dig);
void digit_push_in_tail(node_stack *head, char dig);
void digit_delete_from_head(node_stack *head);
void digit_delete_from_tail(node_stack *head);
