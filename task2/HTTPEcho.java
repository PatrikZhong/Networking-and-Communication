import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) {

        // Your code here
        int portNum = Integer.parseInt(args[0]);
       
        String echoThis;
        
        try{

            while(true){

                ServerSocket serverSocket = new ServerSocket(portNum); 
                Socket clientsocket = serverSocket.accept(); //accept ger en socket som är okejad
                         
                StringBuilder sb = new StringBuilder(); //lägg saker här efter att buffert är död


                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                // DataOutputStream outToClient = new DataOutputStream(new DataOutputStream(clientsocket.getOutputStream()));
                OutputStream outToClient = clientsocket.getOutputStream();


                sb.append("HTTP/1.1 200 OK\r\n\r\n");
                echoThis = fromClient.readLine(); //lägg det som finns i readLine buffert, döda sedan raden

                while(echoThis != null && !echoThis.isEmpty()){
                    sb.append(echoThis + "\r\n");  
                    echoThis = fromClient.readLine();
                
                }
        
                outToClient.write(sb.toString().getBytes());            
                serverSocket.close();
            }
        }
           
      
        catch(IOException e){
            System.out.println("IOexception");
           
        }
        

    }
        

}


