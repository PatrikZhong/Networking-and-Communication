package tcpclient;
import java.net.*;
import java.io.*;

public class TCPClient {
    
    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
        
        if(ToServer == null){
            return askServer(hostname, port);
        }

        int maxsize  = 1024;
        Socket clientSocket = new Socket(hostname, port); //create socket     
        clientSocket.setSoTimeout(1000); //timeout after 1000ms
  
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());

        StringBuilder sb = new StringBuilder();   //ta emot meddelandet med denna      
        String s = null; //börja tomt, öka på och läs på raderna med readLine
        int counter = 0;

        toServer.writeBytes(ToServer + '\n');
        
        try{
        while (((s = fromServer.readLine()) != null) && (counter <= maxsize)){
                sb.append(s + '\n');
                counter++; 
            }      
        }

       catch(IOException e){    
       }
       
       clientSocket.close(); //stäng skiten
       return sb.toString();
  
    }

    public static String askServer(String hostname, int port) throws  IOException {
        int maxsize  = 1024;
        Socket clientSocket = new Socket(hostname, port); //creat socket     
        clientSocket.setSoTimeout(1000); //timeout after 1000ms
  
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        StringBuilder sb = new StringBuilder();   //ta emot meddelandet med denna      
        String s = null; //börja tomt, öka på och läs på raderna med readLine
        int counter = 0;
        
        try{
        while(((s = fromServer.readLine()) != null) && (counter <= maxsize)){
                sb.append(s + '\n');
                counter++;  

            }
            clientSocket.close();
            return sb.toString();

        }

       catch(IOException e){
           clientSocket.close(); //stäng skiten
           return sb.toString();
       }
  
       
           
       }
    }

 


