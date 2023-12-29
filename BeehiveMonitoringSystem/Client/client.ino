#include "HX711.h"
#include <EEPROM.h>
#include "EnergyManagement.h"

const int wakeUpPin = 2;
const int ledPin = 3;
EnergyManagement energyManager(wakeUpPin, ledPin);

void setup() {
  Serial.begin(9600);
  energyManager.setup();
}

void loop() {
  if (energyManager.getJustWokeUp() == true){
    if (energyManager.getIsInSleepMode() == true) {
      energyManager.activateArduino();
    } else {
    energyManager.activateSleepMode();
  }
    energyManager.setJustWokeUp(false);
  }
  delay(1000);
}



/********************
 * Hardware Configurations
 * ******************
*/

// Sensor Configurations
//const int LOADCELL_DOUT_PIN = 2;
//const int LOADCELL_SCK_PIN = 3;
HX711 scale;

void setupSensors() {
       /*sensorSetup();
   float currentWeight = getCurrentWeight();
   storeWeightInMemory(currentWeight);
   String messageToServer = "Client IPAddress:\t" + String(clientIPAddress) + "\nCurrent weight:\t\t" + String(currentWeight, 1) + " kg";*/
}

/********************
 * Network Communication
 * ******************
      ssid = Name of the WiFi-network that the Arduino will connect to.
      password = Password of the WiFi-network.
      clientIPAddress = Predefined IP-address of the Arduino. 
      serverIPAddress = Predefined IP-address of the server the Arduino will connect to.
      serverPort = Port where the UDP-packages will be sent to on the server.
*/
const char* ssid = "TN-AM2619";
const char* password = "arjUfAcyewt4";
const char* clientIPAddress = "192.168.1.11";
const char* serverIPAddress = "192.168.1.10";
const int serverPort = 8080;

// Memory Management
const int MAX_WEIGHTS = 100;
int memoryIndex = 0;

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