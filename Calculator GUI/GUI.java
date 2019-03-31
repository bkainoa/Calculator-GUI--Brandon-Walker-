
/**
 * Write a description of class GUI here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
//Javafx
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.event.*;
import javafx.event.ActionEvent;
import javafx.scene.text.Font;

//Arrays/Lists
import java.util.Arrays;
import java.util.List;

//Sound
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class GUI extends Application
{
    TextField evalField;
    public static String evalText;
    String answer;
    List<String> operatorsL;
    MediaPlayer clickSoundPlayer;
    
    public void start(Stage window)
    {
        window.show();
        window.setTitle("Postfix Calculator");
        
        Pane canvas = new Pane();
        Scene myScene = new Scene(canvas, 234, 250);
        window.setScene(myScene);
        
        //Title
        Label title = new Label("Post Fix Calculator");
        canvas.getChildren().add(title);
        title.relocate(32,20);
        title.setFont(new Font("Arial", 20));
        
        //Adds the main textfield
        evalField = new TextField();
        evalText = "";
        canvas.getChildren().add(evalField);
        evalField.setMinSize(204,28);
        evalField.relocate(16,48);
                
        //Adds the number buttons
        for( int i = 1; i < 4; i++ ) //columns
        {
            for (int j = 1; j < 4; j++ ) //rows
            {
                String buttonName = Integer.toString( (i-1) * 3 + j );
                Button button = new Button(buttonName);
                canvas.getChildren().add(button);
                button.relocate( j * 32 - 16, 75 + i * 32 );
                button.setOnAction(this::handleButton);
                button.setMinSize(30,30);
            }
        }
        //Zero
        Button zeroButton = new Button("0");
        canvas.getChildren().add(zeroButton);
        zeroButton.relocate( 16, 203 );
        zeroButton.setOnAction(this::handleButton);
        zeroButton.setMinSize(30,30);
         
        //Adds Clear button
        Button clearButton = new Button("Clear");
        canvas.getChildren().add(clearButton);
        clearButton.relocate( 15,79 );
        clearButton.setOnAction(this::handleButton);
        clearButton.setStyle("-fx-text-fill: #f70000");
        clearButton.setMinSize(48, 20);
        
        //Adds Back button
        Button backButton = new Button("←");
        canvas.getChildren().add(backButton);
        backButton.relocate( 65,79 );
        backButton.setOnAction(this::handleButton);
        backButton.setMinSize(45,20);
        
        //Adds the space bar
        Button spaceButton = new Button("Space");
        canvas.getChildren().add(spaceButton);
        spaceButton.relocate( 49,203 );
        spaceButton.setOnAction(this::handleButton);
        spaceButton.setMinSize(92,30);
        
        //Adds the evaluation button
        Button evalButton = new Button("↵");
        canvas.getChildren().add(evalButton);
        evalButton.relocate( 188,203 );
        evalButton.setOnAction(this::handleButton);
        evalButton.setMinSize(30,30);
        evalButton.setStyle("-fx-text-fill: #005608");
        evalButton.setStyle("-fx-background-color: #91c18d");
        
        //Adds the Answer button
        Button ansButton = new Button("Ans");
        canvas.getChildren().add(ansButton);
        ansButton.relocate( 144,203 );
        ansButton.setOnAction(this::handleButton);
        ansButton.setMinSize(42,30);
        
        //Adds the operator buttons
        
        //Creates a list of the operators
        String[] operatorsArr = new String[]{"+", "-", "*", "/", "^", "sqrt", "swap", "dup", "rot", "!", "%", "π"};
        operatorsL = Arrays.asList(operatorsArr);
        
        for( int i = 0; i < 4; i++ ) //rows
        {
            for( int j = 0; j < 3; j++ ) //columns
            {
                //The second column needs to be larger
                int padding = 0;
                if( j == 1 )
                    padding = 12;
                    
                //The third column needs to be further right
                int offset = 0;
                if( j == 2 )
                    offset = 12;
                    
                String buttonName = operatorsArr[i + j * 4];
                Button button = new Button(buttonName);
                canvas.getChildren().add(button);
                button.relocate( 112 + 32 * j + offset, 79 + 31 * i );
                button.setOnAction(this::handleButton);                                
                button.setMinSize(30 + padding,29);                
            }
        }
        
        //Sound
        String cSoundFile = "click_sound.mp3";
        Media clickSound = new Media(new File(cSoundFile).toURI().toString());
        clickSoundPlayer = new MediaPlayer(clickSound);
    }
    
    public void handleButton(ActionEvent ae)
    {    
        clickSoundPlayer.stop();
        clickSoundPlayer.play();
        //clickSoundPlayer.rewind();
        evalText = evalField.getText();
        String fullString = ae.toString();
        String[] componentArr = fullString.split("\'");
        String buttonName = componentArr[1];
        int buttonVal = 10; //default value
                        
        try 
        { 
            buttonVal = Integer.parseInt(buttonName);             
        }  
        catch (NumberFormatException e)  
        {     
            
        } 
        
        
        if( buttonVal < 10 ) //if the button pressed was a number 
        {
            evalText = evalText + buttonVal;            
        }
        else if( operatorsL.contains(buttonName) )
        {
            evalText = evalText + " " + buttonName + " ";
        }
        else //Function buttons (clear, back, eval, ...)
        {
            switch(buttonName)
            {
                case "Clear":
                                evalText = "";
                                break;
                case "←":
                                evalText = evalText.substring(0,evalText.length() - 1);
                                break;
                case "Ans":
                                evalText = answer;
                                break;
                case "Space":   
                                evalText = evalText + " ";
                                break;
                case "↵":
                                Postfix p = new Postfix(evalText);  
                                try
                                {
                                    evalText =  Double.toString( p.eval() );
                                }
                                catch( MathError e)
                                {
                                    evalText = "Math Error";
                                }
                                catch( UnknownToken e )
                                {
                                    evalText = "Unknown Token";
                                }
                                answer = evalText;
                                break;
            }         
        }
        
        evalField.setText( evalText ); 
               
    }
}
