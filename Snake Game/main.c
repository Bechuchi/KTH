/*
    Written by Fredrik Lundström
    Updated by Olivia Denbu Wilhelmsson and Ramin Rojas, 2022 (c)*/

#include <stdint.h>
#include <pic32mx.h>
#include "snake.h"

//Interrupt Service Routine
void user_isr()
{
	return;
}

void initHardware();
void initGame();
int main(void)
{
    initHardware();
    initGame();

	while(1)
    {
		display_start(); //Start display
	}

	return;
}

void initHardware()
{
    /*
	  This will set the peripheral bus clock to the same frequency
	  as the sysclock. That means 80 MHz, when the microcontroller
	  is running at 80 MHz. Changed 2017, as recommended by Axel.
	*/
	SYSKEY = 0xAA996655;  /* Unlock OSCCON, step 1 */
	SYSKEY = 0x556699AA;  /* Unlock OSCCON, step 2 */
	while(OSCCON & (1 << 21)); /* Wait until PBDIV ready */
	OSCCONCLR = 0x180000; /* clear PBDIV bit <0,1> */
	while(OSCCON & (1 << 21));  /* Wait until PBDIV ready */
	SYSKEY = 0x0;  /* Lock OSCCON */

	/* Set up output pins */
	AD1PCFG = 0xFFFF;
	ODCE = 0x0;
	TRISECLR = 0xFF;
	PORTE = 0x0;

	/* Output pins for display signals */
	PORTF = 0xFFFF;
	PORTG = (1 << 9);
	ODCF = 0x0;
	ODCG = 0x0;
	TRISFCLR = 0x70;
	TRISGCLR = 0x200;

	/* Set up input pins */
	TRISDSET = (1 << 8);
	TRISFSET = (1 << 1);

	/* Set up SPI as master */
	SPI2CON = 0;
	SPI2BRG = 4;
	/* SPI2STAT bit SPIROV = 0; */
	SPI2STATCLR = 0x40;
	/* SPI2CON bit CKP = 1; */
        SPI2CONSET = 0x40;
	/* SPI2CON bit MSTEN = 1; */
	SPI2CONSET = 0x20;
	/* SPI2CON bit ON = 1; */
	SPI2CONSET = 0x8000;

	display_init(); //Initialize OLED display
}

/*
    Initialization of the snake, before starting to play: Setting the default values

    - We set values to the snake head, and its body
    - The init value of the y-axis is 9, constantly for the whole snakeBody
    - The init value of the x-axis starts at 24, but increases up to the value 30

    - We also set its default direction and player score to 0 ----> Which is used for generating apples and later display score to currentUser

    - Snake.h ----> We have created a struct Snake to handle different values/properties of the snake:
                        * Direction
                        * x- and y- coordinate values
                        * length of snakeBody
*/
void initGame()
{
    /*
        There are two arrays, snakeX[100] and snakeY[100] which represents the x- and y-coordinates of the snake
        We have created a struct Snake, in snake.h, which we use to manipulate the snake while the game is being played

        The head of the snake, which is the main position to keep track of, is at the 0:s position of each array
        Below we set the values to the head coordinates, but also to the initial snakeBody
    */

	snake.y = snakeY;
	snake.x = snakeX;

	snake.y[0] = 9;
	snake.x[0] = 24;
	snake.y[1] = 9;
	snake.x[1] = 25;
	snake.y[2] = 9;
	snake.x[2] = 26;
	snake.y[3] = 9;
	snake.x[3] = 27;
	snake.y[4] = 9;
	snake.x[4] = 28;
	snake.y[5] = 9;
	snake.x[5] = 29;
	snake.y[6] = 9;
	snake.x[6] = 30;

    score = 0;
	snake.length = 7;
	snake.dir = 'L';
}

