package uk.co.musetech.puzzle.domain;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

public class HumanPlayer extends AbstractPlayer {
	
	private Scanner scanner;
	private PrintStream printStream;
	
	public HumanPlayer(InputStream inputStream, PrintStream printStream){
		super();
        this.scanner = new Scanner(inputStream);
		this.printStream = printStream;
	}
	
	public PlayerType getPlayerType(){
		return PlayerType.HUMAN;
	}

	public Gesture selectGesture() {
		printStream.println("Please enter your gesture(" + Arrays.toString(Gesture.values()) + "): ");
		this.lastGesture = Gesture.getGesture(scanner.next());
		return this.lastGesture;
	}
}
