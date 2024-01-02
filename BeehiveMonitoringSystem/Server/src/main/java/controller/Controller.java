
/*
 * Hanterar inkommande meddelanden och koordinera behandlingen av dem.
 */
public class Controller {
    public UDPListener udpListener;

    public Controller() {
        udpListener = new UDPListener();
    }
    
    /*
     * periodiskt kontrollerar om det har kommit inkommande data från Arduino-klienterna.
     * Detta kan göras med en enkel loop som tittar på en specifik port och väntar på UDP-paket.
     * Om det finns inkommande data, kan du hantera den enligt dina krav.
     */
    public void scanLocalArea() {
        // Implementera kod för att lyssna på inkommande UDP-paket här och hantera dem
        while(true) {

            String incomingMessage = udpListener.collectIncomingMessage();

            if (incomingMessage != NULL) {
                IncomingMessageDTO incomingMessageDTO = formatIncomingMessage(incomingMessage);
                // Register incoming weight to model and/or database, display in View
                ResponseMessageDTO responseMessageDTO 
            }
        }
    }

    private IncomingMessageDTO formatIncomingMessage(String incomingMessage) {
        // Implementera formatering av inkommande meddelande till IncomingMessageDTO
        // och returnera DTO-objektet.
        return new IncomingMessageDTO();
    }

    public String clientCommunication(IncomingMessageDTO incomingMessageDTO) {
        // Implementera logik för att hantera inkommande data från en specifik klient
        // och generera ett utgående meddelande om det är nödvändigt.
        return "Response to " + incomingMessageDTO.getIP() + ": " + incomingMessageDTO.getWeight();
    }
}