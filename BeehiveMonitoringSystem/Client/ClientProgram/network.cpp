#include <Arduino.h>
#include "ESP8266WiFi.h"
#include "WiFiUdp.h"

const char* ssid = "DESKTOP-JV5DPM1 4709";
const char* password = "2402v?V3";
const int serverPort = 9090;
const char* serverIP = "192.168.137.1";

void initNetwork(WiFiUDP udp){
    IPAddress staticIP(192, 168, 137, 100); // Exempel på IP-adress
    IPAddress gateway(192, 168, 137, 2);    // Gateway, din dators hotspot-IP
    IPAddress subnet(255, 255, 255, 0);     // Subnet mask
    WiFi.config(staticIP, gateway, subnet);

    WiFi.begin(ssid, password);
    
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
    }

    Serial.println("Connected to WiFi");
    udp.begin(serverPort);
}

void sendData(WiFiUDP udp, String data) {
  udp.beginPacket(serverIP, serverPort);
  udp.write(data.c_str());
  udp.endPacket();
  Serial.println("Data sent: " + data);
}
