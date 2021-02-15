import ch.aplu.jcardgame.*; 

/** This project was completed by Team 49. **/

public class SmartStrategy implements IPlayStrategy {
	
	public Card selectCard(Hand hand, Whist.Suit lead, Whist.Suit trumpSuit) {
		Card selected = null;
		if (hand.getNumberOfCardsWithSuit(lead) > 0) {
        	if (Whist.rankGreaterOrEqual(hand.getCardsWithSuit(lead).get(0), Whist.cardsThrown[0].getCardsWithSuit(lead).get(0))) {
        		selected = hand.getCardsWithSuit(lead).get(0);
        	}
        	else {
        		selected = hand.getCardsWithSuit(lead).get(hand.getCardsWithSuit(lead).size() - 1);
        	}
        } else {
        	if (hand.getNumberOfCardsWithSuit(trumpSuit) > 0) {
        		selected = hand.getCardsWithSuit(trumpSuit).get(hand.getCardsWithSuit(trumpSuit).size() - 1);
        	}
        	else {
        		selected = hand.getCardList().get(hand.getCardList().size() - 1);
        	}
        }
		return selected;
	}
}
