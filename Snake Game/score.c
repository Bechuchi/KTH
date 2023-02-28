/* score.c
    * Written by Olivia Denbu Wilhelmsson and Ramin Shojaei (c)
    * Updated 2022
*/

#include <stdint.h>
#include <pic32mx.h>
#include "snake.h"

//PORTE is used for writing to the leds ----> We use them to show currentScore while the game is being played (updated live while playing)
volatile int* PE = (volatile int*) 0xbf886110;

void increaseScore(void)
{
    score++;
    *PE += 0x01;
}
