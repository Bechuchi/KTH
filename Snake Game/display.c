/*Written by Fredrik Lundström
    Updated by Olivia Denbu Wilhelmsson and Ramin Shojaei 2022 (c)*/

#include <stdint.h>
#include <pic32mx.h>
#include "snake.h" //Project header file

void *stdin, *stdout, *stderr;

#define DISPLAY_CHANGE_TO_COMMAND_MODE (PORTFCLR = 0x10)
#define DISPLAY_CHANGE_TO_DATA_MODE (PORTFSET = 0x10)

#define DISPLAY_ACTIVATE_RESET (PORTGCLR = 0x200)
#define DISPLAY_DO_NOT_RESET (PORTGSET = 0x200)

#define DISPLAY_ACTIVATE_VDD (PORTFCLR = 0x40)
#define DISPLAY_ACTIVATE_VBAT (PORTFCLR = 0x20)

#define DISPLAY_TURN_OFF_VDD (PORTFSET = 0x40)
#define DISPLAY_TURN_OFF_VBAT (PORTFSET = 0x20)

/*************************************/
//Taken from labs
/*************************************/
/* Quick sleep timer*/
void quicksleep(int cyc) {
	int i;
	for(i = cyc; i > 0; i--);
}

/* Send data to the OLED display */
uint8_t spi_send_recv(uint8_t data) {
	while(!(SPI2STAT & 0x08));
	SPI2BUF = data;
	while(!(SPI2STAT & 1));
	return SPI2BUF;
}

/* Initialize OLED display */
void display_init(void){
	DISPLAY_CHANGE_TO_COMMAND_MODE;
	quicksleep(10);
	DISPLAY_ACTIVATE_VDD;
	quicksleep(1000000);

	spi_send_recv(0xAE);
	DISPLAY_ACTIVATE_RESET;
	quicksleep(10);
	DISPLAY_DO_NOT_RESET;
	quicksleep(10);

	spi_send_recv(0x8D);
	spi_send_recv(0x14);

	spi_send_recv(0xD9);
	spi_send_recv(0xF1);

	DISPLAY_ACTIVATE_VBAT;
	quicksleep(10000000);

	spi_send_recv(0xA1);
	spi_send_recv(0xC8);

	spi_send_recv(0xDA);
	spi_send_recv(0x20);

	spi_send_recv(0xAF);
}

/* Display text */
void display_string(int line, char *s) {
	int i;
	if(line < 0 || line >= 4)
		return;
	if(!s)
		return;
	for(i = 0; i < 16; i++)
		if(*s) {
			textbuffer[line][i] = *s;
			s++;
		} else
			textbuffer[line][i] = ' ';
}

/* Display text */
void display_update(void) {
	int i, j, k;
	int c;
	for(i = 0; i < 4; i++) {
		DISPLAY_CHANGE_TO_COMMAND_MODE;
		spi_send_recv(0x22);
		spi_send_recv(i);

		spi_send_recv(0x0);
		spi_send_recv(0x10);

		DISPLAY_CHANGE_TO_DATA_MODE;

		for(j = 0; j < 16; j++) {
			c = textbuffer[i][j];
			if(c & 0x80)
				continue;

			for(k = 0; k < 8; k++)
				spi_send_recv(font[c*8 + k]);
		}
	}
}

/* This will print any image on the display with the help of an array containing the map of the active and inactive pixels*/
void display_image(int x, const uint8_t *data) {
	int i, j;

	for(i = 0; i < 4; i++) {
		DISPLAY_CHANGE_TO_COMMAND_MODE;

		spi_send_recv(0x22);
		spi_send_recv(i);

		spi_send_recv(x & 0xF);
		spi_send_recv(0x10 | ((x >> 4) & 0xF));

		DISPLAY_CHANGE_TO_DATA_MODE;

		for(j = 0; j < 128; j++)
			spi_send_recv(data[i*128 + j]);
	}
}

/*************************************/
//Helper functions to handle the display
/*************************************/
/*
    We use the display[] as a component in our project when we want to modify things to show on the display.
    The display[] is 128*32 big, and this way it is easier for us as humans to modify a certain pixel.

    We also need the oled_display[] to make the display into a computer readable component.
    The oled_display[] has 512 positions.

    We use these arrays to set which specific pixel positions we want to set as white.
    We do this by setting them equal to 1.
*/
uint8_t display[32][128];
uint8_t oled_display[512];

/*
    Translates the human readable array, display[], into the computer readable array, oled_dispaly[].
    The display is structured with 4 pages ----> All together they are the whole screen

    It converts an x- and y- coordinate system into the system of the Oled-display = Readable array for the computer
*/

//EX) display[0][1] ------> Translated intro ----------> Spot 0 on the oled, but bit 1 is set
//EX) display[0][7] ------> Translated intro ----------> Index 0 on the oled, but bit 7 is set
//EX) display[1][9] ------> Translated intro ----------> Index 1 on the oled, but bit 2 is set

                                            // { (0 1 2 3 4 5 6 7), (0 1 2 3 4 5 6 7), (0 1 2 3 4 5 6 7) ..... , (0 1 2 3 4 5 6 7)}
                                            // 521 st
void display_converter()
{
    int currentPage;
    int currentColumn;
    int currentRow;

    uint8_t powerOfTwo = 1;
    uint8_t oledNumber = 0;

    /* Loop through all four pages, one page at a time
            For each page we go through 128 columns, which is the whole width of the display
                For each column, we go through 8 rows at a time

        If-statement shows us the place where we should or oledNumber with powerOfTwo
    */
    for(currentPage=0; currentPage<4; currentPage++)
    {
        //Skärmen är 128 pixlar bred ----> En pixelrad = En kolumn
        for(currentColumn=0; currentColumn<128; currentColumn++)
        {
            powerOfTwo = 1;
            oledNumber = 0;

            for(currentRow=0; currentRow<8; currentRow++)
            {
                //OR with 1 on all 8 bits in the oledNumber
                //We go through all 8 bits by shifting by one
                if(display[8*currentPage + currentRow][currentColumn])
                {
                    oledNumber |= powerOfTwo;
                }
                powerOfTwo <<= 1;
            }

            //For each column we then set the currentPosition of the oled_display with the oledNumber
            oled_display[currentColumn + currentPage * 128] = oledNumber;
        }
    }
}

//Sets all values in the display[] and oled_display[] into zeros
void clear_display()
{
    int row;
    int column;
    int i;

    //Loop through all rows of the display
    for(row=0; row<32; row++)
    {
        //For each row we go through all columns
        for(column=0; column<128; column++)
        {
            //We set the current position of the human-display to zero
            display[row][column] = 0;
        }
    }

    //Cleares all pixels of the display ----> All pixels should display black
    for (i = 0; i < 512; i++)
    {
        oled_display[i] = 0;
    }
}

//This function is used to initalize the game and also makes the transformation of the OLED display so we can show things on the screen
//1. Clear the screen
//2. GameLogics
//3. Translate into computer readable structure
//4. Display on the screen
void display_start()
{
	clear_display();
	game();
	display_converter();
	display_image(0, oled_display);
}



