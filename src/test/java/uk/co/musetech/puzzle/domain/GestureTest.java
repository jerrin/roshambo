package uk.co.musetech.puzzle.domain;

import static org.junit.Assert.*;

import org.junit.Test;

import uk.co.musetech.puzzle.domain.Gesture;

public class GestureTest {
	
	@Test
	public void shouldReturnValidGesture(){
		assertEquals(Gesture.PAPER, Gesture.getGesture("PAPER"));
	}

	@Test
	public void shouldReturnNullForInvalidGesture(){
		assertNull(Gesture.getGesture("TOY"));
	}
	
	@Test
	public void shouldReturnValidGestureText(){
		assertEquals(Gesture.ROCK.toString(), Gesture.getGestureText(Gesture.ROCK));
	}
	
	@Test
	public void shouldReturnInvalidGestureText(){
		assertEquals("Invalid Gesture", Gesture.getGestureText(null));
	}
}