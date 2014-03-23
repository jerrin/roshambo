package uk.co.musetech.puzzle.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.reset;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.co.musetech.puzzle.domain.ComputerPlayer;
import uk.co.musetech.puzzle.domain.Gesture;
import uk.co.musetech.puzzle.domain.Player;
import uk.co.musetech.puzzle.domain.PlayerType;

public class ComputerPlayerTest {
	
	@Mock
	private Random randomGenerator;
	private Player player;
	
	@Before
	public void setup(){
		initMocks(this);
	}
	
	@After
	public void tearDown(){
		reset(randomGenerator);
		player = null;
	}
	
	@Test
	public void shouldReturnValidGesture(){
		player = new ComputerPlayer(new Random());
		assertNotNull(player.selectGesture());
	}

	@Test
	public void shouldReturnGestureRock(){
		setupPlayerAndGesture(Gesture.ROCK);
		assertEquals(Gesture.ROCK, player.selectGesture());
	}
	
	@Test
	public void shouldSaveLastGesture(){
		setupPlayerAndGesture(Gesture.SCISSORS);
		assertNull(player.getLastGesture());
		player.selectGesture();
		assertEquals(Gesture.SCISSORS, player.getLastGesture());
	}

	@Test
	public void shouldIncrementTheScoreWhenPlayerWins() {
		player = new ComputerPlayer(randomGenerator);
		assertEquals(0, player.getScore());
		player.notifyAsWinner();
		assertEquals(1, player.getScore());
	}
	
	@Test
	public void shouldSetPlayerTypeAsComputer(){
		player = new ComputerPlayer(randomGenerator);
		assertEquals(PlayerType.COMPUTER, player.getPlayerType());
	}
	
	private void setupPlayerAndGesture(Gesture gesture){
		when(randomGenerator.nextInt(anyInt())).thenReturn(gesture.ordinal());
		player = new ComputerPlayer(randomGenerator);
	}
}