package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient
{
    private static int SIZE_STATIC_BUFFER = 1024;
    private Socket socket = null;
    private boolean shutdown = false;
    private Integer timeout = null;
    private Integer dataLimit = null;
    
    public TCPClient(boolean inputShutdown, Integer inputTimeout, Integer inputDataLimit)
    {
        this.shutdown = inputShutdown;
        this.timeout = inputTimeout;
        this.dataLimit = inputDataLimit;
    }

    public byte[] askServer(String hostname, int port, byte [] bytesToServer) throws IOException 
    {
        this.socket = new Socket(hostname, port);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[SIZE_STATIC_BUFFER];

        if(bytesToServer.length != 0) {
            writeToServer(bytesToServer);
            setupShutdown();
        }
        
        try {
            setupTimeout();

            int numBytesRead;
            int dynamicSum = 0;

            while ((numBytesRead = socket.getInputStream().read(buffer)) != -1) 
            {
                if(hasDataLimitDemand() && hasReachedLimit(numBytesRead, dynamicSum)) {                
                    int remainingBytes = dataLimit - dynamicSum;
                    outputStream.write(buffer, 0, remainingBytes);
                    break;
                } else {
                    outputStream.write(buffer, 0, numBytesRead);
                    dynamicSum += numBytesRead;
                }
            }   
        } catch(SocketTimeoutException ex) {
            //Handle timeout exception
        } finally {    
            closeCommunication();
        }

        return outputStream.toByteArray();
    }

    private void closeCommunication() throws IOException {
        socket.close();
    }

    private void writeToServer(byte[] bytesToServer) throws IOException {
        socket.getOutputStream().write(bytesToServer, 0, bytesToServer.length);
    }

    /*********/
    //Shutdown
    /*********/
    private void setupShutdown() throws IOException {
        if(isShutdown()) {
            shutdownCurrentOutput();
        }
    }

    private boolean isShutdown() {
        return this.shutdown;
    }

    private void shutdownCurrentOutput() throws IOException {
        socket.shutdownOutput();
    }

    /*********/
    //Timeout
    /*********/
    private void setupTimeout() throws IOException {
        if(hasTimeoutDemand()) {
            activateForPossibleTimeout();
        } 
    }

    private boolean hasTimeoutDemand() {
        return this.timeout != null;
    }

    private void activateForPossibleTimeout() throws IOException {
        socket.setSoTimeout(this.timeout);
    }

    /*********/
    //Limit
    /*********/
    private boolean hasDataLimitDemand() {
        return this.dataLimit != null;
    }

    private boolean hasReachedLimit(int numBytesRead, int dynamicSum) throws IOException {
        return (dynamicSum + numBytesRead) > dataLimit;
    }
}
