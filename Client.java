//client code interacts with the server
import java.util.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class Client {
    String hostname;
    int port;
    Socket currentSock;

    public Client(String theHost, int thePort) throws IOException {
        this.hostname = theHost;
        this.port = thePort;
        //create new socket?
        try {
            currentSock = new Socket(hostname, port);
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        

    }

    public Socket getSocket() {
        return currentSock;
    }

    public String request(String request) {
        //calls after handshake is done
        //sneding string to server and read it 
        //must factorize the number and return a string with how many factors the number has
        //Ex. if "17", then must return the number of factors it has, which is 2.
        //loop through length of number
        int num;
        int primeFactors = 0;
        //won't ever hit 0, starts at the number and decreases until it hits 1.
        try {
            num = Integer.parseInt(request);
        } catch (Exception e) {
            // TODO: handle exception
            return "There was an exception on the server";
        }
    
        for(int i = num; i > 0; i--) {
            //if it is evenly divisible, then it is a factor
            if(num % i == 0) {
                primeFactors++;
            }
        }

        return "The number " + request + " has " + primeFactors + " factors";
    }

    public void handshake() throws IOException {
        //connects the client to the server
        //getoutputstream, and then get printwriter
        //first message send key, 12345 to socket
        try{
            PrintWriter printWriter = new PrintWriter(currentSock.getOutputStream());
            printWriter.println("12345");
            printWriter.close(); 
            currentSock.close();
        }catch(Exception e){
            System.err.print(e);
            System.exit(1);
        }
    }

    public void disconnect() throws IOException{
        try{
            currentSock.close();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
     
    
}