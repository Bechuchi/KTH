#include "Arduino.h"
#include "ArduinoJson.h"
#include "ArduinoJson.h"

#ifndef MOCKEDDATA_H
#define MOCKEDDATA_H

class MockedData {
public:
  void getJSONFormat(DynamicJsonDocument &doc);
  float getMockedWeightData();
  float getMockedTemperatureData();
  float getMockedHumidityData();
};

#endif