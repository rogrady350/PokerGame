package players;

import card.Card;
import deck.Deck;
import hand.Hand;

public class Dealer extends Player {
	private Deck deck;
	
	public Dealer(Deck deck) {
		this.deck = deck;
		prepareDeck();
	}
	
	public Dealer(Deck deck, Hand hand) {
		super(hand);
		
		setName("Dealer");
		setId("dealer");
		setBank(0);
		
		this.deck = deck;
		prepareDeck();
	}
	
	private void prepareDeck() {
		deck.createDeck();
		deck.shuffleDeck();
	}
	
	public void dealCard(Player player) {
		//Place a card from the deck in the players hand
		//Assignment 1.2
		Card card = deck.removeCard(0);
		Hand hand = player.getHand();
		hand.addCard(card);
	}
	
	public Card getCard(int index) {
		Card tempCard = deck.getCard(index);
		
		return tempCard;
	}
	
	public void reshuffleDeck() {
		Card[] tempCards = deck.getCards();
		Card[] tempDiscard = deck.getDiscardPile();
		
		int totalCards = tempCards.length + tempDiscard.length;
		int reshuffleCount = (int)(totalCards*.6);
		if(tempCards.length < reshuffleCount) {
			deck.restack();
		}
	}
	
	public Deck getDeck() {
		return deck;
	}
}