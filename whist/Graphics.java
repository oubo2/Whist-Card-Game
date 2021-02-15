
import java.awt.Font;

//import Whist.Suit;
import ch.aplu.jcardgame.*; 
import ch.aplu.jgamegrid.*;

/** This project was completed by Team 49. **/

public class Graphics {

	public static final Location[] handLocations = {
			  new Location(350, 625),
			  new Location(75, 350),
			  new Location(350, 75),
			  new Location(625, 350)
	};
	public static final Location[] scoreLocations = {
			  new Location(575, 675),
			  new Location(25, 575),
			  new Location(575, 25),
			  new Location(650, 575)
	};
	public static final Location trickLocation = new Location(350, 350);
	public static final Location textLocation = new Location(350, 450);
	public static final Location hideLocation = new Location(-500, - 500);
	public static final Location trumpsActorLocation = new Location(50, 50);
	public static final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};
	
	public static final int handWidth = 400;
	public static final int trickWidth = 40;
	public static final Font bigFont = new Font("Serif", Font.BOLD, 36);
	private static final String version = "1.0";
	
	public static void initialiseRound(int nbPlayers, Player[] players, CardGame game) {
		RowLayout[] layouts = new RowLayout[nbPlayers];
	    for (int i = 0; i < nbPlayers; i++) {
	      layouts[i] = new RowLayout(players[i].getHandLocation(), handWidth);
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);
	      players[i].hand.setView(game, layouts[i]);
	      players[i].hand.setTargetArea(new TargetArea(Graphics.trickLocation));
	      players[i].hand.draw();
//		  for (int i = 1; i < nbPlayers; i++)  // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);
	    }
	}
	public static void displayHand(Hand trick, CardGame game, Card selected) {
		trick.setView(game, new RowLayout(Graphics.trickLocation, (trick.getNumberOfCards()+2)*Graphics.trickWidth));
		trick.draw();
		selected.setVerso(false);  // In case it is upside down
	}
	public static void displayHand(Hand trick, CardGame game, RowLayout r) {
		trick.setView(game, r);
		trick.draw();
	}
	public static Actor displayTrumpSuit(Whist.Suit trumps, CardGame game) {
		final Actor trumpsActor = new Actor("sprites/"+Graphics.trumpImage[trumps.ordinal()]);
	    game.addActor(trumpsActor, Graphics.trumpsActorLocation);
	    return trumpsActor;
	}
	public static void initMessage(CardGame game) {
		game.setTitle("Whist (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
	    game.setStatusText("Initializing...");
	}

}
