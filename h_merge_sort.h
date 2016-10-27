/* File h_merge_sort.h */

#pragma once

void merge_sort(char** a, int n);
void merge_sort_recursively(char** a, char** b, int  n , int *pos);
void merge(char** a, int fir, char** b, int sec, char** c);
void copy_array(char** a, int n, char** b);
