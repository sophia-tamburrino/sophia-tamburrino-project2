//server code recieves and processes requests from clients
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Server {
    int port;
    ServerSocket sock;
    ArrayList<LocalDateTime> timeList;

    public Server(int thePort) {
        this.port = thePort;
        try {
            sock = new ServerSocket(port);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        timeList = new ArrayList<>(); //initialize timelist here
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
                    //should be checking here if the key isnt 12345
                    //System.out.println("gets to before accept");
                    Socket clientSocket = sock.accept(); //connects here
                    LocalDateTime connectedTime = LocalDateTime.now();
                    timeList.add(connectedTime);

                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
                    BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    //System.out.println("gets to after bufferedreader in server");

                    String reply = in.readLine();//read a line from ther server
                    if(reply.equals("12345")) {
                        //System.out.println("gets to reply is 12345");
                        //System.out.println("New connection: "+ clientSocket.getRemoteSocketAddress());
                        //start the thread
                        (new Handler(clientSocket)).start();
                    }
                    else {
                        //couldnt handshake
                        //System.out.println("gets to cant handshake");
                        out.println("couldn't handshake");
                        out.flush();
                        out.close();
                        break;
                    }
                    
                    //continue looping
                } catch(Exception e) {
                    System.out.println(e);
                } 
            }
    }

    //currently writing the clienthandler
    // this method will process the client request in a separate thread so that the server can continue to 
    // accept connections while these expensive factorization calculations are being performed on behalf of various clients

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
                
                //I think we get the message from here and then send back the calculation 
                while(true) {
                    String request = in.readLine();
                    //System.out.println(request); 
                    if(request == null) {
                        //no more request  
                        break;
                    }
                    else {
                        //FACTORIZES 
                        int num;
                        int primeFactors = 0;
                        String line = "";
                        
                        //NUMBER TOO LONG
                        if(request.length() > 10) {
                            out.println("There was an exception on the server");
                            out.flush();
                            break;
                        }

                        num = Integer.parseInt(request);

                        //won't ever hit 0, starts at the number and decreases until it hits 1.
                        for(int i = num; i > 0; i--) {
                            //if it is evenly divisible, then it is a factor
                            if(num % i == 0) {
                                primeFactors++;
                            }
                        }
                
                        line = "The number " + request + " has " + primeFactors + " factors";
                        out.println(line);
                        out.flush();
                    }
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
    

    //The server should record the time each client was connected so that it can properly return these values with the getConnectedTimes method, 
    //which should return a sorted ArrayList of LocalDateTime objects representing the connection time of every client
    public ArrayList<LocalDateTime> getConnectedTimes() {
        //will be already sorted as the timestamps are added in order
        return timeList;
    }
    
}

