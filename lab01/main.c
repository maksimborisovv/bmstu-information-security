#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void getId(char id[40]) {
    system("wmic csproduct get IdentifyingNumber > tmp.txt & cmd /A /C type tmp.txt > id.txt");
    system("del tmp.txt");

    FILE *fin = fopen("id.txt", "r");

    fscanf(fin, "%s", id);
    fscanf(fin, "%s", id);

    fclose(fin);
    system("del id.txt");
}

int main(void) {
    char id[40];
    getId(id);

    if (strcmp(id, "KAN0CV08N227422") == 0) {
        printf("OK\n");
    } else {
        printf("NOT OK\n%s", id);
        return 1;
    }

    int a, b;
    scanf("%d %d", &a, &b);

    printf("%d + %d = %d\n", a, b, a + b);
    system("pause");
}