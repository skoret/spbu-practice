#include <stdio.h>
#include <stdlib.h>

#define dim 12

int main(int argc, char *argv[]) {

    if (argc != 2) {
        printf("Call with 'filename', 'rows', 'cols'");
        exit(1);
    }

    FILE *output;
    if ((output = fopen(argv[1], "w")) == NULL) {
        printf("Can't write file.");
        exit(1);
    }

    fprintf(output, "%d %d\n", dim, dim);

    for (int i = 1; i <= dim; i++) {
        long double s = 0.0;
        for (int j = 1; j <= dim; j++) {
            long double a = 1.0/(i+j-1.0);
            fprintf(output, "%Lf ", a);
            s += a;
        }
        fprintf(output, "%Lf\n", s);
    }

}