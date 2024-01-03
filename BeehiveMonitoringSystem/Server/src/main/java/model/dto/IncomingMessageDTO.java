package model.dto;

/*
 * IncomingMessageDTO-klass om du använder JSON-format för att skicka data från klienten till servern.
 * Denna klass kan användas för att strukturera och enkelt bearbeta inkommande meddelanden.
 * Det kan innehålla olika fält för olika delar av meddelandet, t.ex. IP-adress, viktdata och eventuella
 * andra attribut. Detta hjälper till att organisera och hantera inkommande data på ett mer strukturerat sätt.
 */
public class IncomingMessageDTO {
    public String IPAddress;
    public float weight;

    public IncomingMessageDTO(String message) {

    }
}