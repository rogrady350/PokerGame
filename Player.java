package players;

import hand.Hand;

public class Player {
	private String name;
	private String id;
	private int bank;
	
	private Hand hand;
	
	public Player() {
	}
	
	public Player(Hand hand) {
		name = "Rob";
		id = "000000";
		bank = 1000;
		this.hand = hand;
	}
	
	public Player(String initName, String initId, Hand hand) {
		name = initName;
		id = initId;
		bank = 1000;
		this.hand = hand;
	}
	
	public Player(String initName, String initId, int initBank, Hand hand) {
		name = initName;
		id = initId;
		bank = initBank;
		this.hand = hand;
	}

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public Hand getHand() {
		return hand;
	}
	
	@Override
	public String toString() {
		return "Player " + name + ", id: " + id + " has a bank of $" + bank;
	}

}	