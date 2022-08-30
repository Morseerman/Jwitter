import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 12345;

    public static void main(String[] args) throws IOException 
    {
        //Establishes Connection
        Socket socket = new Socket(SERVER_IP, PORT);

        //Gets input from either Server Or Keyboard

        ServerConnection serverConnection = new ServerConnection(socket);

        BufferedReader keyboardInput = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        new Thread(serverConnection).start();

        //Setting Name
        System.out.println("Please Enter Your Name");
        

        while (true)
        {
            System.out.println("> ");
            String userCommand = keyboardInput.readLine();
  
            if (userCommand.equals("quit")) break;
    
            out.println(userCommand);    
            
        }
        

        socket.close();
        System.exit(0);

    }
}
