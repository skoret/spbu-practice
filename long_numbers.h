#pragma once

#include "struct.h"

void number_read(node_stack **head, char c, char sign);
void number_print(node_stack *head);
void number_delete(node_stack **head);
void digit_push_in_head(node_number **head, node_number **tail, char dig);
void digit_push_in_tail(node_number **head, node_number **tail, char dig);
void digit_delete_from_head(node_number **head);
void digit_delete_from_tail(node_number **tail);
