#include <SoftwareSerial.h>

SoftwareSerial esp8266(10, 11); // RX, TX


void setup() {
  Serial.begin(115200);       // Starta seriell kommunikation med Serial Monitor
  esp8266.begin(115200);      // Starta kommunikation med ESP8266

  Serial.println("Skickar AT till ESP8266");
  esp8266.println("AT");      // Skicka AT-kommandot till ESP8266
}

void loop() {
  if (esp8266.available()) {  // Kontrollera om ESP8266 har skickat ett svar
    Serial.write(esp8266.read()); // Skriv ut vad ESP8266 skickar till Serial Monitor
  }

  if (Serial.available()) {   // Kontrollera om det finns data skickat till Serial Monitor
    esp8266.write(Serial.read()); // Skicka det till ESP8266
  }
}
