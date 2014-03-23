package uk.co.musetech.puzzle.factory;

import uk.co.musetech.puzzle.domain.ComputerPlayer;
import uk.co.musetech.puzzle.domain.HumanPlayer;
import uk.co.musetech.puzzle.domain.Player;
import uk.co.musetech.puzzle.domain.PlayerType;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

public class PlayerFactory {
	
	private InputStream inputStream;
	private PrintStream printStream;
	private Random random;
	
	public PlayerFactory(InputStream inputStream, 
			PrintStream printStream, Random random){
		this.inputStream = inputStream;
		this.printStream = printStream;
		this.random = random;
	}
	
	public Player getPlayer(PlayerType playerType){
        Player player = null;
		if(PlayerType.HUMAN == playerType){
			player = new HumanPlayer(inputStream, printStream);
		} else {
			player = new ComputerPlayer(random);
		}
        return player;
	}
}