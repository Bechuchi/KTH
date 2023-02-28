/* direction.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"
int btns;
void setDirectionValue(char currentDirection);

/* When the snake should move in horizontal direction we modify values of the snake x-koordinates
        EX) Increase x for moving to the right
 * When the snake should move in vertical direction we modify values of the snake y-koordinates */
void setCurrentDirection()
{
    if(isRightDirection())   //Every x coordinate increases by 1 for going in right direction
    {
        snake.x[0] += 1;
    }

    if(isLeftDirection())   //Every x coordinate decreases by 1 for going in left direction
    {
        snake.x[0] -= 1;
    }

    if(isDownDirection())    //Every y coordinate decreases by 1 for going in down direction
    {
        snake.y[0] -= 1;
    }

    if(isUpDirection())      //Every y coordinate increases by 1 for going in up direction
    {
        snake.y[0] += 1;
    }
}

/***********************************************/
//Helper functions for setCurrentDirection()
/***********************************************/
int isRightDirection(void)  //Returns true value if direction is Right
{
    return snake.dir == 'R';
}

int isLeftDirection(void)  //Returns true value if direction is Left
{
    return snake.dir == 'L';
}

int isDownDirection(void)  //Returns true value if direction is Down
{
    return snake.dir == 'D';
}

int isUpDirection(void)    //Returns true value if direction is Up
{
    return snake.dir == 'U';
}

void connectDirectionToBtn()
{
    //We can not go RIGHT if the currentDirection is LEFT
    if(!isLeftDirection())
    {
        if(btns == 0b0001)
        {
            setDirectionValue('R');
        }
    }

    //We can not go LEFT if the currentDirection is RIGHT
    if(!isRightDirection())
    {
        if(btns == 0b1000)
        {
            setDirectionValue('L');
        }
    }

    //We can not go UP if the currentDirection is DOWN
    if(!isDownDirection())
    {
        if(btns == 0b0100)
        {
            setDirectionValue('U');
        }
    }

    //We can not go DOWN if the currentDirection is UP
    if(!isUpDirection())
    {
        if(btns == 0b0010)
        {
            setDirectionValue('D');
        }
    }
}

void setDirectionValue(char currentDirection)
{
    snake.dir = currentDirection;
}
