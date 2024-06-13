package game;

import card.Card;
import deck.Deck;
import deck.StandardDeck;
import gamedata.GameData;
import gamedata.GameFile;
import gameobjects.CardSelector;
import gameobjects.GameOptions;
import gameobjects.GameTimer;
import gameobjects.PayoutTable;
import gameobjects.ScoreBoard;
import gameobjects.Wager;
import hand.Hand;
import hand.PokerHand;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import players.Dealer;
import players.Player;
import reports.GameReport;

public class TioliGame extends Pane {
	
	private BorderPane gameScreen = new BorderPane();
	
	//5 sections, 1 for each section of the BorderPane
	private HBox topSection = new HBox(10);
	//private HBox bottomSection = new HBox(10);
	private VBox leftSection = new VBox(10);
	private VBox rightSection = new VBox(10);
	private VBox centerSection =  new VBox(10);
	
	//Create our game people
	private Dealer dealer;
	private Player player;
	
	//Play areas
	private PlayerArea playerArea;
	private DealerArea dealerArea;
	
	//An HBox to hold our header
	private HBox header;
	
	//Assignment 4.1 declare attributes/game objects
	private PayoutTable payoutTable;
	private Wager wager;
	private ScoreBoard scoreBoard;
	private GameTimer timerObj;
	
	//4.2 Instantiate button attributes
	private Button btnDeal = new Button("Deal");
	private Button btnTake = new Button("Take It");
	private Button btnLeave = new Button("Leave It");
	private Button btnExit = new Button("Quit");
	private VBox takeLeaveButtonPane = new VBox(10);
	
	//5.1 Declare GameOptions attribute
	private GameOptions gameOptions;
	
	//Lab 16.6 add CardSelector
	private CardSelector cardSelector = new CardSelector(5);
	
	//5.2 attributes
	private int maxTioliCards = 5;
	private int tioliCardsDealt = 0;
	
	//Final Project create Report button
	private Button btnReport = new Button("Report");
	
	//Constructor
	public TioliGame(Player player) {
		// Initialize our player object
		this.player = player;
		
		//get dealer object ready
		dealer = new Dealer(new StandardDeck(), new PokerHand());
		
		//Instantiate play areas
		dealerArea = new DealerArea(dealer);
		playerArea = new PlayerArea(this.player);
		
		//Create Header
		createHeader();
		
		styleButtons();
		
		//Assignment 4.1 create 3 game objects
		createGameObjects();
		
		//4.2 Create Button Listeners
		activateButtonListeners();
		
		//Add objects to each pane section, as needed
		addObjectsToTopSection();
		addObjectsToBottomSection();
		addObjectsToLeftSection();
		addObjectsToRightSection();
		addObjectsToCenterSection();
		
		//Put all the sections into the BorderPane
		addObjectsToGameScreen();
		
		//Lab 16.6 disable card selector on load
		cardSelector.setDisable(true);
		//5.2 disable take/leave buttons on load
		takeLeaveButtonPane.setDisable(true);

		
		//Launch the game screen
		showGame();
	}
	
	
//===========================================================================
//Constructor methods
	private void createHeader() {
		Text headerText = new Text("Welcome " + player.getName());
		headerText.setFont(Font.font("Helvetica", FontWeight.BOLD, FontPosture.ITALIC, 18));
		headerText.setFill(Color.GREEN);
		header = new HBox(headerText);
	}
	
	public void createGameObjects() {
		// 4.1 Instantiate 4 game objects
		payoutTable = new PayoutTable();
		wager = new Wager(player, 100);
		scoreBoard = new ScoreBoard(player);
		timerObj = new GameTimer(30, btnLeave);
		gameOptions = new GameOptions(dealerArea, timerObj);
	}
	
	//4.2 method to add/style game buttons
	private void styleButtons() {
		//add buttons to their holders
		manageButtonContainers();
		
		//style buttons
		takeLeaveButtonPane.setStyle("-fx-font-size:18");
		takeLeaveButtonPane.setAlignment(Pos.CENTER);
		btnTake.setPrefSize(100, 30);
		btnLeave.setPrefSize(100, 30);
		
		btnDeal.setStyle("-fx-font-size:18");
		btnDeal.setPrefSize(100, 30);
		
		btnReport.setStyle("-fx-font-size:18");
		btnReport.setPrefSize(100, 30);
		
		btnExit.setStyle("-fx-font-size:18");
		btnExit.setPrefSize(100, 30);
		
	}
	
	//4.2 add buttons to containers
	private void manageButtonContainers() {
		takeLeaveButtonPane.getChildren().addAll(btnTake, btnLeave);
		dealerArea.getChildren().addAll(takeLeaveButtonPane);
		
		playerArea.getChildren().add(cardSelector);
		//cardSelctor styling
		cardSelector.setPadding(new Insets(0, 0, 0, 80));
		
		playerArea.getChildren().add(btnDeal);
	}
	
	
	//4.2 method to declare button listeners
	private void activateButtonListeners() {
		btnTake.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				takeIt();
			}
		});
		
		btnLeave.setOnAction(e -> {
			leaveIt();
		});
		
		btnDeal.setOnAction(e -> {
			startDeal();
		});
		
		btnReport.setOnAction(e -> {
			new GameReport(player);			
		});
		
		btnExit.setOnAction(e -> {
			exitGame();
		});
	}
	
	private void addObjectsToTopSection() {
		topSection.getChildren().add(header);
		topSection.setAlignment(Pos.BASELINE_RIGHT);
		topSection.setPadding(new Insets(10,10,80,10));
		topSection.setStyle("-fx-background-image: url('file:images/chips.jpg')");
	}
	
	private void addObjectsToBottomSection() {
		//add later
	}
	
	private void addObjectsToLeftSection() {
		//5.1 Add GameOptions object
		leftSection.getChildren().add(gameOptions);
		leftSection.setStyle("-fx-background-color: #F8F0E3");
		leftSection.setAlignment(Pos.CENTER);
	}
	
	private void addObjectsToRightSection() {
		rightSection.getChildren().addAll(payoutTable, wager, scoreBoard, btnReport, btnExit);
		rightSection.setStyle("-fx-background-color: #F8F0E3");
		rightSection.setAlignment(Pos.CENTER);
	}
	
	private void addObjectsToCenterSection() {
		centerSection.getChildren().addAll(timerObj, dealerArea, playerArea);
		centerSection.setStyle("-fx-background-image: url('file:images/poker_table.jpg')");
	}
	
	private void addObjectsToGameScreen() {
		gameScreen.setTop(topSection);
		gameScreen.setCenter(centerSection);
		gameScreen.setRight(rightSection);
		gameScreen.setLeft(leftSection);
		
		BorderPane.setMargin(playerArea, new Insets(0,0,50,0));
	}
	
	private void showGame() {
		//Instantiate a Scene object
		Scene scene = new Scene(gameScreen, 1000, 680);
		
		//Instantiate a Stage Object
		Stage primaryStage = new Stage();
		
		//Set the title
		primaryStage.setTitle("Rob's TIOLI");
		
		//Add the scene to the stage
		primaryStage.setScene(scene);
		
		//show the stage
		primaryStage.show();
	}
	
	
//===========================================================================
//Playing the game
	
	private void startDeal() {
		//ec10 do not start deal if wager is greater than player's bank
		if (wager.getWagerAmount() <= player.getBank()) {		
			
			//ec6 disable ability to change timer options after cards are dealt
			gameOptions.getUseTimer().setDisable(true);;
			gameOptions.getSetTimeContainer().setDisable(true);
			
			//ec7 reset win amount when a new hand is dealt
			scoreBoard.setWinAmount(0);
			
			//4.2 Remove cards on table
			if (player.getHand().getCards().length != 0) {
				clearCards();
			}
			
			//5.2 disable deal button
			btnDeal.setDisable(true);
			
			//Clear card area
			playerArea.clearPlayerHand();
	
			//Clear discard pile
			dealerArea.clearDiscardHolder();
			
			//Deal hand to player
			dealPlayer();
			
			//Evaluate hand
			evaluateHand();
			
			//Display player cards
			playerArea.showCards();
			
			//Show the hand description
			playerArea.showHandDescr();
			
			//Deal the dealer's first card
			dealDealer();
		
			//Lab 16.6 Enable option buttons on deal
			cardSelector.setDisable(false);
			
			//5.2 Enable option buttons on deal
			takeLeaveButtonPane.setDisable(false);
		}
	}

	//methods	
	private void clearCards() {		
		Hand tempHand = player.getHand();
		Deck tempDeck = dealer.getDeck();
		
		tempHand.discardAll(tempDeck);
	}

	private void dealPlayer() {
		//5.2
		//ec5 disable wager adjustment of wager after cards are dealt
		wager.setDisable(true);
		for (int i=0; i<5; i++) {
			dealer.dealCard(player);
		}
	}
	
	private void evaluateHand() {
		player.getHand().evaluateHand();
	}
	
	private void dealDealer() {
		if (tioliCardsDealt == maxTioliCards) {
			endHand();
		}
		
		else {
			dealer.dealCard(dealer);
			dealerArea.showTioliCard();
			
			//5.2 keep count of cards dealt
			tioliCardsDealt = tioliCardsDealt + 1;
			timerObj.startTimer();
		}
	}
	
	private void takeIt() {
		//ec12 bug fix. if a card is not selected and the take it button is pressed, this will cause an error.
		if (cardSelector.getCardSelected() != 0) {
			//1.  Stop the timer
			timerObj.stopTimer();
		
			//2.  Determine the index of the card selected. Remember you get card number; arrays start at 0
			int indexCardSelected = cardSelector.getCardSelected() - 1;
		
			//3.  "GET" the (selected) card at the specific index from the player's hand
			Card playerCardSelected = player.getHand().getCard(indexCardSelected);
			
			//4.  "REMOVE" the (Tioli) card from the dealer's hand (remember dealer only has 1 card)
			Card dealerCardSelected = dealer.getHand().getCard(0);
			dealer.getHand().removeCard(0);
			
			//5.  Put (set) the Tioli card into the players hand at the selected index
			player.getHand().setCard(dealerCardSelected, indexCardSelected);
			
			//6.  Send the player's selected card to the discard pile using the Deck's addDiscard method
			dealer.getDeck().addDiscard(playerCardSelected);
			
			//7.  Clear the dealer area's tioli holder (check the class' code)
			dealerArea.clearTioliHolder();
			
			//8.  Show the selected card in the dealer area's discard pile (again, check the class' code)
			dealerArea.showDiscardedCard(playerCardSelected);
			
			//9.  Refresh the player area card images (check the PlayerArea's class code for the correct method
			playerArea.showCards();
			
			//10. Evaluate the hand (we have a method for that above)
			evaluateHand();
			
			//11. Show the updated hand description (again, check PlayerArea)
			playerArea.showHandDescr();
			
			//12. Deal the next Tioli card to the dealer (we already have a method above to do this)
			dealDealer();
			
			//ec8 unselect any selected card option buttons
			cardSelector.clearOptions();
		}
	}
	
	private void leaveIt() {		
		//1.  Stop the timer
		timerObj.stopTimer();
		
		//2.  "REMOVE" the (Tioli) card from the dealer's hand (remember dealer only has 1 card)
		Card dealerCardSelected = dealer.getHand().getCard(0);
		dealer.getHand().removeCard(0);
		
		//3.  Send the Tioli card to the discard pile using the Deck's addDiscard method
		dealer.getDeck().addDiscard(dealerCardSelected);
		
		//4.  Clear the dealer area's Tioli holder (check the class' code)
		dealerArea.clearTioliHolder();
		
		//5.  Show the Tioli card in the dealer area's discard pile (again, check the class' code)
		dealerArea.showDiscardedCard(dealerCardSelected);
		
		//6.  Deal the next Tioli card to the dealer (we already have a method above to do this)
		dealDealer();
		
		//ec8 unselect any selected card option buttons
		cardSelector.clearOptions();
	}
	
	//5.2
	private void endHand() {
		takeLeaveButtonPane.setDisable(true);
		btnDeal.setDisable(false);
		int amountWon = getPayout();
		displayFinalResults(amountWon);
		writeDataToFile(amountWon);
		saveDataInDatabase(amountWon);
		
		discardHand();
		
		tioliCardsDealt = 0;
		timerObj.stopTimer();
		//ec5 enable wager option after round is finished
		wager.setDisable(false);
		//ec6 re-enable ability to change timer options after round is finished
		gameOptions.getUseTimer().setDisable(false);;
		gameOptions.getSetTimeContainer().setDisable(false);
		
	}
	
	private int getPayout() {
		//1. Get the handRank from the player's hand
		int handRank = player.getHand().getHandRank();
		
		//2. Get the wager amount from the wager object
		int wagerAmount = wager.getWagerAmount();
		
		//3. Send those two values as arguments to the PayoutTable (which method returns the payout amount?).
		int payoutAmount = payoutTable.getPayout(handRank, wagerAmount);
		
		//4. Return the payout amount to endHand().
		return payoutAmount;
	}
	
	private void displayFinalResults(int amountWon) {
		//1.  Update the ScoreBoard object with the win/lost amount
		scoreBoard.setWinAmount(amountWon);
		
		//2. Update the player bank amount with the win/lose amount
		int bank = player.getBank() + amountWon;
		player.setBank(bank);
	
		//3.  Refresh the ScoreBoard object with the player's new bank amount
		scoreBoard.updateBank();
	}
	
	private void writeDataToFile(int amountWon) {
		GameFile.writeData("gamedata.dat", player, amountWon);
	}
	
	private void saveDataInDatabase(int amountWon) {
		//32.2Create a database Object
		GameData gameData = new GameData();
		
		//Insert the player's final hand
		gameData.insertHand(player);
		
		//6.2update players bank
		gameData.updateBank(player);
		
		//Insert the results of the game
		gameData.insertResults(player, amountWon);
		
		//32.2 Close the connection
		gameData.close();
	}
	
	private void discardHand() {
		Deck tempDeck = dealer.getDeck();
		
		player.getHand().discardAll(tempDeck);
		
		//ec9 reshuffle at end of each round when the hand is discarded
		dealer.reshuffleDeck();
	}
	
	private void exitGame() {
		Platform.exit();
		System.exit(0);
	}
	
}