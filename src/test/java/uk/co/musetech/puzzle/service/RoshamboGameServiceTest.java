package uk.co.musetech.puzzle.service;


import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

import uk.co.musetech.puzzle.domain.ComputerPlayer;
import uk.co.musetech.puzzle.domain.Gesture;
import uk.co.musetech.puzzle.domain.HumanPlayer;
import uk.co.musetech.puzzle.domain.Player;

public class RoshamboGameServiceTest {

	@Mock 
	private Random randomGenerator;
	@Mock
	private Random secondRandomGenerator;
	@Mock 
	private PrintStream printStream;
	private GameService gameService;
	private Player firstPlayer;
	private Player secondPlayer;
	
	@Before
	public void setup(){
		initMocks(this);
		this.gameService = new RoshamboGameService();
	}
	
	@After
	public void tearDown(){
		reset(randomGenerator);
		reset(secondRandomGenerator);
		reset(printStream);
		this.firstPlayer = null;
		this.secondPlayer = null;
		this.gameService = null;
	}
	
	@Test
	public void shouldIgnoreInvalidGesturesFromFirstPlayer(){
		InputStream firstUserGesture = new ByteArrayInputStream("input".getBytes());
		InputStream secondUserGesture = new ByteArrayInputStream(Gesture.SCISSORS.toString().getBytes());
		
		firstPlayer = new HumanPlayer(firstUserGesture, printStream);
		secondPlayer = new HumanPlayer(secondUserGesture, printStream);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
	}
	
	@Test
	public void shouldIgnoreInvalidGesturesFromSecondPlayer(){
		InputStream firstUserGesture = new ByteArrayInputStream(Gesture.SCISSORS.toString().getBytes());
		InputStream secondUserGesture = new ByteArrayInputStream("input".getBytes());
		
		firstPlayer = new HumanPlayer(firstUserGesture, printStream);
		secondPlayer = new HumanPlayer(secondUserGesture, printStream);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
	}
	
	@Test
	public void shouldNotIncrementScoresWhenGesturesAreSame(){
		when(randomGenerator.nextInt(anyInt())).thenReturn(Gesture.PAPER.ordinal());
		InputStream secondUserGesture = new ByteArrayInputStream(Gesture.PAPER.toString().getBytes());
				
		firstPlayer = new HumanPlayer(secondUserGesture, printStream);
		secondPlayer = new ComputerPlayer(randomGenerator);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
	}

	@Test
	public void shouldNotifyGestureRockAsWinnerOverScissors(){
		when(randomGenerator.nextInt(anyInt())).thenReturn(Gesture.ROCK.ordinal());
		InputStream secondUserGesture = new ByteArrayInputStream(Gesture.SCISSORS.toString().getBytes());
				
		firstPlayer = new ComputerPlayer(randomGenerator);
		secondPlayer = new HumanPlayer(secondUserGesture, printStream);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(1, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
	}

	@Test
	public void shouldNotifyGestureScissorsAsWinnerOverPaper(){
		InputStream secondUserGesture = new ByteArrayInputStream(Gesture.PAPER.toString().getBytes());
		when(randomGenerator.nextInt(anyInt())).thenReturn(Gesture.SCISSORS.ordinal());
				
		firstPlayer = new HumanPlayer(secondUserGesture, printStream);
		secondPlayer = new ComputerPlayer(randomGenerator);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(1, secondPlayer.getScore());
	}

	@Test
	public void shouldNotifyGesturePaperAsWinnerOverRock(){
		when(randomGenerator.nextInt(anyInt())).thenReturn(Gesture.PAPER.ordinal());
		when(secondRandomGenerator.nextInt(anyInt())).thenReturn(Gesture.ROCK.ordinal());
				
		firstPlayer = new ComputerPlayer(randomGenerator);
		secondPlayer = new ComputerPlayer(secondRandomGenerator);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(1, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
	}

	@Test
	public void shouldNotifyTheSecondPlayerAsWinner(){
		when(randomGenerator.nextInt(anyInt())).thenReturn(Gesture.SCISSORS.ordinal());
		when(secondRandomGenerator.nextInt(anyInt())).thenReturn(Gesture.ROCK.ordinal());
				
		firstPlayer = new ComputerPlayer(randomGenerator);
		secondPlayer = new ComputerPlayer(secondRandomGenerator);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(0, secondPlayer.getScore());
		
		gameService.play(firstPlayer, secondPlayer);
		
		assertEquals(0, firstPlayer.getScore());
		assertEquals(1, secondPlayer.getScore());
	}
}