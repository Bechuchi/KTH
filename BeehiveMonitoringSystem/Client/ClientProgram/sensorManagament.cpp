#include "sensorManagement.h"
#include "ArduinoJson.h"
#include <Arduino.h>
#include <EEPROM.h>

const int EEPROM_SIZE = 512;  // Anta att vi har 512 byte EEPROM tillgängligt
int currentIndex = 0;  // Håller reda på nästa lediga plats i EEPROM

void initializeSensors() {
    EEPROM.begin(EEPROM_SIZE);
    currentIndex = EEPROM.read(0);
    if (currentIndex >= EEPROM_SIZE || currentIndex == 0) {
        currentIndex = 1;
    }
}

float generateMockedWeight() {
    float mockedWeight = 30.0 + random(-5, 5);
    return mockedWeight; 
}

String getMockedWeightCollection(){
  String collection = "2, 14, 22, 33, 41, 47";
  return collection;
}
