/*Written by Fredrik Lundström.
    Updated by Olivia Denbu Wilhelmsson and Ramin Shojaei 2022 (c)
*/

void display_image(int x, const uint8_t *data);
void display_init(void);
void display_string(int line, char *s);
void display_update(void);
uint8_t spi_send_recv(uint8_t data);

void quicksleep(int cyc);

void display_debug( volatile int * const addr );

extern const uint8_t const font[128*8];
extern const uint8_t const icon[128];
uint8_t boarder [64*8];
extern char textbuffer[4][16];

void delay(int);
void enable_interrupt(void);

/****************/
//Project specific
/****************/
uint8_t display[32][128];
uint8_t oled_display[512];

uint8_t snakeX[100];
uint8_t snakeY[100];

int main(void);
void display_start();
void clear_display();
void display_converter();
int score;
extern int apple_x[];
extern int apple_y[];

//Struct to have a snake object
typedef struct Snake
{
    uint8_t *y;
    uint8_t *x;
    int length;
    char dir;

} Snake;

Snake snake;
