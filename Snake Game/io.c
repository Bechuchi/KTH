/* io.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <stdio.h>
#include <stdlib.h>
#include <pic32mx.h>
#include "snake.h"

int getbtns(void)
{
    //Btns 2,3 and 4 are on PORTD from bit 5
    //Shift the bits to the right and check if active with AND operation
    //----> See if buttons 4,3 or 2 are active.
    int currentBtn = (PORTD >> 5) & 0x0007;
    currentBtn = (currentBtn << 1);             //We shift by one so they map correctly to how we want them places for different directions

    //The first button is in the PORTF register

    int btn1 = (PORTF >> 1) & 0x1;
    currentBtn = btn1 | currentBtn;

    //Return the active button as an integer (active bits will be set to 1)
    return currentBtn;
}

//Uses btn1 also to restart the game after gameOver()
int getButtonOne(void)
{
    return ((PORTF) & 0x2);
}

