package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;



public class ServerConnection implements Runnable
{

    private Socket server;
    private BufferedReader in;
    

    public ServerConnection(Socket s) throws IOException
    {
        server = s;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));        
    }

    @Override
    public void run() 
    {
        String serverResponse;
        try 
        {
            while (true)
            {
                serverResponse = in.readLine();
                if (serverResponse == null) break;


                System.out.println("[SERVER] " + serverResponse);
            }            

        } catch (IOException e) 
        {            
            e.printStackTrace();
        }finally
        {
            try 
            {
                in.close();

            } catch (IOException e) 
            {                
                e.printStackTrace();
            }
        }

    }
    
}
