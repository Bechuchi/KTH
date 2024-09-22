#ifndef NETWORK_H
#define NETWORK_H

void initNetwork(WiFiUDP udp);
void sendData(WiFiUDP udp, String data);

#endif
