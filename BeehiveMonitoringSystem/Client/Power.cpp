/***********
* Sleep Mode
***********/
volatile bool isAsleep = false; // Håller reda på om Arduino är i sleep mode
volatile bool justWokeUp = false;

void wakeUp() {
  justWokeUp = true; // Sätt flaggan att Arduino precis har vaknat
}

void activateSleepMode() {
  isAsleep = true;
  Serial.println("Going to sleep mode...");
  Serial.flush();
  delay(1000); // Öka om nödvändigt

  digitalWrite(ledPin, LOW);
  set_sleep_mode(SLEEP_MODE_PWR_DOWN);
  sleep_enable();
  sleep_mode();

  // När Arduino vaknar, ställ in LED-pinnen tillbaka till dess föregående tillstånd
  digitalWrite(ledPin, digitalRead(wakeUpPin) == HIGH ? HIGH : LOW);
}