/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample;

//import Util.Data;
//import Util.Information;
//import Util.NetworkConnection;
import javax.sound.midi.MidiDevice;
import java.util.HashMap;


public class ReaderWriterServer implements Runnable{
    String username;
    NetworkConnection nc;
    HashMap<String,Information> clientList;
    
    public ReaderWriterServer(String user, NetworkConnection netConnection, HashMap<String,Information> cList){
        username=user;
        nc=netConnection;
        clientList=cList;
        
    }
    
    @Override
    public void run() {
        while(true){
            Object obj=nc.read();
            //Data dataObj=(Data)obj;
            String actualMessage=(String)obj;
            String msgs[]=actualMessage.split("\\ ");

            //Information inf;

            if(actualMessage.contains("Position")){
              //  Information inf = new Information();
                //Information inf2 = new Information();
                // if(msgs[7].contains("badhon"))
                Information inf = clientList.get("badhon");
                //else if(msgs[7].contains("alvi"))
                   Information inf2 = clientList.get("saifur");
                //Information inf2 = clientList.get("alvi");
                inf.netConnection.write(actualMessage);
                inf2.netConnection.write(actualMessage);
                System.out.println("Sending "+actualMessage);
            }

        }
        
    }
    
}
