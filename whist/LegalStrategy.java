import ch.aplu.jcardgame.*; 
//import ch.aplu.jgamegrid.*;

/** This project was completed by Team 49. **/

public class LegalStrategy implements IPlayStrategy {
	
	public Card selectCard(Hand hand, Whist.Suit lead, Whist.Suit trumpSuit) {
		
		if (hand.getNumberOfCardsWithSuit(lead) > 0) {
			return Whist.randomCard(hand.getCardsWithSuit(lead));
		}
		return Whist.randomCard(hand);
	}
}
