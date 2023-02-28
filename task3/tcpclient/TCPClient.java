package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient
{
    private static int SIZE_STATIC_BUFFER = 1024;
    private Socket clientSocket = null;
    private InputStream inputStream = null;
    private ByteArrayOutputStream outputStream = null;
    private boolean currentShutdown = false;
    private Integer currentTimeout = null;
    private Integer currentLimit = null;
    
    public TCPClient(boolean shutdown, Integer timeout, Integer limit)
    {
        this.currentShutdown = shutdown;
        this.currentTimeout = timeout;
        this.currentLimit = limit;
    }

    public byte[] askServer(String hostname, int port, byte [] toServerBytes) throws IOException
    {
        initCommunication(hostname, port);
        byte[] data = setStaticBuffer();

        if(toServerBytes.length != 0)
        {
            writeToServer(toServerBytes);
            setupShutdown();
        }
        
        try
        {
            setupTimeout();

            int lengthOfServer = getServerLength(data);

            while(lengthOfServer != -1)
            {
                if(hasDataLimitDemand() && hasReachedDataLimit(data))
                {
                    makeDataLimitResult(data);
                    break;
                }

                outputStream.write(data, 0, lengthOfServer);
                lengthOfServer = inputStream.read(data);
            }   
        } catch(SocketTimeoutException currentException) {

        }
        finally {    
            clientSocket.close();
            
            return outputStream.toByteArray();
        }
    }

    /*****************************************************/
    //Setup between client and server communication
    /*****************************************************/
    public void initCommunication(String currentHostname, int currentPort) throws IOException
    {
        this.clientSocket = new Socket(currentHostname, currentPort);
        this.inputStream = clientSocket.getInputStream();
        this.outputStream = new ByteArrayOutputStream();
    }

    public byte[] setStaticBuffer()
    {
        return new byte[SIZE_STATIC_BUFFER];
    }

    public void writeToServer(byte[] toServerBytes) throws IOException
    {
        clientSocket.getOutputStream().write(toServerBytes, 0, toServerBytes.length);
    }

    public int getServerLength(byte[] currentData) throws IOException
    {
        return inputStream.read(currentData);
    }

    /*****************************************************/
    //Added ways to close the ongoing socket communication
    /*****************************************************/
    /*********/
    //Shutdown
    /*********/
    public void setupShutdown() throws IOException
    {
        if(isShutdown())
        {
            shutdownCurrentOutput();
        }
    }

    public boolean isShutdown()
    {
        return this.currentShutdown;
    }

    public void shutdownCurrentOutput() throws IOException
    {
        clientSocket.shutdownOutput();
    }

    /*********/
    //Timeout
    /*********/
    public void setupTimeout() throws IOException
    {
        if(hasTimeoutDemand())
        {
            activateForPossibleTimeout();
        } 
    }

    public boolean hasTimeoutDemand()
    {
        return this.currentTimeout != null;
    }

    public void activateForPossibleTimeout() throws IOException
    {
        clientSocket.setSoTimeout(this.currentTimeout);
    }

    /*********/
    //Limit
    /*********/
    public boolean hasReachedDataLimit(byte[] currentData) throws IOException
    {
        int currentDynamicSum = getDynamicSum(currentData);

        return currentDynamicSum > this.currentLimit;
    }

    public boolean hasDataLimitDemand()
    {
        return this.currentLimit != null;
    }

    public int getDynamicSum(byte[] currentData) throws IOException
    {
        int sizeOutputStream = outputStream.size();
        int serverLenght = getServerLength(currentData);

        return sizeOutputStream + serverLenght;
    }

    public void makeDataLimitResult(byte[] currentData)
    {
        outputStream.write(currentData, 0, this.currentLimit % SIZE_STATIC_BUFFER);
    }
}
