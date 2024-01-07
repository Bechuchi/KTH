#include "MockedData.h"
#include "EnergyManagement.h"
#include "Arduino.h"
#include "ArduinoJson.h"
#include "ESP8266WiFi.h"
#include "WiFiUdp.h"
#include "EEPROM.h"

#define WAKE_UP_INTERVAL 5e6

const char* ssid = "TN-AM2619";
const char* password = "arjUfAcyewt4";
/*const char* ssid = "DESKTOP-EQO8M7S 7857";
const char* password = "BrunStege1!";*/
//const char* clientIPAddress = "192.168.1.11";
const char* clientIPAddress = "nodemcu.local";
const char* serverIP = "192.168.1.97";
//"192.168.137.1" 
const int serverPort = 9090;

MockedData mockedData;
WiFiUDP udp;

/*
 * This part of the program is only activated one single time.
 * It perfoms all nesseccary set ups for hardware components and
 * different parts of the software program.
*/
void setup() {
    Serial.begin(115200);
    delay(1000);
    WiFi.begin(ssid, password);

    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }

    Serial.println(WiFi.localIP());
    Serial.println("Ansluten till WiFi");
    udp.begin(serverPort);
    //ESP.deepSleep(WAKE_UP_INTERVAL);
}

void loop() {
  randomSeed(analogRead(0));
  DynamicJsonDocument doc(1024);
  mockedData.getJSONFormat(doc);
  //Serialisera JSON och skicka det
  char jsonBuffer[1024];
  serializeJson(doc, jsonBuffer);

  udp.beginPacket(serverIP, serverPort);
  udp.write(jsonBuffer);
  udp.endPacket();
}

