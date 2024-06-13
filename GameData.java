package gamedata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import card.Card;
import hand.PokerHand;
import players.Player;


public class GameData {
	//Lab 32.2
	Connection connection;
	Statement statement;
	ResultSet resultSet;
	
	public GameData() {
		//Lab 32.2
		//Load the Driver
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("Driver Loaded");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//Establish a connection
		try {
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tioli", "root", "root");
			System.out.println("Connection Established");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//Create a statement object
		try {
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	public void insertHand(Player player) {
		//Lab 32.2
		String sqlStatement;
		Card[] cards = player.getHand().getCards();
		String handId = player.getId() + new Date().getTime();
		
		for(int i=0; i<cards.length; i++) {
			sqlStatement = "INSERT INTO hands (hand_id, card_num, player_id, face, suit) " + 
							"Values(" + "'" + handId + "', " + 
							(i+1) + ", " + 
							"'" + player.getId() + "', " + 
							"'" + cards[i].getFace() + "', " +
							"'" + cards[i].getSuit() + "'" +
							");";
			
			try {
				statement.executeUpdate(sqlStatement);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public void updateBank(Player player) {
		//PA 6.2
		String sqlStatement = "UPDATE player " + 
							"SET bank = '" + player.getBank() + "' " +
							"WHERE player_id = '" + player.getId() + "';";
		
		try {
			statement.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void insertResults(Player player, int amountWon) {
		//PA 6.2
		String sqlStatement;
		String gameId = player.getId() + new Date().getTime();
		String handDescr = ((PokerHand) player.getHand()).getHandDescr();
		
		sqlStatement = "INSERT INTO game_results (game_id, player_id, hand_descr, amount_won, player_bank) " + 
					"Values(" + "'" + gameId + "', " +
					"'" + player.getId() + "', " + 
					"'" + handDescr + "', " +
					"'" + amountWon + "', " +
					"'" + player.getBank() + "'" +
					");";
		
		try {
			statement.executeUpdate(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
}
	} 
	
	private ResultSet getPlayers() {
		//PA 6.2
		ResultSet results = null;
		try {
			results = statement.executeQuery("SELECT * FROM player");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;
	}
	
	public Player getRandomPlayer() {
		//For use with PA 6.2 and beyond
		Player player = null;
		
		try {
			//Get the list of players
			ResultSet playerData = getPlayers();
			
			//Now determine the size of the list
			playerData.last();
			int size = playerData.getRow() - 1;

			//Get random player and move to that place in the ResultSet
			int randomPlayer = (int)(Math.random() * size) + 1;
			playerData.absolute(randomPlayer);

			//Create a new player object
			player = new Player(playerData.getString(2), playerData.getString(1), playerData.getInt(3), new PokerHand()); 
			
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		
		return player; 
	}
	

	public ResultSet getReportData(Player player) {
		//Final Project
		ResultSet results = null;
		
		String sqlStatement = "SELECT * " + 
				"FROM game_results " +
				"WHERE player_id = '" + player.getId() + "';";
		
		try {
			results = statement.executeQuery(sqlStatement);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return results;		
	}
	

	public void close() {
		//Lab 32.2
		//Close the database object connection
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
