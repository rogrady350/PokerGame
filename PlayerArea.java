package game;

import card.Card;
import hand.Hand;
import hand.PokerHand;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import players.Player;

public class PlayerArea extends VBox{
	//Lab 14.2
	//Who does the player area belong to?
	private Player player;
	
	//An HBox to hold the cards
	private HBox playerCardArea = new HBox(10);

	//A Text box to hold the handDesr
	private Text handResult = new Text(" ");
	
	
	public PlayerArea(Player player) {  //What parameter do we need?
		//Note which player owns this object
		this.player = player;
		
		//Create the display for the card hands
		createCardArea(); 
		createHandResults();
		addObjectsToTheVBox();
	}
	
	private void createCardArea() {
		//LAb 4.2 
		
		//Style the HBox to hold our card images
		playerCardArea.setAlignment(Pos.CENTER);
		playerCardArea.setPadding(new Insets(5,5,5,5));

		
		//Use some CSS to style the HBox
		styleCardHolder();

	}
	
	private void createHandResults() {
		//Lab 14.2
		//Style an object for the Hand Results (the handDescr object from PokerHand)
		handResult.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.ITALIC, 32));
		
		handResult.setFill(Color.WHITE);
		
		VBox.setMargin(handResult, new Insets(0,0,15,0));
	}

	private void addObjectsToTheVBox() {
		//Lab 14.2
		//Place the card area and hand description into a VBox
		//Remember, this class defines a VBox object so use "this"
		this.getChildren().addAll(handResult, playerCardArea);
		
		//Align cards in center
		this.setAlignment(Pos.BOTTOM_CENTER);

	}
	
	private void styleCardHolder() {
		String cssLayout = 
				"-fx-border-color: black;\n" +
                "-fx-border-insets: 5;\n" +
                "-fx-border-width: 2;\n" +
                "-fx-border-style: solid;\n";

		playerCardArea.setStyle(cssLayout);
		playerCardArea.setPrefWidth(425);
		playerCardArea.setPrefHeight(130);
		playerCardArea.setMaxHeight(USE_PREF_SIZE);
		playerCardArea.setMaxWidth(USE_PREF_SIZE);
	}
	
	//This adds the appropriate card images to the screen
	protected void showCards() {
		//Lab 14.2
		
		//If cards existed previously, clear them
		clearPlayerHand();
		
		//We need to get the player's card array from the Hand object
		//so we can get the imageURLs
		Hand playerHand = player.getHand();
		Card[] playerCard = playerHand.getCards();
		
		//Loop through the hand to get the image URLs
		for(int i=0; i<playerCard.length; i++) {
			String imageUrl = playerCard[i].getCardImage();
			
			playerCardArea.getChildren().add(new ImageView(imageUrl));
		}
	}

	protected void clearPlayerHand() {
		//Lab 14.2
		//Clear the PlayerCardArea pane
		playerCardArea.getChildren().clear();
		
	}

	
	protected void showHandDescr() {
		//Lab 14.2
		
		//We need to get the handDesr from the player's hand object
		//Remember, the player has a Hand but the descr sits on PokerHand
		Hand playerHand = player.getHand();
		PokerHand pokerHand = (PokerHand)playerHand;
		
		handResult.setText(pokerHand.getHandDescr());
	}

}