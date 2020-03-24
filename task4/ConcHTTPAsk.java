import java.net.*;
import java.io.*;



public class ConcHTTPAsk{
    public static void main(String args[]) throws IOException{
        ServerSocket sSocket = new ServerSocket(Integer.parseInt(args[0]));

        while(true){
            Socket clientSocket = sSocket.accept();
            Runnable r = new Thread(new MyRunnable(clientSocket));
            new Thread(r).start();
            
        }   

    }
}