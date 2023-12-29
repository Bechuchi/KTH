#include "EnergyManagement.h"

EnergyManagement* EnergyManagement::instance = nullptr;

EnergyManagement::EnergyManagement(int wakeUpPin, int ledPin) {
  this->wakeUpPin = wakeUpPin;
  this->ledPin = ledPin;
  isInSleepMode = false;
  justWokeUp = false;
  instance = this;
}

void EnergyManagement::setup() {
  pinMode(wakeUpPin, INPUT_PULLUP);
  pinMode(ledPin, OUTPUT);
  attachInterrupt(digitalPinToInterrupt(wakeUpPin), staticWakeUpWrapper, CHANGE);
  digitalWrite(ledPin, HIGH);
}

void EnergyManagement::activateArduino() {
    isInSleepMode = false;
    Serial.println("Woke up! :)");
    digitalWrite(ledPin, HIGH);
}

void EnergyManagement::activateSleepMode() {
    Serial.println("Going to sleep mode...");
    Serial.flush();
    digitalWrite(ledPin, LOW);
    delay(100);
    isInSleepMode = true;
    set_sleep_mode(SLEEP_MODE_PWR_DOWN);
    sleep_enable();
    sleep_mode();
}


bool EnergyManagement::getIsInSleepMode() {
    return isInSleepMode;
}

void EnergyManagement::wakeUp() {
  justWokeUp = true;
}

bool EnergyManagement::getJustWokeUp() {
    return justWokeUp;
}

void EnergyManagement::setJustWokeUp(bool status) {
    justWokeUp = status;
}