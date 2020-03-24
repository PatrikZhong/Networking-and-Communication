import java.net.*;
import java.io.*;
import tcpclient.TCPClient; 
//This code will start a server through localhost which will generate a GET HTTP query. We then process
//this query and rip out the necessary parts to ask TCPClient what output it gives. The Output is then written 
//to a browser.
//Input: java HTTPAsk 8888 
//Output: Whatever the website outputs to us, through a browser. 

    //http://hostname.domain/ask?hostname=time.nist.gov&port=13
    //GET /ask?hostname=time.nist.gov&port=13 HTTP/1.1
    //GET ask hostname time.nist.gov port 13 HTTP 1.1

public class HTTPAsk {      
    public static void main( String[] args) throws IOException {

        // Your code here
        int portNum = Integer.parseInt(args[0]);
        ServerSocket serverSocket = new ServerSocket(portNum); 


        while(true){


            try{

                String host = null;
                String port = null;
                String input = null; //our input  
                String string = null; //send away to TCP
        
                Socket clientsocket = serverSocket.accept(); //accept ger en socket som är okejad                         
              

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

                clientsocket.close();
              
             } 

             catch(Exception e){
                 
                 System.exit(1);

             }
      
        }
    
    }
}


