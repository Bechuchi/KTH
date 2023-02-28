package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    private static int BUFFER_SIZE = 1024;

    public TCPClient() {
    }

    public byte[] askServer(String hostname, int port, byte[] bytesToServer) throws IOException {
        Socket socket = new Socket(hostname, port);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        try {
            sendToServer(bytesToServer, socket);

            byte[] buffer = new byte[BUFFER_SIZE];
            int numBytesRead;
            
            while ((numBytesRead = socket.getInputStream().read(buffer)) != -1) {
                outputStream.write(buffer, 0, numBytesRead);
            } 
        } finally {
        socket.close();
        }

        return outputStream.toByteArray();
    }

    private static void sendToServer(byte [] toServerBytes, Socket socket) throws IOException{
        socket.getOutputStream().write(toServerBytes, 0, toServerBytes.length);
    }
}