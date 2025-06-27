#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <WiFiUdp.h>
#include "ArduinoJson.h"
#include <EEPROM.h>
#include <HX711.h>

// HX711 circuit wiring
#define LOADCELL_DOUT_PIN 13 // D7
#define LOADCELL_SCK_PIN 12  // D6
HX711 scale;

int packetID = 0;
char jsonBuffer[1024];
String outgoingMessage;
bool continueRunning = true;
long todayWeight = 0;
//Network
WiFiUDP sendSocket;
WiFiUDP recieveSocket;  // Ny UDP-instans för att ta emot ACK
const int ackPort = 9090;
bool ackReceived = false;
const char* ssid = "DESKTOP-JV5DPM1 4709";
const char* password = "2402v?V3";
const int laptopApplicationPort = 8080;
const char* laptopApplicationIP = "192.168.137.1";

//Energy System
bool hasMeasuredToday = false;

struct WeightSensor {
  void initSensor() {
    scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
    scale.set_gain(128);
    scale.set_scale(-21000.0f); //2.3 kg with mark
    scale.tare();
    delay(5000);
    //float weight = scale.get_units(10);
  }

  long getWeightToday() {
    //todayWeight = 50 + random(-50, 50);  // Simulerad vikt
    float measured = scale.get_units(10);
    Serial.print("Today's weight:\t\t");
    Serial.println(measured);
    todayWeight = measured;
    return todayWeight;
  }
};

struct MemoryModule {
  void initEEPROM() {
    EEPROM.begin(512);
    for (int i = 0; i < 512; i++) {
      EEPROM.put(i, 0);  // Nollställ allt
    }
    EEPROM.put(0, 0);    // Starta pekaren vid 0
    EEPROM.commit();
  }

  void getOrderedWeightHistory(int* targetArray) {
    int writeIndex;
    EEPROM.get(0, writeIndex);  // Pekaren till nästa plats

    for (int i = 0; i < 10; i++) {
      int actualIndex = (writeIndex + i) % 10;  // Läser från äldsta till nyaste
      int value;
      EEPROM.get(4 + actualIndex * sizeof(int), value);
      targetArray[i] = value;
    }
  }
  
  void storeValue(long currentWeight) {
      int nextWriteIndex;
  
      // Läs det nuvarande indexet från EEPROM
      EEPROM.get(0, nextWriteIndex);
  
      // Säkerställ att indexet är inom giltigt intervall
      if (nextWriteIndex < 0 || nextWriteIndex > 9) {
          nextWriteIndex = 0;  // Återställ om index är korrupt
      }
  
      // Beräkna EEPROM-adressen där vi ska skriva (börja från vänster)
      int memoryLocation = (nextWriteIndex * sizeof(int)) + sizeof(int);
  
      // Skriv in vikten i EEPROM på rätt plats
      EEPROM.put(memoryLocation, currentWeight);
      EEPROM.commit();  // Viktigt för ESP8266
  
      // Uppdatera pekaren så den flyttas framåt
      nextWriteIndex = (nextWriteIndex + 1) % 10;
      EEPROM.put(0, nextWriteIndex);
      EEPROM.commit();  // Säkerställ att indexet sparas korrekt
  
      delay(3000);
  }
  
  String readMemory() {
      String weightHistory = "";
      int currentWeight;
      int nextWriteIndex;
  
      EEPROM.get(0, nextWriteIndex);
      
      if (nextWriteIndex < 0 || nextWriteIndex > 9) {
          nextWriteIndex = 0; // Återställ om index är korrupt
      }
  
      int startIndex = (nextWriteIndex) % 10; 
  
      for (int i = 0; i < 10; i++) {
          int currentReadLocation = ((startIndex + i) % 10) * sizeof(int) + 4; // Testa att byta ut sizeof(int) mot en fast offset
          EEPROM.get(currentReadLocation, currentWeight);
          
          if (weightHistory.length() > 0) weightHistory += ", ";
         weightHistory += String(currentWeight);
      }
  
     return weightHistory;
  }
  
  void clearMemory(){
      for (int i = 0; i < EEPROM.length(); i++) {
            EEPROM.put(i, 0);
      }
        
      EEPROM.commit();
  }
};

MemoryModule memory;

struct NetworkModule {
  void tryConnect() {
    WiFi.begin(ssid, password);
    
    while (WiFi.status() != WL_CONNECTED) {
      delay(500);
      Serial.print(".");
    }

    Serial.println("\n");
    Serial.println("**********************************");
    Serial.println("Connected to the LAN-network!");
    Serial.println("**********************************");
    //sendSocket.begin(laptopApplicationPort);
  }

  void setNetworkSettings(){
    IPAddress staticIP(192, 168, 137, 100); // Exempel på IP-adress
    IPAddress gateway(192, 168, 137, 2);    // Gateway, din dators hotspot-IP
    IPAddress subnet(255, 255, 255, 0);     // Subnet mask
    WiFi.config(staticIP, gateway, subnet);
  }

  void sendUDPPacket(String data) {
    sendSocket.beginPacket(laptopApplicationIP, laptopApplicationPort);
    sendSocket.write(data.c_str());
    sendSocket.endPacket();
    Serial.println("Data sent: " + data);
  }

  void processACKMessage(WiFiUDP udpReceiver, char ackBuffer[], int packetSize) {
    udpReceiver.read(ackBuffer, 255);
          ackBuffer[packetSize] = '\0';
          Serial.println("**********************************");
          Serial.print("Verification Message:\t\t");
          Serial.println(ackBuffer);
          Serial.println("**********************************");
          delay(2000);  // Vänta 10 sekunder för testning
  }

  bool waitForACK() {
    ackReceived = false;  // Nollställ status
      int attempts = 0;
      char ackBuffer[255]; 
      int packetSize = recieveSocket.parsePacket();
      
      if (packetSize) {
          processACKMessage(recieveSocket, ackBuffer, packetSize);
          memory.clearMemory();
          String weightValues = memory.readMemory();
          Serial.print("Memory:\t\t" + weightValues + "\n");
          //continueRunning = false;
          return true;
      }  
      return false;
  }
};

struct MessageModule {
  String formatUDPMessage(int packetID) {
    DynamicJsonDocument doc(1024);
    char jsonBuffer[1024];

    doc["packetID"] = packetID;
    doc["ipAddress"] = WiFi.localIP().toString();
    doc["macAddress"] = "1B:B2:16:88:37:2";

    // === Hämta viktvärden från EEPROM i rätt ordning ===
    int weights[10];
    memory.getOrderedWeightHistory(weights);

    JsonArray array = doc.createNestedArray("weightValues");
    for (int i = 0; i < 10; i++) {
      array.add(weights[i]);
    }

    // === Skapa JSON-strängen ===
    serializeJson(doc, jsonBuffer);
    return jsonBuffer;
  }
};

NetworkModule network;
WeightSensor sensor;
MessageModule message;

struct ProgramModule {
  void setConfigurations() {
    Serial.begin(115200);
    delay(5000);
    sensor.initSensor();
    memory.initEEPROM();
    delay(5000);
    network.setNetworkSettings();
    sendSocket.begin(laptopApplicationPort);
    recieveSocket.begin(ackPort);
  }

  void readWeight() {
    long todayWeight = sensor.getWeightToday();
    memory.storeValue(todayWeight);  // Testa spara ett dummyvärde
    String weightValues = memory.readMemory();
    Serial.print("Memory:\t\t" + weightValues + "\n");
  }

  bool isNewDay() {
    static unsigned long dayStart = millis();
    return millis() - dayStart > 24UL * 60UL * 60UL * 1000UL; // 24 h
  }
};

ProgramModule program;

void setup() {
  program.setConfigurations();
}

void loop() {
  // Simulerad solenergi – byt ut till riktig kontroll sen
  bool solarPowerAvailable = true;
  if(!continueRunning) {
        return;
    }

  if (solarPowerAvailable && !hasMeasuredToday) {
    program.readWeight();
    hasMeasuredToday = true;
  }

  if (solarPowerAvailable && hasMeasuredToday) {
    packetID++;
    outgoingMessage = message.formatUDPMessage(packetID); 
    network.tryConnect();
    network.sendUDPPacket(outgoingMessage);

    if (network.waitForACK()) {
      continueRunning = false;
    }
  }
  delay(800);
}