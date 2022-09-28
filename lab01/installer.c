#include <stdio.h>
#include <stdlib.h>

#define CODEBEGIN "#include <stdio.h> \n\
#include <stdlib.h> \n\
#include <string.h> \n\
 \n\
void getId(char id[40]) { \n\
    system(\"wmic csproduct get IdentifyingNumber > tmp.txt & cmd /A /C type tmp.txt > id.txt\"); \n\
    system(\"del tmp.txt\"); \n\
 \n\
    FILE *fin = fopen(\"id.txt\", \"r\"); \n\
 \n\
    fscanf(fin, \"%s\", id); \n\
    fscanf(fin, \"%s\", id); \n\
 \n\
    fclose(fin); \n\
    system(\"del id.txt\"); \n\
} \n\
 \n\
int main(void) { \n\
    char id[40]; \n\
    getId(id); \n\
 \n\
    if (strcmp(id, \""


#define CODEEND "\") == 0) { \n\
        printf(\"OK\\n\"); \n\
    } else { \n\
        printf(\"NOT OK\\n%s\", id); \n\
        return 1; \n\
    } \n\
 \n\
    int a, b; \n\
    scanf(\"%d %d\", &a, &b); \n\
 \n\
    printf(\"%d + %d = %d\\n\", a, b, a + b); \n\
    system(\"pause\"); \n\
}"

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
    FILE *fout = fopen("prog.c", "w");
    char id[40];
    getId(id);
    fprintf(fout, "%s", CODEBEGIN);
    fprintf(fout, id);
    fprintf(fout, "%s", CODEEND);

    fclose(fout);

    system("gcc prog.c -o prog.exe");
    system("del prog.c");
}

// gcc installer.c -o installer.exe
// installer.exe
// prog.exe