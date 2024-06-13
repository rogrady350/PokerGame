package hand;

import card.Card;

public class PokerHand extends Hand {

	private String handDescr = "";
	public PokerHand() {
	}

	public PokerHand(Card[] dealtCards) {
		super(dealtCards);
	}

	public void evaluateHand() {
		//Call the PokerSolver class to evaluate the hand (see PokerSolver UML)
		//Note: "this" represents the current object
		helpers.PokerSolver.evaluateHand(this); 
		
	}
	
	public String getHandDescr() {
		return handDescr;
	}

	public void setHandDescr(String handDescr) {
		this.handDescr = handDescr;
	}

}
