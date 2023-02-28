#include <stdio.h>
#include <stdlib.h>

void iterative();
void recursive();

int main()
{
    printf("Input:\t\t\t");
    //iterative();
    recursive();

    return 0;
}

/*iterates over input characters until end of line has been reached
 *max amount of characters is 20*/
void iterative()
{
    char str[20];
    int i = 0;
    char c;

    while((c = getc(stdin)) != '\n')
    {
        str[i+1] = c;                         //store each character inside array
        i++;                                //increment element index
    }

    printf("Reversed by iteration:\t");

    for(i; i>0; i--)
    {
        putchar(str[i]);                    //iterate over str-array and prints out each element that has been stored
    }
}

/*prints out input characters in reversed order
 *done by recursion*/
void recursive()
{
    char c = getc(stdin);                   //local variables are stored on the stack

    if(c == '\n')
    {
        printf("Reversed by recursion:\t");
    }
    else
    {
        recursive();
        putchar(c);                         //each character that has been stored will be printed out
    }
}
