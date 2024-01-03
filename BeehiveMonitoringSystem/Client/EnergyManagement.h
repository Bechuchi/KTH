#include "Arduino.h"
#include <avr/sleep.h>
#include <avr/power.h>
#include <avr/wdt.h>

#ifndef ENERGYMANAGEMENT_H
#define ENERGYMANAGEMENT_H

/*
 * The Arduino goes into sleep mode whenever the button is pressed.
 * In the same way it wakes up once the button is pressed again.
*/
class EnergyManagement {
public:
  EnergyManagement(int wakeUpPin, int ledPin);
  void setup();
  void wakeUp();
  void activateArduino();
  void activateSleepMode();
  bool getJustWokeUp();
  void setJustWokeUp(bool status);
  bool getIsInSleepMode();

private:
  int wakeUpPin;
  int ledPin;
  volatile bool isInSleepMode;
  volatile bool justWokeUp;

  static void staticWakeUpWrapper() {
    if (instance) {
      instance->wakeUp();
    }
  }

  static EnergyManagement* instance;
};

#endif