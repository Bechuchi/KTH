#include <Arduino.h>
#include "HX711.h"
#include "ArduinoJson.h"
#include "EEPROM.h"
#include "ESP8266WiFi.h"
#include "WiFiUdp.h"
#include "network.h"
#include "sensorManagement.h"
#include "memory.h"

bool continueRunning = true;

// HX711 circuit wiring
const int LOADCELL_DOUT_PIN = 4;
const int LOADCELL_SCK_PIN = 5;
HX711 scale;

WiFiUDP udp;
const int EEPROM_SIZE = 512;
char jsonBuffer[1024];
String outgoingMessage;

void setup() {
    Serial.begin(9600);
    delay(2000);
    EEPROM.begin(EEPROM_SIZE); // Allokerar 512 bytes i flashminnet för EEPROM-användning
    
    randomSeed(analogRead(A0));
    
    initNetwork(udp);

    //float weight = generateFakeWeightData();
    //storeValue(weight);
}

void loop() {     
    if(!continueRunning) {
        return;
    }  

    if (WiFi.status() == WL_CONNECTED) {
      //String weightValues = readMemory();
      String weightValues = getMockedWeightCollection();
      outgoingMessage = formatMessageToServer(weightValues); 
      Serial.println("Verification of Connected to server, sending data...");  

      sendData(udp, outgoingMessage);

      // Lyssna efter svar från servern
      int packetSize = udp.parsePacket();
      if (packetSize) {
          // Läs paketet
          int len = udp.read(jsonBuffer, 1024);
          if (len > 0) {
              jsonBuffer[len] = 0;  // Null-terminate string
          }
          Serial.print("Server Response: ");
          Serial.println(jsonBuffer);
          clearMemory(); 
          continueRunning = false;
      }  
    } else {
      Serial.println("Server not reachable, storing data...");
    }
  
    delay(2000);  
}

String formatMessageToServer(String weightList) {
    DynamicJsonDocument doc(1024);
    char jsonBuffer[1024];
    
    doc["IPaddress"] = WiFi.localIP().toString();  // Konverterar IP-adressen till en sträng
    doc["Weight"] = String(weightList);

    //String jsonBuffer;
    serializeJson(doc, jsonBuffer);

    return jsonBuffer;
}
