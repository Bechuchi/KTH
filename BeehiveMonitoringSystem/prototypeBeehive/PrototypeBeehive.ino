#include <Arduino.h>
#include "ESP8266WiFi.h"
#include "WiFiUdp.h"
#include "ArduinoJson.h"
#include "NetworkManager.h"

// === 1. Variabler och instanser ===
WiFiUDP udp;
long todayWeight = 0;  // Dagens vikt
bool ackReceived = false;  // Indikerar om servern bekräftat mottagning
int packetID = 0;  // Identifierar paket för att hantera UDP-reliabilitet
char jsonBuffer[1024];
String outgoingMessage;
WiFiUDP udpReceiver;  // Ny UDP-instans för att ta emot ACK
const int ackPort = 9090;  // Samma port som Laptop Applikationen skickar på
bool continueRunning = true;
NetworkManager networkManager;

void setup() {
    Serial.begin(115200);
    delay(2000);
    Serial.println("Beehive Prototype Start");
    
    // === 2. Initiera vikt- och nätverksmoduler ===
    // weightManager.init();   // Läggs till senare
    // networkManager.init();  // Läggs till senare
    init(udp);
    udpReceiver.begin(ackPort);  // För att ta emot ACK
}

void loop() {
    if(!continueRunning) {
        return;
    }  
    // === 3. Hämta dagens viktvärde ===
    todayWeight = 5000 + random(-50, 50);  // Simulerad vikt
    Serial.print("Today's weight:\t\t");
    Serial.println(todayWeight);

    // === 4. Skicka data till Laptop Applikationen ===
    Serial.println("Attempting to send weight data...");
    packetID++;  // Ökar paket-ID vid varje försök

    outgoingMessage = formatMessageToServer(); 
    // networkManager.sendWeightData(todayWeight, packetID);  // Läggs till senare
    sendWeightData(udp, outgoingMessage);

    // === 5. Vänta på ACK från servern ===
    ackReceived = false;  // Nollställ status
    int attempts = 0;
    char ackBuffer[255]; 
    int packetSize = udpReceiver.parsePacket();
    if (packetSize) {
        udpReceiver.read(ackBuffer, 255);
        ackBuffer[packetSize] = '\0';
        Serial.println("**********************************");
        Serial.print("ACK:\t\t");
        Serial.println(ackBuffer);
        Serial.println("**********************************");
        Serial.print("10 days of honey weight is recieved!");
        Serial.println("Reset Memory");
        Serial.println("**********************************");
        continueRunning = false;
        delay(2000);  // Vänta 10 sekunder för testning
    }      
    
    Serial.println("Sleeping until next cycle...");
    Serial.println("**********************************");
    delay(10000);  // Vänta 10 sekunder för testning
}

String formatMessageToServer() {
    DynamicJsonDocument doc(1024);
    char jsonBuffer[1024];

    doc["ipAddress"] = WiFi.localIP().toString();  // Konverterar IP-adressen till en sträng
    doc["macAddress"] = "1B:B2:16:88:37:Z";
    JsonArray array = doc.createNestedArray("weightValues");
    array.add(2);
    array.add(14);
    array.add(22);
    array.add(33);
    array.add(41);
    array.add(47);

    //String jsonBuffer;
    serializeJson(doc, jsonBuffer);

    return jsonBuffer;
}
