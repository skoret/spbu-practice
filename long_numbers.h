#pragma once

#include "struct.h"

void read_number(node_stack **head, char c, char sign);
void push_digit_in_head(node_number **head, node_number **tail, char dig);
void push_digit_in_tail(node_number **head, node_number **tail, char dig);
void delete_digit_from_head(node_number **head);
void delete_digit_from_tail(node_number **tail);
void print_number(node_stack *head);
void delete_number(node_stack **head);
