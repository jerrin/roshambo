package uk.co.musetech.puzzle;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;

import uk.co.musetech.puzzle.domain.Gesture;
import uk.co.musetech.puzzle.domain.Player;
import uk.co.musetech.puzzle.factory.PlayerFactory;
import uk.co.musetech.puzzle.domain.PlayerType;
import uk.co.musetech.puzzle.service.GameService;
import uk.co.musetech.puzzle.service.RoshamboGameService;

public class Roshambo {
	
	final static int HUMAN_VS_COMPUTER = 1;
	final static int COMPUTER_VS_COMPUTER = 2;
	final static int BEST_OF_THREE = 3;
	
	private GameService gameService;
	private InputStream inputStream;
	private PrintStream printStream;
	private PlayerFactory playerFactory;
	
	public Roshambo(InputStream inputStream, 
			PrintStream printStream, 
			GameService gameService, 
			PlayerFactory playerFactory){
		this.inputStream = inputStream;
		this.printStream = printStream;
		this.gameService = gameService;
		this.playerFactory = playerFactory;
	}

	public void play(){
		printStream.println(String.format("Human vs Computer(%s)", HUMAN_VS_COMPUTER));
		printStream.println(String.format("Computer vs Computer(%s)", COMPUTER_VS_COMPUTER));
		printStream.println(String.format("Select the game mode (%s | %s): ", HUMAN_VS_COMPUTER, COMPUTER_VS_COMPUTER));

		Scanner scanner = new Scanner(inputStream);
		int gameMode = scanner.nextInt();
		if(gameMode == HUMAN_VS_COMPUTER){
			playHumanVsComputer();
		} else if(gameMode == COMPUTER_VS_COMPUTER){
			playComputerVsComputer();
		} else {
            printStream.println("Invalid game mode, please start again.");
        }
	}
	
	private void playHumanVsComputer(){
		Player firstPlayer = playerFactory.getPlayer(PlayerType.HUMAN);
		Player secondPlayer = playerFactory.getPlayer(PlayerType.COMPUTER);
		playBestOfThree(firstPlayer, secondPlayer);
        printResult(firstPlayer, secondPlayer);
	}
	
	private void playComputerVsComputer(){
		Player firstPlayer = playerFactory.getPlayer(PlayerType.COMPUTER);
		Player secondPlayer = playerFactory.getPlayer(PlayerType.COMPUTER);
		playBestOfThree(firstPlayer, secondPlayer);
        printResult(firstPlayer, secondPlayer);
	}
	
	private void playBestOfThree(Player firstPlayer, Player secondPlayer){
		for(int i=0; i<BEST_OF_THREE; i++){
			gameService.play(firstPlayer, secondPlayer);	
			
			printStream.println(String.format("first player's gesture: %s",  Gesture.getGestureText(firstPlayer.getLastGesture())));
			printStream.println(String.format("second player's gesture: %s", Gesture.getGestureText(secondPlayer.getLastGesture())));
		}
	}

    private void printResult(Player firstPlayer, Player secondPlayer) {
        printStream.println(" ");
        printStream.println("first player's score: " + firstPlayer.getScore());
        printStream.println("second player's score: " + secondPlayer.getScore());

        if(firstPlayer.getScore() == secondPlayer.getScore()){
            printStream.println("It is a TIE!!");
        } else if(firstPlayer.getScore() > secondPlayer.getScore()){
            printStream.println(String.format("first player(%s) WON the game!", firstPlayer.getPlayerType()));
        } else {
            printStream.println(String.format("second player(%s) WON the game!", secondPlayer.getPlayerType()));
        }
    }


    public static void main(String... args) {

        InputStream gameInputStream = System.in;
        PrintStream gameOutputStream = System.out;

        PlayerFactory playerFactory = new PlayerFactory(gameInputStream, gameOutputStream, new Random());
        Roshambo roshambo = new Roshambo(gameInputStream, gameOutputStream, new RoshamboGameService(), playerFactory);

        roshambo.play();
    }

}
