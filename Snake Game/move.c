/* move.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"

int btns;
void makeBodyFollowHead();
void updateBodyCoordinates(int currentIndex);

//Using the buttons to make the snake move
void moveSnake()
{
    btns = getbtns(); //Get buttons

    makeBodyFollowHead();
    setCurrentDirection();
    connectDirectionToBtn();
}

//We update the body to follow the head
//The new position of the currentPart is at index where the one before just was
void makeBodyFollowHead()
{
    int i;
    for(i=snake.length-1; i>0; i--)
    {
        snake.x[i] = snake.x[i-1];
        snake.y[i] = snake.y[i-1];
    }
}



