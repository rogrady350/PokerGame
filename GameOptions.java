package gameobjects;

import game.DealerArea;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class GameOptions extends VBox {
	//Step 1 -  create the attributes
	
	//Instantiate the 3 check box objects
	private CheckBox cardBack = new CheckBox("Red Card Back");
	private CheckBox showTimer = new CheckBox("Timer Visible");
	private CheckBox useTimer = new CheckBox("Use Timer");

	//Instantiate a label object and a TextField object for the timer value
	private Label lblSetTime = new Label("Set Timer To");
	private TextField txtSetTime = new TextField();
	
	//Instantiate an HBox to hold the the label and text field for set time
	HBox setTimeContainer = new HBox();
	
	//Instantiate a header
	private Text headerText = new Text("Game Options");
	HBox headerContainer = new HBox(headerText);
	
	//Declare attributes to hold the parameters sent to the Constructor
	private DealerArea dealerAreaObj;
	private GameTimer timerObj;
	
	public GameOptions(DealerArea dealerArea, GameTimer timer) {
		//Step 1A - Set the parameter attributes
		dealerAreaObj = dealerArea;
		timerObj = timer;
		
		//Now establish the nodes and listeners
		//Step 2.1
		createCardBackOption();
		
		//Step 2.2
		createShowTimerOption();
		
		//Step 2.3
		createUseTimerOption();
		
		//Step 2.4
		addSetTimerObjectsToHBox();
		createSetTimerText();
				
		//Step 2.5 - add everything to the VBox ('this')
		addObjectsToVBox();
		
		//Optional Step 2.6 - do some optional styling
		optionalStyling();
	}
	
	private void createCardBackOption() {
		//Step 2.1
		//Use this method to set the cardBack check box default and its listener
		//2.1A - Set the default checked (to false)
		cardBack.setSelected(false);
		
		//2.1B - Create a listener
		//The listener will set the color of the card back displayed
		//Use the dealerAreaObj to call the setCardBack method
		//And send it the appropriate color string
		cardBack.setOnAction(e -> {
			if(cardBack.isSelected()) {
				dealerAreaObj.setCardBack("red");
			}
			else {
				dealerAreaObj.setCardBack("blue");
			}
		});
	}
	
	private void createShowTimerOption() {
		//Step 2.2
		//2.2A - Set the default checked for the showTimer (to true)
		showTimer.setSelected(true);
		
		//2.2B - Create the listener
		//The listener will change the visibility of the timer object
		//Use the timer object and .setVisibility() method to set the visibility to true or false.
		showTimer.setOnAction(e -> {
			if(showTimer.isSelected()) {
				timerObj.setVisible(true);
			}
			else {
				timerObj.setVisible(false);
			}
		});
	}
	
	private void createUseTimerOption() {
		//Step 2.3
		//2.3A - Set the default check (to true)
		useTimer.setSelected(true);

		//2.3B - Create the listener
		//Call useTimerUpdates() method with the value of useTimer .isSelected
		useTimer.setOnAction(e -> {
			useTimerUpdates(useTimer.isSelected());
		});
	}
	
	private void useTimerUpdates(boolean useTimerSelected) {
		//Step 2.3C
		//This method is used by the useTimer checkbox's listener
		
		//Use the game timer object's setter to set the value of the useTimer
		timerObj.setUseTimer(useTimerSelected);
		
		//Optional Extra credit (up to 5 points)
		//Using the value of useTimerSelected:
		//Make the timerObj and setTimeContainer visible if true, not visible if false
		timerObj.setVisible(useTimerSelected);
		setTimeContainer.setVisible(useTimerSelected);
		
		//Make the showTimer check box's selected value true if true, false if false
			showTimer.setSelected(useTimerSelected);
			
		//Disable the showTimer if false, enable the showTimer if true
		if(useTimerSelected == false) {
			showTimer.setDisable(true);
		}
		else {
			showTimer.setDisable(false);
		}
	}
	
	private void addSetTimerObjectsToHBox() {
		//Step 2.4A
		//Add the set timer label and text field to the HBox
		setTimeContainer.getChildren().addAll(lblSetTime, txtSetTime);
	}
	
	private void createSetTimerText() {
		//Step 2.4B
		
		//2.4B_1 - Set the current number to the startSeconds value from the timer object
		//Use the timerObj to get the value of the startSeconds attribute
		//Convert that value to a string
		//Set the text of the text field to that string value
		
		txtSetTime.setText(String.valueOf(timerObj.getStartSeconds()));
		
		//2.4B_2 - Create the listener to change timer maxValue
		//1. Use setOnKeyPressed
		//2. Check if ENTER or TAB pressed.  If so, call the codeExecutedBySetTimerListener
		txtSetTime.setOnKeyPressed(e -> {
			if(e.getCode() == KeyCode.ENTER || e.getCode() == KeyCode.TAB) {
				codeExecutedBySetTimerListener();
			}
		});
	}
	
	private void codeExecutedBySetTimerListener() {
		//Step 2.4C - This is the code that is run when the txtSetTime listener is kicked off
		
		//1.  Get the value from the text field
		//2.  Convert to an int value
		//3.  Use the int value to and the timerObj to set the startSeconds attribute
		int startSeconds = Integer.parseInt(txtSetTime.getText());
		timerObj.setStartSeconds(startSeconds);
		//4.  Use the timerObj to refresh the timer display
		timerObj.refreshTimerDisplay();
	}
	
	//option boxes getters
	public CheckBox getUseTimer() {
		return useTimer;
	}

	public HBox getSetTimeContainer() {
		return setTimeContainer;
	}

	private void addObjectsToVBox() {
		//Step 2.5 - Add the header, 3 checkboxes, and the set time container to the VBox 
		//Remember, the GameOptions object is a VBox 
		//In other words 'this' is a VBox
		this.getChildren().addAll(headerContainer, cardBack, showTimer, useTimer, setTimeContainer);
	}
	
	private void optionalStyling() {
		//Step 2.6
		//Optional - Extra Credit (up to 5 points)
		//Provide some styling on the objects in the GameOptions VBox
		this.setStyle("-fx-font-size:18");
		this.setPadding(new Insets(20));
		txtSetTime.setPrefWidth(60);
		headerContainer.setAlignment(Pos.CENTER);
		headerText.setFont(Font.font("Helvetica", FontWeight.BOLD, 18));
		headerText.setUnderline(true);
	}
	
}
