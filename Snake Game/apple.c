/* apple.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"

//Predefined values for apple x- and y-coordinates ---> Used for printing out apples at different positions (combined with currentScore)
//x-axis has a wider range than the y-axis ---> 128 VS 32 pixles
int apple_x[] = {
    96,
    29,
    38,
    75,
    16,
    89,
    45,
    70,
    107,
    13,
    68,
    25
};

int apple_y[] = {
    18,
    24,
    14,
    9,
    27,
    12,
    11,
    30,
    12,
    16,
    11,
    22
};

/*  Prints out an apple on the display
        * The coordinates of the apple, x and y, varies with the score and 12 hardcoded values
        * Combined with %12 the apples are always generated
*/
void printApple()
{
    int currentIndex = score % 12;

    display[apple_y[currentIndex]][apple_x[currentIndex]] = 1;
}

//Position 0 in the snake.y[0] and snake.x[0] is the head
int appleSnakeCoordinatesAreEqual()
{
    return (snake.y[0] == apple_y[score%12]) && (snake.x[0] == apple_x[score%12]);
}

void increaseLength()
{
    snake.length = snake.length+5;  //the snake gets bigger when apple is eaten
}

void setAppleLogics()
{
    if(appleSnakeCoordinatesAreEqual())
    {
        increaseScore();
        increaseLength();
    }
}
