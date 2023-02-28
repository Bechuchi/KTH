/* gameOver.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/
#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"

int btns;
int wallSnakeCoordinatesAreEqual();
int bodyHeadCoordinatesAreEqual(int currentIndex);

/*
    Score:
        - We display the finalScore both as a string on the display, but also with the lamps ---> Highest score is 255
        - sprintf to convert from score as integer into a string to display it

    Restart game:
        - If the user wants to play again he presses btn1
*/
void gameOver(){

    char finalScore[3];
    sprintf(finalScore, "%d", score);

    int currentButton;

    while(1)
    {
        display_string(0, "GAME OVER!");
        display_string(1, "**********");
        display_string(2, finalScore);
        display_string(3, "Restart: Btn1");
        display_update();

        if(currentButton = getButtonOne())
        {
            main();
            break;
        }
    }
}

/***********************************/
//Helper functions: Head hit walls
/***********************************/

//Checks if the snake has hit the wall ----> If it has, we call the function gameOver()
void setWallsLogics()
{
    if(wallSnakeCoordinatesAreEqual())
        gameOver();
}

/*
    Checking if the coordinates of the snakeHead has hit the wall
        * y ---> Checks vertically, 0 and 31
        * x ---> Checks horizontally, 0 and 127
*/
int wallSnakeCoordinatesAreEqual()
{
    return (snake.y[0] == 31 || snake.y[0] == 0) || (snake.x[0] == 127 || snake.x[0] == 0);
}

/***********************************/
//Helper functions: Head hit Body
/***********************************/
/*
    Looping through all coordinates of the snakeBody to see if the head has hit the body
    We send the currentIndex of the body as an input parameter to bodyHeadCoordinatesAreEqual() and if it hits the head ----> gameOver()
*/
void setHeadBodyCollisionLogics()
{
    int i;
    for(i=snake.length-1; i>0; i--)
    {
        if(bodyHeadCoordinatesAreEqual(i))
        {
            gameOver();
        }
    }
}

/*
    Head is at arrayIndex 0 so we check if both: snake.x[0] is equal to the currentIndex of the bodyParts and also for snake.y[0]
*/
int bodyHeadCoordinatesAreEqual(int currentIndex)
{
    return (snake.x[0] == snake.x[currentIndex]) && (snake.y[0] == snake.y[currentIndex]);
}
