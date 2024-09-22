#include <EEPROM.h>
#include <Arduino.h>

int address = 0; // Startadress för skrivning

void storeValue(int currentWeight) {
    EEPROM.put(address, currentWeight);
    address += sizeof(int);
    EEPROM.commit();
}

String readMemory() {
    String storedValues = "";
    int readValue;
    for (int readAddr = 0; readAddr < EEPROM.length(); readAddr += sizeof(int)) {
        EEPROM.get(readAddr, readValue);
        if (readValue == 0) break;
        if (storedValues.length() > 0) storedValues += ", ";
        storedValues += String(readValue);
    }
    return storedValues;
}

/*void readMemory(){
   int readValue;
   for (int readAddr = 0; readAddr < EEPROM.length(); readAddr += sizeof(int)) {
        EEPROM.get(readAddr, readValue);
        Serial.print("Value at address ");
        Serial.print(readAddr);
        Serial.print(": ");
        Serial.println(readValue);
    }
}*/

void clearMemory(){
    for (int i = 0; i < EEPROM.length(); i++) {
          EEPROM.put(i, 0);
    }
      
    EEPROM.commit();
}
