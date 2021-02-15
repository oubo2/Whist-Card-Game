// Whist.java

import ch.aplu.jcardgame.*;  
import ch.aplu.jgamegrid.*;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.io.IOException;

/** This project was completed by Team 49. **/

@SuppressWarnings("serial")
public class Whist extends CardGame {
	
  public static enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }

  static final Random random = ThreadLocalRandom.current();
  
  // return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }
  
  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }
 
  // return random Card from ArrayList
  public static Card randomCard(ArrayList<Card> list){
      int x = random.nextInt(list.size());
      return list.get(x);
  }
  
  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }
  public static final int nbPlayers = 4;
  private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
  private final int thinkingTime = 2000;
  
  public static int nbStartCards = 13;
  public static int winningScore = 5;
  public static boolean enforceRules=false;
  private static Player[] players = {null, null, null, null };
  
  public void setStatus(String string) { setStatusText(string); }
  
  /**** extra deck ***/
  public static boolean rankGreaterOrEqual(Card card1, Card card2) {
	  return card1.getRankId() <= card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }
  
  //Deck and hand for the cards that have been played on the table
   private static Deck SecondDeck= new Deck(Suit.values(), Rank.values(), "cover");
   public static Hand[] cardsThrown = SecondDeck.dealingOut(1,52);
   
   /************/

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 players[i].setScore(0);
		 players[i].setScoreActor(new TextActor("0", Color.WHITE, bgColor, Graphics.bigFont));
		 addActor(players[i].getScoreActor(), Graphics.scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	// removeActor(scoreActors[player]);
	players[player].setScoreActor(null);
	players[player].setScoreActor(new TextActor(String.valueOf(players[player].getscore()), Color.WHITE, bgColor, Graphics.bigFont));
	addActor(players[player].getScoreActor(), players[player].getScoreLocation());
}

private Card selected;

private void initRound() {
		 Hand[] hands = deck.dealingOut(nbPlayers, nbStartCards); // Last element of hands is leftover cards; these are ignored
		 for (int i = 0; i < nbPlayers; i++) {
			 players[i].setHand(hands[i]);
			 players[i].hand.sort(Hand.SortType.SUITPRIORITY, true);
		 }
		 // Set up human player for interaction
		CardListener cardListener = new CardAdapter()  // Human Player plays card 
			{ public void leftDoubleClicked(Card card) { 
			    selected = card;
			    players[0].hand.setTouchEnabled(false); }
		    };
		players[0].hand.addCardListener(cardListener);
		Graphics.initialiseRound(nbPlayers, players, this);
 }
private Suit selectTrumpSuit() {
	final Suit trumps = randomEnum(Suit.class);
	return trumps;
	
}

private void checkRuleViolation(Suit lead, int nextPlayer) {
	if (selected.getSuit() != lead && players[nextPlayer].hand.getNumberOfCardsWithSuit(lead) > 0) {
		 // Rule violation
		 String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
		 System.out.println(violation);
		 if (enforceRules) 
			 try {
				 throw(new BrokeRuleException(violation));
				} catch (BrokeRuleException e) {
					e.printStackTrace();
					System.out.println("A cheating player spoiled the game!");
					System.exit(0);
				}  
	 }
}
private void selectCardToPlay(int nextPlayer) {
	selected = null;
	// Select lead depending on player type
	if (players[nextPlayer].getType().equals(Player.Type.HUMAN)){  
    	players[nextPlayer].hand.setTouchEnabled(true);
    	setStatus("Player " + nextPlayer + "double-click on card to lead.");
		while (null == selected) delay(100);
    } else {
		setStatusText("Player " + nextPlayer + " thinking...");
        delay(thinkingTime);
        selected = randomCard(players[nextPlayer].hand);
        //selected = players[nextPlayer].playStrategy();
    }
}

private void selectCardToPlay(int nextPlayer, Suit lead, Suit trumpSuit) {
	selected = null;
	// Select lead depending on player type
	if (players[nextPlayer].getType().equals(Player.Type.HUMAN)) {
		players[nextPlayer].hand.setTouchEnabled(true);
		setStatus("Player " + nextPlayer + "double-click on card to lead.");
		while (null == selected) delay(100);
    } else {
		setStatusText("Player " + nextPlayer + " thinking...");
        delay(thinkingTime);
        //selected = randomCard(players[nextPlayer].hand);
        selected = players[nextPlayer].playStrategy(lead, trumpSuit);
    }
}

private void playCard(Hand trick, Card winningCard, Suit trumps, int winner, int nextPlayer) {
	 selected.transfer(trick, true); // transfer to trick (includes graphic effect)
	 System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + winningCard.getRankId());
	 System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " +    selected.getRankId());
}
private Card getWinningCard(Hand trick, Card winningCard, Suit trumps, int winner, int nextPlayer) {
	if ( // beat current winner with higher card
			 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
			  // trumped when non-trump was winning
			 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
			 //winner = nextPlayer;
			 winningCard = selected;
		 }
	return winningCard;
}
private int getWinner(Hand trick, Card winningCard, Suit trumps, int winner, int nextPlayer) {
	if ( // beat current winner with higher card
			 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
			  // trumped when non-trump was winning
			 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
			 winner = nextPlayer;
			 //winningCard = selected;
		 }
	return winner;
}

private Optional<Integer> playRound() {  // Returns winner, if any
	// Select and display trump suit  
	Suit trumps = selectTrumpSuit();
	Actor trumpsActor = Graphics.displayTrumpSuit(trumps, this);
	// End trump suit
	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;
	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	for (int i = 0; i < nbStartCards; i++) {
		trick = new Hand(deck);  
    	selectCardToPlay(nextPlayer);
        // Lead with selected card
    		Graphics.displayHand(trick, this, selected);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
		// End Lead
		for (int j = 1; j < nbPlayers; j++) {
			if (++nextPlayer >= nbPlayers) nextPlayer = 0;  // From last back to first
			selectCardToPlay(nextPlayer, lead, trumps);
	        // Follow with selected card
			Graphics.displayHand(trick, this, selected);
			// Check: Following card must follow suit if possible
			checkRuleViolation(lead, nextPlayer);
		    playCard(trick, winningCard, trumps, winner, nextPlayer);
			winner = getWinner(trick, winningCard, trumps, winner, nextPlayer);
		    winningCard = getWinningCard(trick, winningCard, trumps, winner, nextPlayer);
		}
		//recording cards that have been played
		for (int j = 0; j < nbPlayers; j++) {
			cardsThrown[0].remove(trick.get(j),false);
		}
		delay(600);  
		Graphics.displayHand(trick, this, new RowLayout(Graphics.hideLocation, 0));
		nextPlayer = winner;
		setStatusText("Player " + nextPlayer + " wins trick.");
		players[nextPlayer].setScore(players[nextPlayer].getscore() + 1);
		updateScore(nextPlayer);
		if (winningScore == players[nextPlayer].getscore()) return Optional.of(nextPlayer);
	}
	removeActor(trumpsActor);
	return Optional.empty();
}

  public Whist () throws IOException {
    super(700, 700, 30);
    players =  PlayerFactory.createPlayers();
    Graphics.initMessage(this);
    initScore();
    Optional<Integer> winner;
    do { 
      initRound();
      winner = playRound();
    } while (!winner.isPresent());
    addActor(new Actor("sprites/gameover.gif"), Graphics.textLocation);
    setStatusText("Game over. Winner is player: " + winner.get());
    refresh();
  }

  public static void main(String[] args) throws IOException {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
    new Whist();
  }

}
