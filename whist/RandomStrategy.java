import ch.aplu.jcardgame.*; 
//import ch.aplu.jgamegrid.*;

/** This project was completed by Team 49. **/

public class RandomStrategy implements IPlayStrategy {
	
	public Card selectCard(Hand hand, Whist.Suit lead, Whist.Suit trumpSuit) {
		return Whist.randomCard(hand);
	}

}
