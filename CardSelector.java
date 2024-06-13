package gameobjects;

import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;

public class CardSelector extends HBox{
	
	private RadioButton[] optionButtons;
	private ToggleGroup group = new ToggleGroup();
	private int cardSelected = 0;
	
	//Constructor
	public CardSelector(int numberOfCards) {
		this.optionButtons = new RadioButton[numberOfCards];
		
		createOptionButtons();
	}

	private void createOptionButtons() {
		for(int i=0; i<optionButtons.length; i++) {
			optionButtons[i] = new RadioButton();
			
			//Add the button to the toggle group
			optionButtons[i].setToggleGroup(group);
			
			//Styling
			optionButtons[i].setPadding(new Insets(0, 60, 20, 0));
			
			//Create listeners
			final int index = i;
			optionButtons[i].setOnAction(e -> {
				cardSelected = index + 1;
			});
			
			this.getChildren().add(optionButtons[i]);
		}
	}
	
	//Method to set all option buttons to not selected
		public void clearOptions( ) {
			for (int i=0; i<optionButtons.length; i++) {
				optionButtons[i].setSelected(false);
			}
			
			cardSelected = 0;
		}
		
	//getter
	public int getCardSelected() {
		return cardSelected;
	}

}
