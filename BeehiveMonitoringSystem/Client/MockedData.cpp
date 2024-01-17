#include "MockedData.h"

void MockedData::getJSONFormat(DynamicJsonDocument &doc) {
    doc["IP Address: "] = "192.168.1.11";
    doc["Weight: "] = getMockedWeightData();
    doc["Temperature: "] = getMockedTemperatureData();
    doc["Humidity: "] = getMockedHumidityData();
}

float MockedData::getMockedWeightData() {
    return random(1000, 5000) / 100.0; // vikt mellan 10.00 och 50.00 kg
}

float MockedData::getMockedTemperatureData() {
    return random(150, 300) / 10.0; // temperatur mellan 15.0 och 30.0 grader Celsius
}

float MockedData::getMockedHumidityData() {
    return random(300, 800) / 10.0; // fuktighet mellan 30.0% och 80.0%
}
