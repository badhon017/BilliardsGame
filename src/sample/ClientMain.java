
package sample;

import sample.NetworkConnection;
import sample.Writer;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Scanner;


public class ClientMain {

   // Thread readerThread, writerThread;
    public NetworkConnection nc;

    public ClientMain(String in) throws IOException {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter The IP Address: ");
        String string = input.nextLine();
        nc = new NetworkConnection(string, 12345);
        //nc = new NetworkConnection("127.0.0.1", 12345);
        String username = in;
        nc.write(username);

    }
        public void writeMessage(String msg){
            nc.write(msg);
        }
       
       
    }

