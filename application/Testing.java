package application;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.scene.Node;

public class Testing extends Application implements EventHandler<ActionEvent>
{       
    
    private StageGUI mainMenuScreen = new StageGUI();
    private SignUpScreen signUpScreen = new SignUpScreen();
    private LoginScreen loginScreen = new LoginScreen();
    private UserHomeScreen userHomeScreen = new UserHomeScreen();

    public static void main(String[] args) 
    {
        // runs 'start()' function
        launch();     
        
    }

    @Override
    public void start(Stage stage) throws Exception 
    {
        openMainMenuScreen(stage); 
        
    } 

    @Override
    public void handle(ActionEvent event) 
    {
        
        //------------------------------------Main Menu Button Click------------------------------------------
        for (int i = 0; i < mainMenuScreen.getButtons().size(); i++) 
        {
            if (event.getSource() == mainMenuScreen.getButtons().get(i))
            {
                try 
                {   
                    //String textBoxInput = signUpScreen.getTextBox().getCharacters().toString();

                    //All buttons functionality for this stage
                    if (i == 0)
                    {
                        openSignUpScreen(event);
                        return;
                    }
                    if (i == 1) 
                    {
                        openLoginScreen(event);
                        return;
                    }          
                                    
                } catch (Exception e) 
                {            
                    e.printStackTrace();
                }  
            }
        }
        //------------------------------Sign Up Menu Button Push-----------------------------------
        for (int i = 0; i < signUpScreen.getButtons().size(); i++) 
        {
            if (event.getSource() == signUpScreen.getButtons().get(i))
            {
                try 
                {   
                    //String textBoxInput = signUpScreen.getTextBox().getCharacters().toString();
                    
                    //All buttons functionality for this stage
                    if (i == 0) //'Back' Button
                    {
                        openMainMenuScreen(mainMenuScreen.getStage());
                        return;
                    }  
                    if (i == 1) //'Sign Up' Button - Receiving UserName and Password
                    {
                        for (int j = 0; j < signUpScreen.getTextBoxes().size(); j++)
                        {
                            System.out.println(signUpScreen.getTextBoxInput(j)); 
                        }
                        System.out.println(signUpScreen.getPasswordTextBoxInput());
                        return;
                    }                              
                                   
                } catch (Exception e) 
                {            
                    e.printStackTrace();
                }  
            }
        }
        //------------------------------Login Menu Button Push-----------------------------------
        for (int i = 0; i < loginScreen.getButtons().size(); i++) 
        {
            if (event.getSource() == loginScreen.getButtons().get(i))
            {
                try 
                {   
                    //String textBoxInput = signUpScreen.getTextBox().getCharacters().toString();
                    
                    //All buttons functionality for this stage
                    if (i == 0)
                    {
                        openMainMenuScreen(mainMenuScreen.getStage());
                        return;
                    }
                    if (i == 1)
                    {
                        if (loginScreen.getPasswordTextBoxInput().equals("assword")) openUserHomeScreen(event);
                        
                        return;
                    }                                   
                                                   
                                   
                } catch (Exception e) 
                {            
                    e.printStackTrace();
                }  
            }
        }
         //------------------------------User's Home Screen Button Push-----------------------------------
        for (int i = 0; i < userHomeScreen.getButtons().size(); i++) 
        {
            if (event.getSource() == userHomeScreen.getButtons().get(i))
            {
              try 
                {                         
                    //All buttons functionality for this stage
                    if (i == 0)
                    {
                        openMainMenuScreen(mainMenuScreen.getStage());
                        return;
                    }                                           
                                    
                } catch (Exception e) 
                {            
                    e.printStackTrace();
                }  
            }
         }
               
            
    }

    //Switching Screens
    public void openMainMenuScreen(Stage stage)
    {
        //Main Menu
        Group root = new Group();
        Scene scene = new Scene(root, 1400, 900, Color.PINK);  
        
        mainMenuScreen = new StageGUI(stage, scene, root);
        mainMenuScreen.getButtons().clear(); //resets buttons array for re-use

        mainMenuScreen.drawText("Main Menu", 420, 200, 100);
        mainMenuScreen.drawButton(this, "Sign Up ;)", 400, 500);
        mainMenuScreen.drawButton(this, "Login ;)", 800, 500);
        
        
    }
    public void openSignUpScreen(ActionEvent event)
    {        
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Group root = new Group();
        Scene scene = new Scene(root, 1400, 900, Color.LIGHTBLUE);        

        signUpScreen = new SignUpScreen(stage, scene, root);
        signUpScreen.getButtons().clear(); //resets buttons array for re-use
        
        //Drawing Scene
        signUpScreen.drawText("Email: ", 230, 170, 20);
        signUpScreen.drawText("User Name: ", 175, 320, 20);
        signUpScreen.drawText("Password: ", 190, 470, 20);
        signUpScreen.drawButton(this, "back", 1175, 820);
        signUpScreen.drawButton(this, "touch me ;)", 275, 600);
        signUpScreen.drawTextBox(300, 150);              
        signUpScreen.drawTextBox(300, 300);
        signUpScreen.drawPasswordTextBox(300, 450);

        
    }
    public void openLoginScreen(ActionEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Group root = new Group();
        Scene scene = new Scene(root, 1400, 900, Color.ORANGE);

        loginScreen = new LoginScreen(stage, scene, root);
        loginScreen.getButtons().clear(); //resets buttons array for re-use

        //Drawing Scene
        loginScreen.drawButton(this, "back", 1175, 820);
        loginScreen.drawButton(this, "login", 600, 600);        
        
        loginScreen.drawText("User Name: ", 500, 400, 20);
        loginScreen.drawText("Password: ", 520, 500, 20);
        loginScreen.drawTextBox(625, 380);
        loginScreen.drawPasswordTextBox(625, 480);
        
        
    }
    public void openUserHomeScreen(ActionEvent event)
    {
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Group root = new Group();
        Scene scene = new Scene(root, 1400, 900, Color.DARKGREEN);

        userHomeScreen = new UserHomeScreen(stage, scene, root);
        userHomeScreen.getButtons().clear(); //resets buttons array for re-use

        userHomeScreen.drawButton(this, "home", 1175, 820);
        userHomeScreen.drawTextBox(500, 400);         
        
    }
      
     
}
