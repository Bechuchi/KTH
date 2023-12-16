#include "HX711.h"
#include <EEPROM.h>

// HX711 circuit wiring
const int LOADCELL_DOUT_PIN = 2;
const int LOADCELL_SCK_PIN = 3;

HX711 scale;

/**
Sensor
*/
void sensorSetup() {
  Serial.begin(38400);
  scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
  calibration();
}

void calibration() {
  scale.set_scale(2280.f);  // kalibreringsvärde
  scale.tare();             // nollställer skalan
}

void updateMemory(int address, int value) {
  byte lowByte = value & 0xFF; // Isolerar de lägre 8 bitarna
  byte highByte = (value >> 8) & 0xFF; // Skiftar och isolerar de högre 8 bitarna

  EEPROM.update(address, lowByte); // Skriver den lägre byten
  EEPROM.update(address + 1, highByte); // Skriver den högre byten
}

void setup() {
  sensorSetup();
  int currentWeight = scale.get_units(10); // Beräknar medelvärdet av 10 mätningar för exakthet
  Serial.print("Dagens vikt: ");
  Serial.println(currentWeight, 1); // Skriver ut dagens vikt

  // Anta att vi börjar skriva från adress 0
  updateMemory(0, currentWeight);
}

void loop() {
  boolean signalAvailable = true;
  
}
