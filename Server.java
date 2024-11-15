//server code recieves and processes requests from clients
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Server {
    int port;
    ServerSocket sock;

    public Server(int thePort) {
        this.port = thePort;
        try {
            sock = new ServerSocket(port);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    public void disconnect() {
        try {
            sock.close();
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }

    //bulk of code seems to be here
    public void serve(int i) {
        //if message 12345, accept, then start thread
        //if not, disconnect
        //i = num requests
            for(int j = 0; j < i; j++){
                //from website
                try{
                    //accept incoming connection
                    Socket clientSocket = sock.accept();
                    System.out.println("New connection: "+ clientSocket.getRemoteSocketAddress());
                    
                    //start the thread
                    (new ClientHandler(clientSocket)).start();
                    
                    //continue looping
                } catch(Exception e)
                {
                    System.out.println(e);
                } //exit serve if exception
            }
    }

    //from website
    //currently writing the clienthandler--

    //implement later
    public ArrayList getConnectedTimes() {

        return null;
    }
    
}

