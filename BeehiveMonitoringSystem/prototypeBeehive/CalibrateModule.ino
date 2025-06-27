#include "HX711.h"

// HX711 circuit wiring
#define LOADCELL_DOUT_PIN 13 // D7
#define LOADCELL_SCK_PIN 12  // D6

HX711 scale;

void setup() {
  Serial.begin(115200);
  delay(5000);
  Serial.println("HX711 Demo");
  delay(5000);
  Serial.println("Initializing the scale");

  scale.begin(LOADCELL_DOUT_PIN, LOADCELL_SCK_PIN);
  scale.set_gain(128);

  // === MANUELL CALIBRATION TEST ===
  //scale.set_scale(5500.0f);  // Börja prova här
  //scale.set_scale(-14000.0f);
  //scale.set_scale(-17000.0f);
  //scale.set_scale(-20000.0f); //2.4 kg
  //scale.set_scale(-23000.0f); //2.6 kg
  //scale.set_scale(-18500.0f); //2.5 kg
  //scale.set_scale(-13000.0f); //3.7 kg
  scale.set_scale(-21000.0f); //2.3 kg with mark
  //scale.set_scale(-900.0f); //38.5 kg 
  //scale.set_scale(-1000.0f); 33kg
  //scale.set_scale(-3000.0f); -79
  //scale.set_scale(-5000.0f);
  scale.tare();
  Serial.println("Tare done. Place known weight on the scale.");
  delay(5000);

  float weight = scale.get_units(10);
  Serial.print("Measured weight: ");
  Serial.print(weight);
  Serial.println(" kg (or chosen unit)");
}

void loop() {
  Serial.print("one reading:\t");
  Serial.print(scale.get_units(), 1);
  Serial.print("\t| average:\t");
  Serial.println(scale.get_units(10), 1);
//  scale.power_down();             // put the ADC in sleep mode
  delay(2000);
//  scale.power_up();
}