import ch.aplu.jcardgame.*;  
import ch.aplu.jgamegrid.*;

/** This project was completed by Team 49. **/

public class Player {
	
	public enum Type {
		HUMAN, LEGAL, SMART, RANDOM
	}
	
	private int iD;
	private Location handLocation;
	private Location scoreLocation;
	private Actor scoreActor;
	public Hand hand;
	private int score;
	private Type type;
	
	private IPlayStrategy playStrategy = null;
	
	
	public Player(int iD, Type type) {
		this.iD = iD;
		handLocation = Graphics.handLocations[iD];
		scoreLocation = Graphics.scoreLocations[iD];
		scoreActor = null;
		this.type = type;
		setPlayerStrategy(type);
	}
	private void setPlayerStrategy(Type type) {
		if (type.equals(Type.RANDOM))     { this.playStrategy = new RandomStrategy(); }
		else if (type.equals(Type.LEGAL)) { this.playStrategy = new LegalStrategy(); }
		else if (type.equals(Type.SMART)) { this.playStrategy = new SmartStrategy(); }
		else { }
	}
	public int getID() { return iD; }
	public Type getType() { return type; }
	public int getscore() { return score; }
	public void setScore(int score) {
		assert (score >= 0);
		this.score = score;
	}
	public Hand getHand() { return hand; }
	public void setHand(Hand hand) {
		this.hand = hand;
	}
	public void setScoreActor(Actor s) {
		this.scoreActor = s;
	}
	public Actor getScoreActor() { return scoreActor; }
	public Location getScoreLocation() { return scoreLocation; }
	public Location getHandLocation()  { return handLocation;  }
	
	public Card playStrategy(Whist.Suit lead, Whist.Suit trumpSuit) {
	    return playStrategy.selectCard(hand, lead, trumpSuit);
	}

}
