package uk.co.musetech.puzzle.factory;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import uk.co.musetech.puzzle.domain.ComputerPlayer;
import uk.co.musetech.puzzle.domain.HumanPlayer;
import uk.co.musetech.puzzle.domain.PlayerType;
import uk.co.musetech.puzzle.factory.PlayerFactory;

public class PlayerFactoryTest {
	
	@Mock 
	private InputStream inputStream;
	@Mock
	private PrintStream printStream;
	@Mock
	private Random random;
	private PlayerFactory playerFactory;
	
	@Before
	public void setUp() throws IOException {
        initMocks(this);
        when(inputStream.read()).thenReturn(0);
        playerFactory = new PlayerFactory(inputStream, printStream, random);
	}

    @After
    public void teamDown(){
        reset(inputStream);
        reset(printStream);
        reset(random);
    }

	@Test
	public void shouldReturnComputerPlayer(){
		assertEquals(ComputerPlayer.class, playerFactory.getPlayer(PlayerType.COMPUTER).getClass());
	}

	@Test
	public void shouldReturnHumanPlayer(){
		assertEquals(HumanPlayer.class, playerFactory.getPlayer(PlayerType.HUMAN).getClass());
	}
}
