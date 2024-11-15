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
            for(int j = 0; j < i; j++) {
                //from website
                try{
                    //accept incoming connection
                    
                    Socket clientSocket = sock.accept();
                    System.out.println("New connection: "+ clientSocket.getRemoteSocketAddress());
                    
                    //start the thread
                    (new Handler(clientSocket)).start();
                    
                    //continue looping
                } catch(Exception e) {
                    System.out.println(e);
                } 
            }
    }

    //currently writing the clienthandler--
    private class Handler extends Thread {
        Socket handlerSock;
        public Handler(Socket theSock) {
            this.handlerSock = theSock;
        }

        public void run() {
            PrintWriter out;
            BufferedReader in;
            try {
                //reads output from client server
                out = new PrintWriter(handlerSock.getOutputStream());
                in = new BufferedReader(new InputStreamReader(handlerSock.getInputStream()));
                
                //don't know if this is what we want,
                while(true) {
                    String message = in.readLine();
                    if(message == null) {
                        //no more input 
                        break;
                    }
                    //print out the message, then flush it out and grab new message
                    out.println(message);
                    out.flush();
                }

                out.close();
                in.close();
                handlerSock.close();
            } catch (Exception e) {
                // TODO: handle exception
                System.out.println("The connection was lost");
            }
        }



    }
    

    //implement later
    public ArrayList getConnectedTimes() {

        return null;
    }
    
}

