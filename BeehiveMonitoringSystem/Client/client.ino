#include "HX711.h"
#include <EEPROM.h>
#include <avr/sleep.h>
#include <avr/power.h>
#include <avr/wdt.h>

//LED skall be off when Arduino is in sleep mode
const int wakeUpPin = 2;
const int ledPin = 3;

// Sensor Configurations
//const int LOADCELL_DOUT_PIN = 2;
//const int LOADCELL_SCK_PIN = 3;
HX711 scale;

// Wifi-Configurations
const char* ssid = "TN-AM2619";
const char* password = "arjUfAcyewt4";
const char* clientIPAddress = "192.168.1.11";
const char* serverIP = "192.168.1.10";
const int serverPort = 8080;

// Memory Management
const int MAX_WEIGHTS = 100;
int memoryIndex = 0;

volatile bool isAsleep = false; // Håller reda på om Arduino är i sleep mode
volatile bool justWokeUp = false;

void wakeUp() {
  justWokeUp = true; // Sätt flaggan att Arduino precis har vaknat
}

void setup() {
  Serial.begin(9600);
  pinMode(wakeUpPin, INPUT_PULLUP);
  pinMode(ledPin, OUTPUT);
  attachInterrupt(digitalPinToInterrupt(wakeUpPin), wakeUp, CHANGE);
  digitalWrite(ledPin, HIGH); // Initialt tänd LED
     /*sensorSetup();
   float currentWeight = getCurrentWeight();
   storeWeightInMemory(currentWeight);
   String messageToServer = "Client IPAddress:\t" + String(clientIPAddress) + "\nCurrent weight:\t\t" + String(currentWeight, 1) + " kg";*/
}

void activateSleepMode() {
  isAsleep = true;
  Serial.println("Going to sleep mode...");
  Serial.flush();
  delay(1000); // Öka om nödvändigt

  digitalWrite(ledPin, LOW);
  set_sleep_mode(SLEEP_MODE_PWR_DOWN);
  sleep_enable();
  sleep_mode();

  // När Arduino vaknar, ställ in LED-pinnen tillbaka till dess föregående tillstånd
  digitalWrite(ledPin, digitalRead(wakeUpPin) == HIGH ? HIGH : LOW);
}

void loop() {
  if (justWokeUp) {
    if (isAsleep) {
      // Vaknade från sleep mode
      isAsleep = false;
      Serial.println("Woke up!");
      digitalWrite(ledPin, HIGH); // Tänd LED när Arduino är vaken
    } else {
      // Går i sleep mode
      Serial.println("Going to sleep mode...");
      Serial.flush();
      digitalWrite(ledPin, LOW); // Stäng av LED innan sleep mode
      delay(100); // Kort fördröjning

      isAsleep = true;
      set_sleep_mode(SLEEP_MODE_PWR_DOWN);
      sleep_enable();
      sleep_mode();
    }
    justWokeUp = false; // Återställ flaggan
  }
  delay(1000);
}

void sensorSetup() {
  Serial.begin(115200);
  //scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
  scale.set_scale(2280.f);
  scale.tare();
  calibration();
}

void calibration() {

}

float getCurrentWeight() {
    return 20.0;
}

void storeWeightInMemory(float newWeight) {
  int address = memoryIndex * sizeof(float); // Beräknar adressen i EEPROM
  if (address < EEPROM.length() - sizeof(float)) {
    EEPROM.put(address, newWeight); // Lagrar det nya värdet
    memoryIndex = (memoryIndex + 1) % MAX_WEIGHTS; // Uppdaterar indexet för nästa vikt
  }
}

void getAllWeightsInMemory() {
  int index = 0;

  while (index < EEPROM.length()) {
     float storedValue;
    EEPROM.get(index, storedValue);

    Serial.print("Address ");
    Serial.print(index);
    Serial.print(": ");
    Serial.println(storedValue, 4); // Använd 4 decimalers precision

    index += sizeof(float); // Flytta fram indexet med storleken av en float
  }
}
