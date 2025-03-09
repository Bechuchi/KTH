#ifndef NETWORKMANAGER_H
#define NETWORKMANAGER_H

#include <Arduino.h>
#include "WiFiUdp.h"

class NetworkManager {
  public:  
      void init(WiFiUDP udp);
      void sendWeightData(WiFiUDP udp, String data);
};

#endif
