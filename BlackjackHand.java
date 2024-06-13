package hand;

import card.Card;
import helpers.BlackjackSolver;

public class BlackjackHand extends Hand {
	private boolean blackjack = false;
	private boolean charlie = false;

	public BlackjackHand() {
		// Do not need this. Will automatically execute parents no arg constructor.
		super();
	}

	public BlackjackHand(Card[] dealtCards) {
		super(dealtCards);
		
	}
	
	@Override
	//overrides parents evaluateHand() method
	public void evaluateHand() {
		int score = BlackjackSolver.handScore(this);
		super.setHandScore(score);
		
		this.blackjack = BlackjackSolver.hasBlackjack(this);
		this.charlie = BlackjackSolver.hasCharlie(this);
	}

	//getters
	public boolean isBlackjack() {
		return blackjack;
	}

	public boolean isCharlie() {
		return charlie;
	}
}