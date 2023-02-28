/******************************************************************************
 *  Compilation:  Assignment3.c
 *  Execution:
 *  Dependencies: None
 *  Data files:   None
 *
 *  Sorts a sequence of input integers so that all negative values are placed at the beginning of the output.
 *
 ******************************************************************************/

#include <stdio.h>
#include <stdlib.h>

/**
 *  @author Olivia Denbu Wilhelmsson
 *  @date Code has been generated: 25 september - 2021
 *
 *  Input: {8, 0, -1, -4, 5, -89, 12, -14, -3, 0};
 *  Output: {-1, -4, -89, -14, 5, 8, 12, 0}
 */

void init();
void placeNegativesFirst(int[]);
void swap(int[], int, int);
void print(int[]);

int main()
{
    init();
    return 0;
}


//Initializes the program and instantiates the unsorted array.
void init()
{
    int data[10] = { -8, 0, -1, -4, 5, -89, 12, -14, -3, 0 };
    print(data);
    placeNegativesFirst(data);
    print(data);
}

/**
* Rearranges the array in with all negative values placed first.
* @param data the array to be sorted
*/
void placeNegativesFirst(int data[])
{
    for(int i=0; i<sizeof(data); i++)
    {
        for(int j=i+1; j<sizeof(data); j++)
        {
            if(data[j] < 0)
            {
                swap(data, i, j);
                break;
            }
        }
    }
}

/**
* Swaps data[highIndex] and data[lowindex]
* @param data the array to be sorted
* @param highIndex the higher index of swapping
* @param lowIndex the lower index of swapping
*/
void swap(int data[], int highIndex, int lowIndex)
{
    int tempStorage = data[lowIndex];
    data[lowIndex] = data[highIndex];
    data[highIndex] = tempStorage;
}

/**
* Prints all data
* @param data to print
*/
void print(int data[])
{
    printf("Values: ");

    for(int i = 0; i<sizeof(data); i++)
    {
        printf("%d\t", data[i]);
    }

    printf("\n");
}
