import game.TioliGame;
import gamedata.GameData;
import javafx.application.Application;
import javafx.stage.Stage;
import players.Player;

public class PlayGame extends Application {
	
	private GameData databaseObj = new GameData();
	
	public void start(Stage primaryStage) {		
		Player playerObj = databaseObj.getRandomPlayer();
			
			new TioliGame(playerObj);
		}
	
	public static void main(String[] args) {
		launch(args);
	}

}
