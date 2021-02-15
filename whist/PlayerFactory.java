import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/** This project was completed by Team 49. **/

public class PlayerFactory {
	
	private static String type0, type1, type2, type3;
	
	public static Player[] createPlayers() throws IOException {
		readFile();
		Player[] players = new Player[Whist.nbPlayers];
		players[0] = new Player(0, parseType(type0));
		players[1] = new Player(1, parseType(type1));
		players[2] = new Player(2, parseType(type2));
		players[3] = new Player(3, parseType(type3));
		return players;
		
	}
	private static Player.Type parseType(String type) {
		if (type.equals("human")) { return Player.Type.HUMAN; }
		else if (type.equals("smart")) { return Player.Type.SMART; }
		else if (type.equals("legal")) { return Player.Type.LEGAL; }
		else if (type.equals("random")) { return Player.Type.RANDOM; }
		else { return Player.Type.RANDOM; }
	}
	
	private static void readFile() throws IOException {
		Properties whistProperties = new Properties();
		// Read properties
		FileReader inStream = null;
				try {
					inStream = new FileReader("whist.properties");
					whistProperties.load(inStream);
				} finally {
					 if (inStream != null) {
			                inStream.close();
			            }
				}
				Whist.winningScore = Integer.parseInt(whistProperties.getProperty("winningScore"));
				Whist.nbStartCards = Integer.parseInt(whistProperties.getProperty("nbStartCards"));
				Whist.enforceRules = Boolean.parseBoolean(whistProperties.getProperty("enforceRules"));
		type0 = whistProperties.getProperty("player0");
		type1 = whistProperties.getProperty("player1");
		type2 = whistProperties.getProperty("player2");
		type3 = whistProperties.getProperty("player3");		
	}

}
