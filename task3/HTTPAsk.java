import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

enum ServerStatus
{
    OK {
        public String toString() {
          return "HTTP/1.1 200 OK \r\n\r\n";
        }
     },

     BAD_REQUEST {
        public String toString() {
          return "HTTP/1.1 400 Bad Request \r\n";
        }
     },

     NOT_FOUND {
        public String toString() {
          return "HTTP/1.1 404 Not Found \r\n";
        }
     };
}

public class HTTPAsk
{
    private static int SIZE_STATIC_BUFFER = 1024;

    private static boolean shutdown = false;
    private static Integer timeout = null;
    private static Integer limit = null;
    
    private static String host = null;
    private static int port = 0;
    private static String serverStatus = null;
    private static String dataContent = "";

    private static InputStream input = null; 
    private static OutputStream output = null;

    public static void main(String[] args) throws IOException
    {
        port = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(port);

        while(true)
        {
            Socket clientSocket = connectionSetup(serverSocket);
            streamSetup(clientSocket);

            byte[] fromClientBuffer = new byte[SIZE_STATIC_BUFFER];
            int outputLength = input.read(fromClientBuffer);
        
            while(outputLength != -1)
            {
                String decoder = new String(fromClientBuffer, 0, outputLength);
                String[] serverOutput = decoder.split("[?&= ]", 10);

                if(isValidValidRequest(decoder, serverOutput))
                {
                    setServerStatus(ServerStatus.OK);

                    for(int i=0; i<serverOutput.length; i++)
                    {
                        if(isPartOfServerOutput(serverOutput, i, "hostname")) {
                            setResponseParameter(serverOutput, i, "hostname");
                        }
                        else if(isPartOfServerOutput(serverOutput, i, "port"))
                        {
                            setResponseParameter(serverOutput, i, "port");
                        }
                        else if(isPartOfServerOutput(serverOutput, i, "string"))
                        {
                            setResponseParameter(serverOutput, i, "string");
                        }
                        else if(isPartOfServerOutput(serverOutput, i, "shutdown")) {
                            setResponseParameter(serverOutput, i, "shutdown");
                        }
                        else if(isPartOfServerOutput(serverOutput, i, "limit"))
                        {
                            setResponseParameter(serverOutput, i, "limit");
                        }
                        else if(isPartOfServerOutput(serverOutput, i, "timeout"))
                        {
                            setResponseParameter(serverOutput, i, "timeout");
                        }
                    }
                    
                    browseServerStatus();
                }
                else { 
                    setServerStatus(ServerStatus.BAD_REQUEST);
                }
                break;
            }

            if(isSuccessfulConnection())
            {
				try {
					byte[] toServerBytes = dataContent.getBytes("UTF-8");
					TCPClient tcpClient = new TCPClient(shutdown, timeout, limit);
					byte[] screenOutput = tcpClient.askServer(host, port, toServerBytes);
					browseHTTPResponse(screenOutput);
				} catch (IOException ex) {
					setServerStatus(ServerStatus.NOT_FOUND); //if no hostname or hostname not recognized
					browseServerStatus();
				}
			} 
            else { 
                browseServerStatus();
            }

            clientSocket.close();
        }
    }

    public static Socket connectionSetup(ServerSocket welcomeSocket) throws IOException
    {
        log("Waiting for client to connect...");
        Socket connectionSocket = welcomeSocket.accept();
        log("Connected!\n");

        return connectionSocket;
    }

    public static void streamSetup(Socket connectionSocket) throws IOException
    {
        input = connectionSocket.getInputStream(); 
        output = connectionSocket.getOutputStream();
    }
    
    public static void log(String text)
    {
        System.out.println(text);
    }
    
    public static void browseServerStatus() throws IOException
    {
        output.write(serverStatus.getBytes("UTF-8"));
    }
    
    public static void browseHTTPResponse(byte[] currentOutput) throws IOException
    {
        output.write(currentOutput);
    }

    public static boolean isValidValidRequest(String stringDecoder, String[] currentStringSplit)
    {
        boolean isGETRequest = currentStringSplit[0].equals("GET");
        boolean containsAsk = currentStringSplit[1].equals("/ask");
        boolean containsHTTP = stringDecoder.contains("HTTP/1.1");

        return isGETRequest && containsAsk && containsHTTP;
    }
    
    public static void setServerStatus(ServerStatus currentStatus)
    {
        serverStatus = currentStatus.toString();
    }
    
    public static void setResponseParameter(String[] serverOutput, int index, String currentParameter)
    {
        switch(currentParameter)
        {
            case "hostname":
                host = serverOutput[index+1];
                break;
            case "port":
                port = Integer.parseInt(serverOutput[index+1]);
                break;
            case "string":
                dataContent = serverOutput[index+1];
                break;
            case "shutdown":
                shutdown = Boolean.parseBoolean(serverOutput[index+1]);
                break;
            case "limit":
                limit = Integer.parseInt(serverOutput[index+1]);
                break;
            case "timeout":
                timeout = Integer.parseInt(serverOutput[index+1]);
                break;
        }
    }
    
    public static boolean isPartOfServerOutput(String[] serverOutput, int currentIndex, String currentParameter)
    {
        return serverOutput[currentIndex].equals(currentParameter);
    }

    public static boolean isSuccessfulConnection()
    {
        return !(serverStatus.contains(ServerStatus.BAD_REQUEST.toString())) &&
            !(serverStatus.contains(ServerStatus.NOT_FOUND.toString()));
    }
}


