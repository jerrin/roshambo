package uk.co.musetech.puzzle.domain;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;


public class HumanPlayerTest {
	
	private InputStream inputStream;
    @Mock
	private InputStream playerMockInputStream;
	@Mock
	private PrintStream printStream;
	private Player player;
	
	@Before
	public void setup() throws IOException {
		initMocks(this);
        when(playerMockInputStream.read()).thenReturn(0);
	}
	
	@After
	public void teamDown(){
		reset(printStream);
		inputStream = null;
		player = null;
	}
	
	@Test
	public void shouldReadTheGestureFromPlayer(){
		setupPlayerAndGesture(Gesture.SCISSORS.toString());
		assertEquals(Gesture.SCISSORS, player.selectGesture());
	}
	
	@Test
	public void shouldReturnNullForInvalidGestureInput(){
		setupPlayerAndGesture("input");
		assertNull(player.selectGesture());
	}

    @Test
	public void shouldSaveLastGesture(){
		setupPlayerAndGesture(Gesture.SCISSORS.toString());
		assertNull(player.getLastGesture());
		player.selectGesture();
		assertEquals(Gesture.SCISSORS, player.getLastGesture());
	}
	
	@Test
	public void shouldIncrementTheScoreWhenPlayerWins() {
		player = new HumanPlayer(playerMockInputStream, printStream);
		assertEquals(0, player.getScore());
		player.notifyAsWinner();
		assertEquals(1, player.getScore());
	}
	
	@Test
	public void shouldSetPlayerTypeAsHuman(){
		player = new HumanPlayer(playerMockInputStream, printStream);
		assertEquals(PlayerType.HUMAN, player.getPlayerType());
	}

	private void setupPlayerAndGesture(String gestureText){
		inputStream = new ByteArrayInputStream(gestureText.getBytes());
		System.setIn(inputStream);
		player = new HumanPlayer(inputStream, printStream);

	}
}
