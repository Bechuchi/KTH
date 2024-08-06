#include <Arduino.h>
#include "HX711.h"
#include "Bonezegei_DHT11.h"
#include "MockedData.h"
#include "EnergyManagement.h"
#include "ArduinoJson.h"
#include "ESP8266WiFi.h"
#include "WiFiUdp.h"
#include "EEPROM.h"

//#define BATTERY_PIN D7  // The GPIO pin connected to the battery
#define WAKE_UP_INTERVAL 5e6
bool continueRunning = true;

//####################
//Memory Configurations
//####################
const int addr = 0; // Adress i EEPROM där vi lagrar värdet
float weight = 30.0; // Värde som ska lagras, t.ex. 30 kg

//####################
//Wi-Fi Configurations
//####################
const char* ssid = "TN-AM2619";
const char* password = "arjUfAcyewt4";
//const char* ssidComputerHotspot = "DESKTOP-EQO8M7S 7857";
//const char* password = "*********";
//const char* clientIPAddress = "192.168.1.11";
const char* clientIPAddress = "nodemcu.local";
const char* serverIP = "192.168.1.97";
const int serverPort = 9090;

MockedData mockedData;
WiFiUDP udp;

// HX711 circuit wiring
//uint8_t LOADCELL_DOUT_PIN = D6;
//uint8_t LOADCELL_SCK_PIN = D7;
HX711 scale;
Bonezegei_DHT11 dht(14);

void setup() { 
    Serial.begin(115200);
    //pinMode(BATTERY_PIN, INPUT);
    delay(1000);
    
    /*if (digitalRead(BATTERY_PIN) == HIGH) {
      dht.begin();
      WiFi.begin(ssid, password);
      WiFi.begin(ssid, password);

      while (WiFi.status() != WL_CONNECTED) {
          delay(500);
          Serial.print(".");
      }

      Serial.println(WiFi.localIP());
      Serial.println("Ansluten till WiFi");
      udp.begin(serverPort);
    }*/

    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println(WiFi.localIP());
    Serial.println("Ansluten till WiFi");
    udp.begin(serverPort);
    //ESP.deepSleep(WAKE_UP_INTERVAL);

    
  // turn the LED on when we're done
  /*pinMode(13, OUTPUT);
  digitalWrite(13, HIGH);
  EEPROM.end();
  Serial.println("Memory Cleared");*/
    //ESP.deepSleep(WAKE_UP_INTERVAL);
}

void loop() {
    if(!continueRunning) {
        return;
    }

    randomSeed(analogRead(0));
    DynamicJsonDocument doc(1024);
    //mockedData.getJSONFormat(doc);
    formatOutgoingDataPacket(doc);

     // Serialisera JSON-dokumentet till en sträng
    char jsonBuffer[1024];
    serializeJson(doc, jsonBuffer);

    udp.beginPacket(serverIP, serverPort);
    udp.write(jsonBuffer);
    udp.endPacket();

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
        continueRunning = false;
    }

    delay(2000); // Vänta lite innan nästa sändning för att undvika att spammar nätverket
}

void readHX711Sensor() {
}

void readDHT11Sensor() {
    /*
    if (dht.getData()) {                         // get All data from DHT11
        float tempDeg = dht.getTemperature();      // return temperature in celsius
        float tempFar = dht.getTemperature(true);  // return temperature in fahrenheit if true celsius of false
        int hum = dht.getHumidity();               // return humidity
        Serial.printf("Temperature: %0.1lf°C  %0.1lf°F Humidity:%d \n", tempDeg, tempFar, hum);
    */
}

void formatOutgoingDataPacket(DynamicJsonDocument &doc) {
    doc["IPaddress"] = WiFi.localIP().toString();  // Konverterar IP-adressen till en sträng
    doc["Weight"] = "30 kg";
    doc["Temperature"] = "35 degree";
    doc["Humidity"] = "55%";
}