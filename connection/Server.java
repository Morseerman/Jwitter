package connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;



public class Server {
    
    private static final int PORT = 12345;

    private static ArrayList<ClientHandler> clients = new ArrayList<ClientHandler>();
    private static ExecutorService pool = Executors.newFixedThreadPool(10);
    
    public static void main(String[] args) throws IOException
    {
        ServerSocket connector = new ServerSocket(PORT);

        while(true)
        {
            System.out.println("[SERVER] Waiting for client connection...");
            Socket client = connector.accept();
            System.out.println("[SERVER] Connected Successfully"); 

            ClientHandler thread = new ClientHandler(client, clients);
            clients.add(thread); 
            pool.execute(thread);             
                        
        }
        
       
                    
               
                
    }   

    public static String getDateAndTime()
    {
        String t = LocalTime.now().toString();        
        String d = LocalDate.now().toString();
        
        String dt = ("{" + d + "} " + "{" + t.substring(0, 5) + "} ");
        return dt;

    }


}