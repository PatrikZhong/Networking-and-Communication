import java.net.*;
import java.io.*;
import tcpclient.TCPClient;

public class MyRunnable implements Runnable {


    Socket clientSocket;

    public MyRunnable(Socket socket){
    clientSocket = socket;

    }

    public void run(){

    while(true){


        try{

            String host = null;
            String port = null;
            String input = null; //our input  
            String string = null; //send away to TCP          

            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(new DataOutputStream(clientsocket.getOutputStream()));
       
         
            input = fromClient.readLine();
            String[] partition = input.split("[/?&= ]");

            //Maybe check if input is okay

            for(int i = 0; i< partition.length; i++){
                if(partition[i].equals("hostname")){ //om jag hittar "hostname" när jag iterar, så är nästa index mitt hostname
                host = partition[i+1];
                // System.out.println(host);
                }
                else if(partition[i].equals("port")){
                port = partition[i+1];
                // System.out.println(port);
                }
                else if(partition[i].equals("string")){
                string = partition[i+1];
                // System.out.println(string);
                }
            }

            if(partition[2].equals("ask") && host != null && port != null){

                try{
                    String output = TCPClient.askServer(host, Integer.parseInt(port), string);
                    // for(int i = 0; i < partition.length; i++){
                    //     System.out.println(partition[i] + " " + i);
                    // }
                    outToClient.writeBytes("HTTP/1.1 200 OK \r\n\r\n" + output);
                    
                }
                catch(Exception e){
                    outToClient.writeBytes("HTTP/1.1 404 Not Found\r\n");

                }
             
            }
            else{
                outToClient.writeBytes("HTTP/1.1 400 Bad Request\r\n\r\n" );                    
            }  

            clientSocket.close();
          
         } 

         catch(Exception e){
             
             System.exit(1);

         }
  
    }
    }




}