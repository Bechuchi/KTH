package model;

/*
 * En klass som representerar en ansluten Arduino-klient. Den kan innehålla information om klienten, som dess IP-adress och anslutningsstatus.
 */
public class Client {
    public int IPAddress;
    public boolean isConnected;
    public String messageFromClient;
    public String messageToClient;
}