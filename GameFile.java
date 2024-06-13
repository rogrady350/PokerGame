package gamedata;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import hand.PokerHand;
import players.Player;

public class GameFile {
	
	public static void writeData(String filename, Player playerObj, int winAmount) {
		try {
			DataOutputStream output = new DataOutputStream(new FileOutputStream("files/" + filename, true));
				output.writeUTF(playerObj.getId());
				output.writeUTF(playerObj.getName());
				output.writeUTF(((PokerHand) playerObj.getHand()).getHandDescr());
				output.writeInt(winAmount);
				output.writeInt(playerObj.getBank());
				
			output.close();
			
		} catch (IOException ex) {
			System.out.println("Error Writeing Data");
		}
	}

}
