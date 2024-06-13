package reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import gamedata.GameData;
import players.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GameReport {
	//Window dimensions - adjust as needed
	int windowWidth = 1050;
	int windowHeight = 550;
	
	//Instantiate a new Stage (window object)
	Stage reportStage = new Stage();

	//To do: We need to Declare a player object as an attribute
	private Player player;
	
	//To do: Declare a Text Object field for the page title
	private Text pageTitle;
	
	//The panes to hold our nodes
	BorderPane pane = new BorderPane(); //Main pain for the scene
	GridPane dataGrid = new GridPane();  //To display our data (from the Text objects)
	ScrollPane scrollPane;  //So our data will scroll
	HBox titleContainer;  //A container for our report title
	
	//new panes
	GridPane labelGrid = new GridPane(); //Displays our headers
	VBox gridContainer; //Container for the center section. H labelGrid and scrollPane
	
	//To do: We need to instantiate database object
	private GameData gameData = new GameData();
	
	//Create array lists to hold each piece of data in a Text object
	ArrayList<Text> gameId = new ArrayList<Text>();
	//To do: Now create the array lists that are needed for the other data being reported
	ArrayList<Text> descr = new ArrayList<Text>();
	ArrayList<Text> winAmount = new ArrayList<Text>();
	ArrayList<Text> bank = new ArrayList<Text>();

	//We will need to close the screen when done
	private Button btnExit = new Button("Exit");

	
	public GameReport(Player player) {
		//First, use the parameter to set the appropriate attribute
		this.player = player;
		
		//Step 1:
		//Get our data from the database
		getData();
		
		//Step 2:
		//Put our Text objects into the GridPane
		populateGridPane();

		//Step 3:
		//Put the grid pane into a scroll pane
		addGridToScroll();

		//Step 4:
		//Set the listener for the Exit button
		createExitButtonListener();

		//Step 5:
		//Create a report title
		createReportTitle();
				
		//Step 6:
		//Put our objects into the BorderPane
		addObjectsToPage();
		
		//Step 7:
		//Add styling to make it look pretty
		styleStuff();

		//Make the screen appear
		showScene();
	}
	
	private void getData() {
		//1A
		//Call the getReportData method you created
		//Use your GameData object 
		//Put the data returned into a local result set 
		ResultSet results = gameData.getReportData(player);
		
		//Add the column headers to the Text object ArrayLists
		gameId.add(new Text("Game ID"));
		descr.add(new Text("Hand Descr"));
		winAmount.add(new Text("Amount Won"));
		bank.add(new Text("Player Bank"));
		
		//1B
		//Now loop through the result set
		//Add each of the appropriate columns to Text objects
		//Add the Text Objects to the appropriate Array List
		//The format is like the header but instead of harcoded text, use the information from the ResultSet
		try {		
			while(results.next()) {
				Text gameIdText = new Text(results.getString(2));
				gameId.add(gameIdText);
				
				Text descrText = new Text(results.getString(3));
				descr.add(descrText);
				
				Text winAmountText = new Text(results.getString(4));
				winAmount.add(winAmountText);
				
				Text bankText = new Text(results.getString(5));
				bank.add(bankText);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void populateGridPane() {
		//2
		//We use this method to add the Text objects to the GridPane
		//Again, you will have to loop through the Text ArrayLists
		//Hint...all the Text ArrayLists are the same size
		
		labelGrid.add(gameId.get(0), 0, 0);
		labelGrid.add(descr.get(0), 1, 0);
		labelGrid.add(winAmount.get(0), 2, 0);
		labelGrid.add(bank.get(0), 3, 0);
		
		for(int i=1; i<gameId.size(); i++) {
			dataGrid.add(gameId.get(i), 0, i-1);
			dataGrid.add(descr.get(i), 1, i-1);
			dataGrid.add(winAmount.get(i), 2, i-1);
			dataGrid.add(bank.get(i), 3, i-1);
		}
	}
	
	private void addGridToScroll() {
		//3
		//Here you add the GridPane to the ScrollPane by
		//Instantiating your scrollPane object and feed the grid pane to the ScrollPane constructor
		scrollPane = new ScrollPane(dataGrid);
		//Instantiate gridContainer and feed labelGrid and scrollPane to it
		//This VBox gets sent to the center section of the page as scrollPane did
		gridContainer = new VBox(labelGrid, scrollPane);
	}
	
	
	private void createExitButtonListener() {
		//4A
		//Define the exit button listener to call exitReport()
		btnExit.setOnAction(e -> {
			reportStage.close();
		});

		//4B - complete the exitReport method (already defined below)
		exitReport();
	}
	
	private void createReportTitle() {
		//5A
		//Instantiate the Text object attribute for the title at the top of the page 
		//Title should include the player's name and "Game Data" or "Report", etc.
		pageTitle = new Text(player.getName() + "'s Game Data");
		
		
		//5B
		//Instantiate the titleContainer and put the titleText into it
		titleContainer = new HBox(pageTitle);
	}
	
	private void addObjectsToPage() {
		//Here we will add our objects to the page (the BorderPane):
		//6A
		//Put the title in the top
		pane.setTop(titleContainer);
		
		//6B
		//Put the ScrollPane in the center
		//scrollPane is now inside gridContainer
		pane.setCenter(gridContainer);
		
		//6C
		//Put the Button object in the bottom
		//See if you can center it (width-wise) in the styling method
		pane.setBottom(btnExit);

	}
	
	private void exitReport() {
		//4B
		//Close the database object before closing the window
		gameData.close();
		
		//Close the window
		reportStage.close();
	}
	
	private void styleStuff() {
		//Some styling for the GridPane...should work ok
		//Feel free to adjust as needed	
		//Added styling for both GridPanes here
		//This was my best attempt to get all the columns to consistently display neatly
		
		dataGrid.setVgap(20);
		
		gridContainer.setPadding(new Insets(40));
		
		ColumnConstraints c1 = new ColumnConstraints();
			c1.setPrefWidth(220);
		ColumnConstraints c2 = new ColumnConstraints();
			c2.setPrefWidth(220);
		ColumnConstraints c3 = new ColumnConstraints();
			c3.setPrefWidth(220);
		ColumnConstraints c4 = new ColumnConstraints();
			c4.setPrefWidth(220);
			
		labelGrid.getColumnConstraints().addAll(c1, c2, c3, c4);
		dataGrid.getColumnConstraints().addAll(c1, c2, c3, c4);

		//Here is some styling to help keep the scroll pane from squishing to the edge of the window
		//Feel free to modify
		int leftRightSpace = 80;
		scrollPane.setPrefWidth(windowWidth - leftRightSpace);
		scrollPane.setMaxWidth(windowWidth - leftRightSpace);
		
		//Any styling for the Page title?
		titleContainer.setAlignment(Pos.CENTER);
		titleContainer.setPadding(new Insets(10));
		pageTitle.setFont(Font.font("Helvetica", FontWeight.BOLD, 35));
		
		//Any styling for your exit button?
		btnExit.setStyle("-fx-font-size:15");
		btnExit.setPrefSize(80, 30);
		
		//Can you make the exit button centered horizontally?
		//Hint: note there is a window width attribute
		BorderPane.setAlignment(btnExit, Pos.CENTER);
		BorderPane.setMargin(btnExit, new Insets(10));
		
		//Style your Text objects?
		//Consider font type, font size, color, etc.
		//Remember you will have to loop through the Text object ArrayLists
		//Hint...all the Text ArrayLists are the same size
		for(int i=1; i<gameId.size(); i++) {
			gameId.get(i).setFont(Font.font("Helvetica", 12));
			descr.get(i).setFont(Font.font("Helvetica", 12));
			winAmount.get(i).setFont(Font.font("Helvetica", 12));
			bank.get(i).setFont(Font.font("Helvetica", 12));
		}

		//Change column headers' size/color?
		gameId.get(0).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		gameId.get(0).setFill(Color.RED);
		gameId.get(0).setUnderline(true);
		
		descr.get(0).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		descr.get(0).setFill(Color.RED);
		descr.get(0).setUnderline(true);
		
		winAmount.get(0).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		winAmount.get(0).setFill(Color.RED);
		winAmount.get(0).setUnderline(true);
		
		bank.get(0).setFont(Font.font("Helvetica", FontWeight.BOLD, 20));
		bank.get(0).setFill(Color.RED);
		bank.get(0).setUnderline(true);
		
		//Maybe style the scroll pane so there is some padding on top of the GridPane?
		//Padding for GridPanes as well
		scrollPane.setPadding(new Insets(10));
		labelGrid.setPadding(new Insets(0, 10, 5, 15));
		dataGrid.setPadding(new Insets(10));
	}
		
	private void showScene() {
		//This will make our report show
		//No changes needed
		Scene scene = new Scene(pane, windowWidth, windowHeight);
		reportStage.setTitle("Draw Poker Data For " + player.getName());
		reportStage.setScene(scene);
		reportStage.show();		
	}
}
