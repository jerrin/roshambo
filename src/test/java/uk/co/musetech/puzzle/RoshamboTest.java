package uk.co.musetech.puzzle;



import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.io.*;
import java.util.Random;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import uk.co.musetech.puzzle.domain.ComputerPlayer;
import uk.co.musetech.puzzle.domain.HumanPlayer;
import uk.co.musetech.puzzle.domain.Player;
import uk.co.musetech.puzzle.factory.PlayerFactory;
import uk.co.musetech.puzzle.domain.PlayerType;
import uk.co.musetech.puzzle.service.GameService;

public class RoshamboTest {

	@Mock
	private GameService gameService;
	@Mock 
	private PlayerFactory playerFactory;
    @Mock
    private InputStream playerInputStream;

	private InputStream inputStream;
	private PrintStream printStream;
	private ByteArrayOutputStream outputStream;
	
	private ComputerPlayer firstComputerPlayer;
	private ComputerPlayer secondComputerPlayer;
	
	private HumanPlayer humanPlayer;

	
	@Before
	public void setUp() throws IOException {
		initMocks(this);
        when(playerInputStream.read()).thenReturn(0);
		outputStream = new ByteArrayOutputStream();
		printStream = new PrintStream(outputStream);

		humanPlayer = new HumanPlayer(playerInputStream, printStream);

		firstComputerPlayer = new ComputerPlayer(new Random());
		secondComputerPlayer = new ComputerPlayer(new Random());
		
		when(playerFactory.getPlayer(PlayerType.COMPUTER)).thenReturn(firstComputerPlayer, secondComputerPlayer);
		when(playerFactory.getPlayer(PlayerType.HUMAN)).thenReturn(humanPlayer);
	}
	
	@After
	public void tearDown(){
		reset(gameService);
		reset(playerFactory);
		this.inputStream = null;
		this.outputStream = null;
	}
	
	@Test
	public void shouldPlayThreeTimesHumanVsComputer(){
		inputStream = new ByteArrayInputStream(String.valueOf(Roshambo.HUMAN_VS_COMPUTER).getBytes());
		Roshambo roshambo = new Roshambo(inputStream, printStream, gameService, playerFactory);
		roshambo.play();
		verify(gameService, times(Roshambo.BEST_OF_THREE)).play(humanPlayer, firstComputerPlayer);
	}
	
	@Test
	public void shouldPlayThreeTimesComputerVsComputer(){
		inputStream = new ByteArrayInputStream(String.valueOf(Roshambo.COMPUTER_VS_COMPUTER).getBytes());
		Roshambo roshambo = new Roshambo(inputStream, printStream, gameService, playerFactory);
		roshambo.play();
		verify(gameService, times(Roshambo.BEST_OF_THREE)).play(firstComputerPlayer, secondComputerPlayer);
	}
	
	@Test
	public void shouldDeclareGameIsTie(){
		inputStream = new ByteArrayInputStream(String.valueOf(Roshambo.HUMAN_VS_COMPUTER).getBytes());
		Roshambo roshambo = new Roshambo(inputStream, printStream, gameService, playerFactory);
		roshambo.play();
		// mock service will not notify the winner
		assertThat(outputStream.toString(), containsString("It is a TIE"));
	}

	@Test
	public void shouldDeclareFirstPlayerAsWinner(){
		
		doAnswer(new Answer<Object>() {
		    public Object answer(InvocationOnMock invocation) throws Throwable {
		        Object[] arguments = invocation.getArguments();
		        if (arguments != null && arguments.length > 1 && arguments[0] != null && arguments[1] != null) {
		            Player firstPlayer = (Player) arguments[0];
		            firstPlayer.notifyAsWinner();
		        }
		        return null;
		    }
		}).when(gameService).play(any(Player.class), any(Player.class));
		
		inputStream = new ByteArrayInputStream(String.valueOf(Roshambo.COMPUTER_VS_COMPUTER).getBytes());
		Roshambo roshambo = new Roshambo(inputStream, printStream, gameService, playerFactory);
		roshambo.play();
		assertThat(outputStream.toString(), containsString(String.format("first player(%s) WON the game", PlayerType.COMPUTER)));
	}

	@Test
	public void shouldDeclareSecondPlayerAsWinner(){
		
		doAnswer(new Answer<Object>() {
		    public Object answer(InvocationOnMock invocation) throws Throwable {
		        Object[] arguments = invocation.getArguments();
		        if (arguments != null && arguments.length > 1 && arguments[0] != null && arguments[1] != null) {
		            Player secondPlayer = (Player) arguments[1];
		            secondPlayer.notifyAsWinner();
		        }
		        return null;
		    }
		}).when(gameService).play(any(Player.class), any(Player.class));

		inputStream = new ByteArrayInputStream(String.valueOf(Roshambo.COMPUTER_VS_COMPUTER).getBytes());
		Roshambo roshambo = new Roshambo(inputStream, printStream, gameService, playerFactory);
		roshambo.play();
		assertThat(outputStream.toString(), containsString(String.format("second player(%s) WON the game", PlayerType.COMPUTER)));
	}
}
