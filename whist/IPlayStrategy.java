import ch.aplu.jcardgame.*; 
//import ch.aplu.jgamegrid.*;

/** This project was completed by Team 49. **/

public interface IPlayStrategy {
	
	public Card selectCard(Hand hand, Whist.Suit lead, Whist.Suit trumpSuit);

}
