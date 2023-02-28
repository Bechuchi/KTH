/* program.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"

int speed = 1000;
void printSnakeBody();

//This function is being called from display_start() ----> main function, which we call from main.c
void game()
{
    printSnakeBody();
    printApple();
    setAppleLogics();
    setWallsLogics();
    moveSnake();
    setHeadBodyCollisionLogics();
    delay(speed);
}

void printSnakeBody()
{
    int i;
    int j;
    int currentBodyIndex;
    int currentX;
    int currentY;

    //Loop through all bodyParts of the snake and sets all bits of the snake to 1 ----> White
    for(currentBodyIndex=0; currentBodyIndex<snake.length; currentBodyIndex++)
    {
        for(i = 0; i < 1; i++)
        {
            for(j = 0; j < 1; j++)
            {
                currentX = snake.x[currentBodyIndex] + i;
                currentY = snake.y[currentBodyIndex] + j;

                //We make this part of display[][] into white on the screen ----> Snake shows as white
                display[currentY][currentX] = 1;
            }
        }
    }
}
