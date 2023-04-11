package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
//import org.json.simple.*;



public class ClientHandler implements Runnable {

    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<ClientHandler> clients;    
    private HashSet<ClientHandler> following = new HashSet<ClientHandler>();
    public HashSet<ClientHandler> followers = new HashSet<ClientHandler>();
    

    private String name;
    private boolean canFollow;


    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clients) throws IOException
    {
        this.client = clientSocket;
        this.clients = clients;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }  

    @Override
    public void run() 
    {              
        try
        {
            setName(in.readLine());

            //  START MENU
            out.println("     ~~~~ Menu ~~~~\n ");
            out.println("  1) 'username' shows username ");
            out.println("  2) 'follow' follows another user ");
            out.println("  3) 'unfollow' unfollows a user ");
            out.println("  4) 'following' displays who you're following ");
            out.println("  5) 'followers' displays followers ");
            out.println("  6) 'say _____' sends a message to all your followers ");
           
            readFile();

            while(true)
            {
                int userInvalidCounter;
                
                
                String request = in.readLine();

                //Testing Strength
                if(request.contains("test"))
                {
                    out.println("Enter Test Message");
                    String message = "[TEST] " + in.readLine();
                    for (int i = 0; i < 1000; i++)
                    {
                        outToAll(message);
                    }
                }
                else if (request.startsWith("spammer"))
                {
                    int firstSpace = request.indexOf(" ");
                    if (firstSpace != -1)
                    {
                        for (int i = 0; i < 100; i++)
                        {
                            outToFollowers(request.substring(firstSpace + 1));
                            try 
                            {
                                Thread.sleep(1000);

                            } catch (Exception e) 
                            {
                                return;
                            }
                        }
                        outToFollowers("Done");
                        
                    }
                }
                //Sends messsage to followers
                else if(request.startsWith("say"))
                {
                    int firstSpace = request.indexOf(" ");
                    if (firstSpace != -1)
                    {
                        outToFollowers(request.substring(firstSpace + 1));
                    }
                    try 
                    {
                        Thread.sleep(1000);
                    } catch (Exception e) 
                    {
                        return;
                    }
                }
                 else if(request.startsWith("all say"))
                {
                    int firstSpace = request.indexOf(" ");
                    if (firstSpace != -1)
                    {
                        for (ClientHandler client : clients) 
                        {
                            client.outToFollowers(request.substring(firstSpace + 1));
                        }
                        
                    }
                    try 
                    {
                        Thread.sleep(1000);
                    } catch (Exception e) 
                    {
                        return;
                    }
                }
                //Outputs your username
                else if(request.contains("username"))           
                {
                    out.println("Your username is: " + getName());
                }
                //Follow Users
                else if(request.equals("follow"))           
                {
                    out.println("Who would you like to follow?\n");
                    for (ClientHandler client: clients) 
                    {
                        out.println(client.getName());
                    }
                    
                    canFollow = true;
                    userInvalidCounter = 1;
                    String userInputClient = in.readLine();
                    if (userInputClient.equals(name)) //Stops from following yourself
                    {
                        out.println("You can't follow yourself..");
                        canFollow = false;
                        userInvalidCounter = -1;
                    }
                    
                    
                    for (ClientHandler client: clients) 
                    {   
                                                              
                        if (client.getName().equals(userInputClient) && canFollow == true)
                        {
                            out.println("You're now following " + client.getName());                            
                            follow(client);
                            addFollowers(client);                            
                        }
                        else //If you follow a non-existant user
                        {
                            
                            if (userInvalidCounter == clients.size())
                            {
                                out.println("User does not exist");
                            }
                            userInvalidCounter++;
                        }
                        
                    }
                    

                }
                //Unfollow Users
                else if(request.equals("unfollow"))           
                {
                    out.println("Who would you like to unfollow?\n");
                    for (ClientHandler client: following) 
                    {
                        out.println(client.getName());
                    }
                    
                    String userInputClient = in.readLine();
                    try 
                    {
                            userInvalidCounter = 1;
                        for (ClientHandler client: following) 
                        {                              
                            if (client.getName().equals(userInputClient))
                            {
                                out.println("You unfollowed " + client.getName());                            
                                unFollow(client);    
                                removeFollowers(client);                        
                            }
                            else //If you follow a non-existant user
                            {
                                
                                if (userInvalidCounter == following.size())
                                {
                                    out.println("You're not following this user");
                                }
                                userInvalidCounter++;
                            }
                        
                        }
                    } catch (Exception e) 
                    {
                        System.out.println("pain");
                    }
                    

                }
                //Display Followers
                else if (request.equals("following"))   
                {
                    getFollowing();
                }
                else if (request.equals("followers"))   
                {
                    getFollowers();
                }
                else if (request.equals("logs"))
                {
                    readFile();
                }            
                else
                {
                    out.println("Invalid command \n");
                }                                      
                              
            }
        }catch (IOException e)
        {
            System.err.println("IO exception in client handler");
            System.err.println(e.getStackTrace());
        }finally        
        {
            System.out.println("[SERVER] Server Closing");
            out.close();
            try {
                in.close();
            } catch (IOException e)
            {                
                e.printStackTrace();
            }
        }   

    }

    public void setName(String name)
    {        
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    public void follow(ClientHandler user)
    {
        following.add(user);
    }
    public void unFollow(ClientHandler user)
    {
        following.remove(user);
    }
    
    public void getFollowing()
    {
        out.println("You're following: ");
        for (ClientHandler followed : following)
        {
            out.println(followed.getName());
        }
    }

    public void getFollowers()
    {
        out.println("Your followers: ");
        for (ClientHandler follower : followers)
        {
            out.println(follower.getName());
        }
    }

    public void addFollowers(ClientHandler user)
    {        
        user.followers.add(this);
    }

    public void removeFollowers(ClientHandler user)
    {        
        user.followers.remove(this);
    }

    public void outToFollowers(String message)
    {
        String fullMessage = Server.getDateAndTime() + name + ": " + message;

        if (message.length() > 50)
        {
            out.println("Too many Characters.");
        }
        else
        {
            for(ClientHandler aClient : followers)
            {
               
                aClient.out.println(fullMessage);
            }
            saveFile(fullMessage);
        }
        
    }

    public void outToAll(String message)
    {
        for(ClientHandler aClient : clients)
        {
            aClient.out.println(message);
        }
        
    }

    public void saveFile(String message)
    {
    try{
            BufferedWriter bw =  new BufferedWriter(new FileWriter("logs.txt", true));
            
            //Adding id tags to messages
            String allNames = "<" + name + ">";
            for (ClientHandler client : followers)
            {                
                allNames += "<" + client.getName() + ">";
            }
            allNames += "@"; //Marks end of tags

            //writing to file
            bw.append(allNames + " " + message + "\n");            
            
            bw.close();
        
       } catch (Exception e) 
       {
           return;
       }
    }

    public void readFile()
    {
        try 
        {
            BufferedReader br = new BufferedReader(new FileReader("logs.txt"));

            String word;

            while ((word = br.readLine()) != null)
            {                
                
                if (word.contains("<" + getName() + ">"))
                {
                    int startLine = word.indexOf("@");

                    out.println("\n" + word.substring(startLine + 1));
                }        
                                
            }
            br.close();

        } catch (Exception e) 
        {
            return;
        }
    }

    public void jsonEndoding(String message)
    {
        // JSONObject jsonObject = new JSONObject();

        // String text;

        // jsonObject.put("name", getName());
        // jsonObject.put("message", message);
        
        // text = jsonObject.toString();
        // out.println(text);
        
    }

    
    
}
