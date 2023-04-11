import connection.Client;

import java.util.ArrayList;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StageGUI
{
    //Data
    private Stage stage;
    //private Scene scene;
    private Group root; 

    private Text text;
    private TextField textBox;
    private PasswordField passwordTextBox;
    protected ArrayList<TextField> textBoxes = new ArrayList<TextField>();
    protected ArrayList<Button> buttons = new ArrayList<Button>();
    protected ArrayList<Text> texts = new ArrayList<Text>();

    //Constructors
    public StageGUI()
    {
       
    }
    public StageGUI(Stage stage, Scene scene, Group root)
    {
        this.stage = stage;
        //this.scene = scene;
        this.root = root;

        stage.setTitle("Poop");
        //stage.setFullScreen(true);

        stage.setScene(scene);
        stage.show();
    }

    //Standard Methods
    public String getTextBoxInput(int i)
    {        
        String textBoxInput = textBoxes.get(i).getCharacters().toString();        
        
        return textBoxInput;
    }
    
    public String getPasswordTextBoxInput()
    {               
        String passwordTextBoxInput = passwordTextBox.getCharacters().toString();       
        return passwordTextBoxInput;
    }
    

    //Drawing methods
    public Text drawText(String userText, int locationX, int locationY, int fontSize)
    {
        text = new Text("");
        text.setText(userText);
        text.setX(locationX);
        text.setY(locationY);        
        text.setFont(Font.font("Verdana", fontSize));
        texts.add(text);
        root.getChildren().add(text);
        return text; 
    }
    public TextField drawTextBox(int locationX, int locationY)
    {
        textBox = new TextField();
        textBox.setLayoutX(locationX);
        textBox.setLayoutY(locationY);  
        textBoxes.add(textBox);            
        root.getChildren().add(textBox);
        
        return textBox;
    }
    public PasswordField drawPasswordTextBox(int locationX, int locationY)
    {
        passwordTextBox = new PasswordField();        
        passwordTextBox.setLayoutX(locationX);
        passwordTextBox.setLayoutY(locationY);        
        root.getChildren().add(passwordTextBox);

        return passwordTextBox;
    }
    public Button drawButton(Window t, String buttonText, int locationX, int locationY)
    {
        Button button = new Button(buttonText);
        button.setOnAction(t);
        button.setLayoutX(locationX);
        button.setLayoutY(locationY);
        button.setPrefSize(200, 50);  
        buttons.add(button);      
        root.getChildren().add(button);
        return button;
        
    }


    // Getters
    public ArrayList<Button> getButtons()
    {
        return buttons;
    }
    public ArrayList<TextField> getTextBoxes()
    {
        return textBoxes;
    }  
    public TextField getTextBox()
    {
        return textBox;
    }
    public ArrayList<Text> getTexts()
    {
        return texts;
    }
    public Stage getStage()
    {
        return stage;
    }
        
    

 


}