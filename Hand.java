package hand;

import java.util.ArrayList;

import card.Card;
import deck.Deck;

public abstract class Hand {
	//Create the attributes
	//private Card[] cards;
	private ArrayList <Card> cards;
	
	//Attributes used use by the PokerSolver
	private int handScore = 0;
	private int handRank = 0;
	private int highCard =0;

	public Hand() {
		//Only need to establish an empty cards array here
		//cards = new Card[handSize];
		cards = new ArrayList<Card>();
	}
	
	public Hand(Card[] dealtCards) {
		//Create the empty array
		//cards = new Card[dealtCards.length];
		cards = new ArrayList<Card>();
		
		//Loop through the dealtCards array and add each card object to the cards array
		for (int i=0; i<dealtCards.length; i++) {
			//cards[i] = dealtCards[i];  
			//cardCounter++; 
			cards.add(dealtCards[i]);
		}
	}
	
	public void addCard(Card dealtCard) {
		//We are sent 1 card
		//If the array is not full, then add the
		//Otherwise, just ignore it
		//Keep track of the  of cards added to the cards array
		//cards[cardCounter] = dealtCard;
		cards.add(dealtCard);
		}
	
	public void addCard(Card[] dealtCards) {
		//We are sent multiple cards
		//Loop through the array and try to add each card to the cards array
		//If the array is not full, then add the card
		//Otherwise, just ignore it
		//Keep track of the number of cards added to the cards array
		
			for (int i=0; i<dealtCards.length; i++) {
				//cards[cardCounter] = dealtCards[i];
				cards.add(dealtCards[i]);
		}
	}
	
	//Put a card into a specific element within the Hand
    public void setCard(Card dealtCard, int index){
    	//cards[index] = dealtCard;
		cards.set(index, dealtCard);
    }
	
	//Get a specific Card object at a specific index
	public Card getCard(int index) {
		//return cards[index]
		return cards.get(index);
	}
	
	//Get a specific Card object at a specific index and remove from Hand
	public Card removeCard(int index) {
		//Assignment 2.1
		return cards.remove(index);
	}
	
	public abstract void evaluateHand();
	
	public void discard(Deck deck, int index) {
		//Assignment 2.1
		//remove a card from the hand and send to the deck's discard pile
		Card tempCard = removeCard(index);
		deck.addDiscard(tempCard);
	}
	
	public void discardAll(Deck deck) {
		//Assignment 2.1
		//remove all the cards from the hand and send to the deck's discard pile
		while (cards.size()>0) {
			deck.addDiscard(cards.remove(0));
		}

	}
	
	@Override
	public String toString() {
		//Return a string with the card objects in the hand
		String handString = "Hand: ";
		for (int i=0; i<cards.size(); i++) {
			//handString += cards[i] + "\t"
			handString += cards.get(i) + " ";
		}
		
		return handString;
	}
	
	//Add the typical getters/setters

	//public Card getCards() {
	public Card[] getCards() {
		Card[] tempCards = new Card[cards.size()];
		cards.toArray(tempCards);
		return tempCards;
	}

	public int getHandScore() {
		return handScore;
	}

	public void setHandScore(int handScore) {
		this.handScore = handScore;
	}

	public int getHandRank() {
		return handRank;
	}

	public void setHandRank(int handRank) {
		this.handRank = handRank;
	}

	public int getHighCard() {
		return highCard;
	}

	public void setHighCard(int highCard) {
		this.highCard = highCard;
	}
}