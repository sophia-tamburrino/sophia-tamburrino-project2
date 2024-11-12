//client code interacts with the server
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Client {
    String hostname;
    int port;
    Socket clientSocket;

    public Client(String theHost, int thePort) throws IOException{
        this.hostname = theHost;
        this.port = thePort;
        //create new socket?
        try {
            clientSocket = new Socket(hostname, port);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }

        clientSocket = new Socket(hostname, port);
    }

    public Socket getSocket() {
        return clientSocket;
    }

    public String request(String request) {
        return null;
    }

    public void handshake() {

    }

    public void disconnect() {

    }
     
    
}